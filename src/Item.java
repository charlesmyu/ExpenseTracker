//contains the item object and methods to store object
	//Object includes data:
		//Date of purchase
		//Date of entry
		//Description
		//Amount ($)
		//Person who purchased

import java.io.PrintStream;
import java.util.Scanner;
import java.time.LocalDate;
import java.text.DecimalFormat;

public class Item {
	private int entryNumber;
	private LocalDate datePurchased;
	private LocalDate dateEntered;
	private String description;
	private double amount;
	private String purchaser;
	
	public Item(int entryNum, String date, String desc, double amt, String purchaser) {
		this.entryNumber = entryNum;
		this.datePurchased = LocalDate.parse(date);
		this.dateEntered = LocalDate.now();
		this.description = desc;
		this.amount = amt;
		this.purchaser = purchaser;
	}
	
	public Item(String line) {
		Scanner lineScanner = new Scanner(line);
		
		this.entryNumber = lineScanner.nextInt();
		this.dateEntered = LocalDate.parse(lineScanner.next()); 
		this.datePurchased = LocalDate.parse(lineScanner.next()); //TODO ! must be in YYYY-MM-DD
		this.description = lineScanner.next(); //TODO ! description cannot have spaces
		String amount = lineScanner.next(); //TODO ! dollar value must have one dollar sign, in $00.00
		this.amount = Double.parseDouble(amount.substring(1, amount.length())); //bc of dollar sign
		this.purchaser = lineScanner.next();
		
		lineScanner.close();
	}
	
	
	public static void printHeader(PrintStream output) {
		output.println("Entry #\t\tDate Entered\t\tDate Purchased\t\tDescription\t\t\tAmount\t\t\tPurchaser");
		//TODO fix print spacings
	}
	
	public String toString() {
		//format dollar amounts to always have 2 decimal places
		DecimalFormat dollars = new DecimalFormat("#.00");
		
		//TODO fix print spacings
		return (this.entryNumber + "\t\t\t" + this.dateEntered + "\t\t\t" + this.datePurchased + "\t\t\t" + this.description + "\t\t\t$" + dollars.format(this.amount) + "\t\t\t" + this.purchaser);
	}
	
	public LocalDate getDatePurchased() {
		return this.datePurchased;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public String getName() {
		return this.purchaser;
	}
	
	public void setName(String name) {
		this.purchaser = name;
	}
	
	public void setNumber(int num) {
		this.entryNumber = num;
	}
}
