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
		//use boolean and while loop to determine if user has saved or ended program
		boolean askFunction = true;
		
		//CODE NOTES FOR WHILE LOOP:
		//while loop (end on command "end", "compile", or "save")
		//initialize temp file into object array
			//for each entry, extract dates, description, amounts, etc and put into object in array 

		//ask for what user would like to do
		//perform function
			//for every function performed, when function complete, save into temp file using save method (remember to retrieve number of lines in file)
			//then program will loop back to beginning of while loop and re-initialize temp file
		
		while(askFunction) { 
			
			//----
			int entries = lines-1; //number of entries in the file is # of lines less the header
			Item[] item = new Item[entries]; //initialize array based on above size
			
			//place temp file's content into item array, and print entries out to console
			initializeArray(item);
			//----
			
			//find function and execute using item array, then save result to temp file
			//modifies askFunction boolean if necessary to stop program
			askFunction = executeFunction(item, askFunction, console);
		} //if while loop continues, re-initialize temp file into item array (replace old array with new info)
		//-----

		//when program ends, delete temp file
		File tempFile = new File("tempFile");
		tempFile.delete();
	}
	
	
	//find and execute requested function to be performed on array
	public static boolean executeFunction(Item[] item, boolean askFunction, Scanner console) throws IOException {
		
		//ask user for function to execute
		System.out.print("What function would you like to execute? You can add, delete, change, save, compile, end, or ask for help: ");
		String request = console.next();
		request = request.toLowerCase();
		System.out.println();
		
		if(request.equals("end")) { //if end requested, change askFunction to false to end program
			askFunction = false;
			
		} else if(request.equals("save")) {
			System.out.print("Enter output file name (must be a single string): ");
			String outputFileName = console.next();
			
			//save file using requested file name
			saveArray(item, outputFileName);

			//end program, as save indicates end of usage
			System.out.println("Expense list saved to " + outputFileName);
			askFunction = false;
		
		} else if(request.equals("change")) { 
			//TODO when each function completed, perform save into temp file using save method
			//refer to function descriptions at top
			
			
			//TODO change method
				//input: item
				//output: item2 (new item array)
			
				//request and search for item to change
				//alter entry requested for
				//arrange to be able to change multiple entries at a time (use while loop and end on "done" or smth)
			
			//saveArray(item2, "tempFile");
		} else if(request.equals("delete")) {
			//TODO delete method
				//input: item
				//output: item2 (new item array)
				//make search method to search array
					//search method must be able to search through any of the item elements
				//arrange to be able to change multiple entries at a time (use while loop and end on "done" or smth)
			
			//saveArray(item2, "tempFile");
		} else if(request.equals("add")) {
			//TODO add method
				//input: item
			 	//output: item2 (new item array)
			
			//in method:
				//ask for number of entries to be added
				//prompt user for entries, and use to create add array containing ONLY new entries
					//for date entered, use LocalDate.now()
				//using this add array, confirm with user they wish to add (print out array to console to confirm)
					//if they do not wish to add, return original array (do not modify)
				//if they wish to add, append add array onto original array and return that (copyOf method)
			
			//back in main method (here), save new array to temp file (written below)
				//saveArray(item2, "tempFile");
		} else if(request.equals("help")) {
			//print help document to console
			
			/* Help document
			This program is designed to track expenses of a group of people, and determine how to even out those costs (i.e. how to pay one another back).
			It is designed such that multiple people can purchase items.
			
			Expense lists are saved in text files when the program is not in operation, consisting of various "entries" that each represent an expense.
			
			Upon startup, the program will prompt you to either create a new file or pull up an existing file. 
			Given an existing file, the program will import the file's entries and utilize those as its current entries.
			When asked to create a new file, the program will begin a new file with no entries.
			
			The program will then prompt for various functions:
	
			Save:
				This function saves the existing entries into a text file, and then ends the program.
				You will be prompted for a file name that will be used to save the file.
				Any existing file with the same file name will be overwritten.
				This type of file (save file) can be used at the start of the program to import entries. 
			
			Compile:
				This function will save two files: a save file, and a unique "compile file".
				The compile file will contain the statement of all expenses, balances owing and owed for each purchaser, as well as amounts and transfer paths to even costs (i.e. Person A pays person B $x).
				You will be prompted for two file names: one for the save file, and one for the compile file.
				
				Notes:
					The compile file cannot be used as an import file at the beginning of the program.
					If there is an individual who you wish to split costs with, but has not purchased anything, you must enter them in the expense list at least once with a purchase amount of $0.
						This is because the program will only divide expenses amongst those who have been entered into the list
			
			Add:
			
			
			Change:
			
			
			Delete:
			
			
			Help:
				This.
				
				
			Please note that if any file name is not specified with a specific path name, it will be saved to the program's ExpensesTracker folder.
			 */
		} else if(request.equals("compile")) {
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
			System.out.println("\nExpense list compiled to " + outputFileName2);
			askFunction = false;
			
		} else {
			System.out.println("That request is invalid. Please try again.");
		}
		
		return askFunction;
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
			output.println(item[i]);
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
			
			item[i] = new Item(); //initialize new item for i'th array element (avoid null pointer exception)
			item[i].fromString(line); //put string info into item object
		}
		tempFileScanner.close();
		System.out.println();
	}
}
