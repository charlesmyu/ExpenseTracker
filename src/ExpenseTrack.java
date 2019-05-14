//program tracks expenses of the roommates
//info is stored between activation of program through file
	//stores each expense line by line
	//each line corresponds to an item object
//during program runtime, info is stored in a temp file

//****Structure:
//Startup:
	//ask for input file name or create new file command from console
	//if(new file requested)
		//initialize header in temp file (no entries)
	//else
		//load input file into temp file (initialilzeFile method)

//Operation:
	//while(user has not requested program to end)
		//load temp file entries into item array (initializeArray method)
		//ask for function requested
			//execute function requested:
			//if (save/compile/end)
				//perform function (save/compile)
				//user has requested program to end, set as such
			//else if (change/add/delete)
				//perform function, save array results to temp file (use saveArray method)
	//end of program
//****

//contains console functions:
	//add expense
		//adds expense entry to list with associated info
	//delete expense
		//delete expense from list
	//change expense
		//modify expense in list
	//compile expense
		//calculate amounts owed, amounts spent per person, and amount each person pays each other
		//output to new compiled file with all summarized expenses and above info
	//save expense
		//save expenses to file in list form with all associated object info

//possible future functions:
	//compile statement for range of dates 
	//pull up expenses to console for range of dates using printArray method

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.io.FileNotFoundException;
import java.time.LocalDate;

public class ExpenseTrack {
	public static void main(String[] args) throws IOException {
		
		Scanner console = new Scanner(System.in);
		
		//startup -----
		//ask for and retrieve input file name
		System.out.print("Enter input file name or create new expense list with \"new\": ");
		String inputFileName = console.next();
		
		int lines = 0;
		
		//if request new file, initialize temp file as new file, otherwise copy input file into temp file
		if(inputFileName.equals("new")) { 
			
			//if new file requested, initialize temp file with only header (no entries)
			PrintStream tempPrint = new PrintStream(new File("tempFile"));
			Item.printHeader(tempPrint);
			tempPrint.close();
			
			lines = 1; //if new file, only 1 line exists in file
			
		} else {
			//copy input file into temp file
			boolean exception = true;
			
			do {
				try {
					lines = initializeFile(inputFileName);
					exception = false; //no exception triggered, end while loop
				} catch(FileNotFoundException e) { //exception triggered, retrieve new file name
					System.out.println("\nThat file does not exist.");
					System.out.print("Type another file name: ");
					inputFileName = console.next();
				} 
			} while(exception); //continue to execute if exception has been triggered
		}
		//-----
		
		
		//operation-----
		
		//CODE NOTES FOR WHILE LOOP:
		//while loop (end on command "end", "compile", or "save")
		//initialize temp file into object array
			//for each entry, extract dates, description, amounts, etc and put into object in array 

		//ask for what user would like to do
		//perform function
			//for every function performed, when function complete, save into temp file using save method (remember to retrieve number of lines in file)
			//then program will loop back to beginning of while loop and re-initialize temp file
		
		//use # of lines to determine whether to end loop or not (where lines = 0 indicates end desired)
		//# of lines used bc this value can change with add/delete functions, and must be able to change accordingly as a result
		while(lines != 0) { 
			
			//----
			int entries = lines-1; //number of entries in the file is # of lines less the header
			Item[] item = new Item[entries]; //initialize array based on above size
			
			//place temp file's content into item array, and print entries out to console
			initializeArray(item);
			//----
			
			//find function and execute using item array, then save result to temp file
			//modifies askFunction boolean if necessary to stop program
			lines = executeFunction(item, lines, console);
		} //if while loop continues, re-initialize temp file into item array (replace old array with new info)
		//-----

		//when program ends, delete temp file
		File tempFile = new File("tempFile");
		tempFile.delete();
	}
	
	
	//find and execute requested function to be performed on array
	public static int executeFunction(Item[] item, int lines, Scanner console) throws IOException {
		
		//ask user for function to execute
		System.out.print("What function would you like to execute? You can add, delete, change, save, compile, end, or ask for help: ");
		String request = console.next();
		request = request.toLowerCase();
		System.out.println();
		System.out.println("--------------------");
		System.out.println();
		
		if(request.equalsIgnoreCase("end")) { //if end requested, change lines to 0 to end program
			System.out.println("Program ended.");
			lines = 0;
			
		} else if(request.equalsIgnoreCase("save")) {
			System.out.print("Enter output file name (must be a single string): ");
			String outputFileName = console.next();
			
			//save file using requested file name
			saveArray(item, outputFileName);

			//end program, as save indicates end of usage
			System.out.println("\nExpense list saved to " + outputFileName + ".");
			lines = 0;
		
		} else if(request.equalsIgnoreCase("change")) { 		
			//retrieve # of entry to change and call change method
			boolean wantChange = true;
			
			while(wantChange) {
				//activate change procedure
				changeEntry(findNumber(console), item, console);
				
				//ask user if another change is wanted. if no, break loop
				System.out.println();
				System.out.print("Would you like to make another change? (Yes/No): ");
				String change = console.next();
				if(change.toUpperCase().startsWith("N")) {
					wantChange = false;
				} else {
					System.out.println();
				}
			}
			
			lines = saveArray(item, "tempFile");
			
		} else if(request.equalsIgnoreCase("delete")) {
			//delete item and save to new array
			Item[] newItem = deleteEntry(findNumber(console), item, console);
			lines = saveArray(newItem, "tempFile");
			
		} else if(request.equalsIgnoreCase("add")) {
			//call method to add entries, and assign to new array that will be re-saved
			Item[] newItem = addEntries(item, console);
			
			System.out.println("\nEntries appended.");
			lines = saveArray(newItem, "tempFile"); //new # of lines counted by the saveArray method, so use that
			
		} else if(request.equalsIgnoreCase("help")) {
			
			//print README doc to console
			Scanner readMe = new Scanner(new File("README"));
			
			while(readMe.hasNextLine()) {
				System.out.println(readMe.nextLine());
			}
			
			readMe.close();
			
			lines = saveArray(item, "tempFile");

		} else if(request.equalsIgnoreCase("compile")) {
			//save normal file before compiling file (just for documentation's sake)
			System.out.print("Enter save list file name (must be a single string, add .txt): ");
			String outputFileName1 = console.next();
			
			saveArray(item, outputFileName1);
			
			//now prompt for compile file name
			System.out.print("Enter compile list file name (must be a single string, add .txt): ");
			String outputFileName2 = console.next();
			
			//compile file
			compile(item, outputFileName2, console);
			
			//end program as compile indicates end of use
			System.out.println("\nExpense list compiled to " + outputFileName2 + ", and entries saved to " + outputFileName1 + ".");
			lines = 0;
			
		} else {
			System.out.println("That request is invalid. Please try again.");
		}
		
		System.out.println();
		System.out.println("--------------------");
		
		return lines;
	}
	
	
	//finds number of entry desired by user
	public static int findNumber(Scanner console) {
		System.out.print("Enter the target entry number: ");
		int change = console.nextInt();
		System.out.println();
		return change;
	}
	
	
	//deletes entry with specified entry number given
	public static Item[] deleteEntry(int entry, Item[] item, Scanner console) {
		//checks whether user wants to delete entry or not
		System.out.print("Are you sure you would like to delete entry " + entry + "? ");
		String ans = console.next();
		
		//if user does, create new array with length one less and copy all applicable elements over
		if(ans.toLowerCase().startsWith("y")) {
			Item[] newItem = new Item[item.length - 1];
			
			for(int i=0; i<entry - 1; i++) {
				newItem[i] = item[i];
			}
			
			for(int i=entry; i<item.length; i++) {
				newItem[i-1] = item[i];
			}
			
			System.out.println("\nEntry Deleted.");
			
			return newItem;
		} else { //otherwise return original array
			return item;
		}
	}
	
	
	//changes entry with specified entry number given
	public static void changeEntry(int entry, Item[] item, Scanner console) {
		item[entry - 1].change(console);
	}
	
	
	//method adds multiple entries to current list at discretion of the user
	public static Item[] addEntries(Item[] items, Scanner console) {
		
		//Prompt for number of entries desired
		System.out.print("Number of entries to be added: ");
		int numEntries = console.nextInt();
		
		//create new items array and copy old items over
		Item[] newItems = new Item[items.length + numEntries];
		
		for(int i=0; i<items.length; i++) {
			newItems[i] = items[i];
		}
		
		System.out.println();
		
		//prompt user to input info and add new entries through use of Item constructor
		for(int i=items.length; i<numEntries+items.length; i++) {
			System.out.println("New Entry #" + (i-items.length+1));
			
			System.out.print("Enter purchase date in format YYYY-MM-DD: ");
			String date = console.next();
			
			System.out.print("Enter description (no spaces): ");
			String desc = console.next(); 
			
			System.out.print("Enter amount spent: $");
			double amt = console.nextDouble();
			
			System.out.print("Enter purchaser name: ");
			String name = console.next();
			
			System.out.println();
			
			newItems[i] = new Item(i-items.length+1, date, desc, amt, name);
		}
		
		//print out new entries to confirm entry
		Item.printHeader(System.out);
		for(int i=items.length; i<numEntries+items.length; i++) {
			System.out.println(newItems[i].toString());
		}
		
		//confirm entries are desired
		System.out.println();
		System.out.print("Would you like to append the above entry/entries? ");
		String ans = console.next();
		
		//if entries are desired, return new array with appended entries. Otherwise, return old array
		if(ans.toUpperCase().startsWith("Y")) {
			return newItems;
		} else {
			return items;
		}
	}
	
	
	//compile file and save as separate file 
	public static void compile(Item[] items, String outputFileName, Scanner console) throws IOException {
		PrintStream output = new PrintStream(new File(outputFileName));
		
		//Print title
		output.println("COMPILED STATEMENT FOR " + LocalDate.now().toString());
		output.println();
		output.println("STATEMENT:");
		
		//print cumulative list to file 
		printArray(items, output);
		output.println("\n--------------------------------------------------");
		
		//DETERMINE UNIQUE PURCHASERS 
		int totalPeople = 0;
		Person[] purchasers = new Person[0];
		
		//determine number of unique purchasers, assign each unique purchaser to person in purchasers array
		for(int i=0; i<items.length; i++) {
			//if purchaser is unique, tally up on total purchasers, and add to purchaser array
			if(uniquePerson(purchasers, items[i])) {
				totalPeople++;
				purchasers = Arrays.copyOf(purchasers, purchasers.length + 1);
				purchasers[purchasers.length-1] = new Person(items[i].getName());
			}
		}

		
		//CALCULATE AMOUNT SPENT PER PERSON 
		double perPersonSpend  = amountPerPerson(totalPeople, items);
		
		
		//CALCULATE AMOUNT OWED TO EACH PERSON, INITIALIZE BALANCE OF EACH PERSON 
		//run through all entries in array and assign each item's amount to a person
		for(int i=0; i<items.length; i++) {
			//determine which person to alter stats of based on purchaser name
			int change = selectPerson(items[i], purchasers);
			
			//add amount owed by what was bought
			purchasers[change].changeOwed(items[i].getAmount());
		}
		
		//set outstanding amount and balance for each person (outstanding amount is same for each person)
		for(int i=0; i<purchasers.length; i++) {
			purchasers[i].setOutstanding(perPersonSpend);
			purchasers[i].setBalance();
		}
		
		
		//PRINT BALANCES
		output.println("\nBALANCES OWED AND OWING:");
		//output.println("--Positive balance is owing, negative balance is owed--");
		for(int i=0; i<purchasers.length; i++) {
			output.println(purchasers[i].toString());
			output.println();
		}
		
		output.println("--------------------------------------------------");
		
		//PRINT CASH FLOWS AND FIND TRANSFER AMTS
		output.println("\nCASH FLOWS:");
		
		//find amounts to transfer while balances are not zero
		while(!Person.balancesZeroed(purchasers)) {
			
			//find purchasers with max and min balances, transfer amts between the two first
			Person max = Person.findMax(purchasers);
			Person min = Person.findMin(purchasers);
			
			//now find the max amount possible to be transferred between the two
			double transfer = Person.findTransfer(max, min);
			
			//print out amts transferred
			DecimalFormat dollars = new DecimalFormat("#.00");
			output.println("Transfer $" + dollars.format(transfer) + " from " + max.getName() + " to " + min.getName());
			
			//transfer amts in person object's balances
			max.changeBalance(-transfer);
			min.changeBalance(transfer);
		}
		
		/*
		//test code (debugging purposes)
		System.out.println("----\n" + totalPeople);
		for(int i=0; i<purchasers.length; i++) {
			System.out.println(purchasers[i].toString());
		}
		System.out.println("----\n");
		//----
		 */
		
		output.println("\n--------------------------------------------------");
		output.print("\nEND OF FILE");
	}
	
	
	//calculates amount owed per person
	public static double amountPerPerson(int totalPeople, Item[] items) {
		double totalSpent = 0;
		
		for(int i=0; i<items.length; i++) {
			totalSpent += items[i].getAmount();
		}
		
		return totalSpent/totalPeople;
	}
	
	
	//returns index of person to apply changes to when given some item 
	public static int selectPerson(Item input, Person[] purchasers) {
		String name = input.getName();
		
		for(int i=0; i<purchasers.length; i++) {
			if(name.equals(purchasers[i].getName())) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	//determines whether the new item contains a unique person
	public static boolean uniquePerson(Person[] purchasers, Item entry) {
		for(int i=0; i<purchasers.length; i++) {
			if(purchasers[i].getName().equalsIgnoreCase(entry.getName())) {
				return false;
			}
		}
		return true;
	}

	
	//saves array to desired output file location, return number of lines printed in file
	public static int saveArray(Item[] item, String outputFileName) throws IOException {
		//create printstream using desired file name
		PrintStream output = new PrintStream(new File(outputFileName));
		
		//print out array to that printstream and return number of lines
		return printArray(item, output);
	}
	
	
	//print array to given output (i.e. file or console), return number of lines printed
	public static int printArray(Item[] item, PrintStream output) {
		//print header for array 
		Item.printHeader(output);
		
		//sort array using comparator by date purchased
		Arrays.sort(item, Comparator.comparing(Item::getDatePurchased));
		
		//for every element in array, print it out and increase line count
		int lines = 1;
		for(int i=0; i<item.length; i++) {
			item[i].setNumber(lines);
			output.println(item[i].toString());
			lines++;
		}
		
		return lines;
	}
	
	
	//takes an input file and copies it to a temporary file
	//allows original file to remain untouched to prevent overwriting
	public static int initializeFile(String inputFileName) throws IOException {
		PrintStream tempPrint = new PrintStream(new File("tempFile")); //create temp file and print
		
		Scanner inputScan = new Scanner(new File(inputFileName)); //scan input file
		
		//track number of lines in file
		int lines = 0; 
		
		//copy everything over exactly, line by line
		while(inputScan.hasNextLine()) { 
			tempPrint.println(inputScan.nextLine());
			lines++;
		}
		
		inputScan.close();
		tempPrint.close();
		
		return lines;
	}
	
	
	//takes temp file and parses it into an array of items
	public static void initializeArray(Item[] item) throws IOException {
		Scanner tempFileScanner = new Scanner(new File("tempFile")); //now process temp file line by line
		tempFileScanner.nextLine(); //bin one line for header
		System.out.println();
		Item.printHeader(System.out); //print header to console to accompany printed entries later
		
		//for every line, enter information into array
		//document contains info in form [1 2019-03-25 2019-03-22 <description>	$43.25 Charles]
		for(int i=0; i<item.length; i++) {
			String line = tempFileScanner.nextLine();
			System.out.println(line); //print out entries to console
			
			item[i] = new Item(line); //initialize new item for i'th array element (avoid null pointer exception) using fromString constructor
		}
		tempFileScanner.close();
		System.out.println();
	}
}
