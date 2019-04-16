import java.text.DecimalFormat;

//object tracks stats of person in order to compile payment report

public class Person {
	private String name;
	private double outstandingAmount;
	private double owedAmount;
	private double balance;
		//balance is overall sum owed or outstanding. Positive is sum owed to person, negative is sum outstanding
	
	public Person(String name, double outstanding, double paid) {
		this.name = name;
		this.outstandingAmount = outstanding;
		this.owedAmount = paid;
	}
	
	public Person(String name) {
		this(name, 0, 0);
	}
	
	//returns true if names of two Person objects are identical (not case sensitive). Otherwise, false.
	public boolean nameEquals(Person other) {
		return this.name.toLowerCase().equals(other.name.toLowerCase());
	}
	
	//finds whether all balances of every person is 0
	public static boolean balancesZeroed(Person[] purchasers) {
		for(int i=0; i<purchasers.length; i++) {
			if(Math.round(purchasers[i].getBalance() * 100)/100 != 0.0) { //Math.round to compensate for the way java stores 0
				return false;
			}
		}
		
		return true;
	}
	
	//finds amount to be transferred from two people (must be the smaller of the two absolute values, otherwise will cause overdraw)
	public static double findTransfer(Person max, Person min) {
		double maxAmt = Math.abs(max.getBalance());
		double minAmt = Math.abs(min.getBalance());
		
		return Math.min(maxAmt, minAmt);
	}
	
	//finds purchaser with max balance, returns person
	public static Person findMax(Person[] purchaser) {
		Person max = purchaser[0];
		
		for(int i=1; i<purchaser.length; i++) {
			if(Math.max(max.getBalance(), purchaser[i].getBalance()) == purchaser[i].getBalance()) {
				max = purchaser[i];
			}
		}
		
		return max;
	}
	
	//finds purchaser with min balance, returns person
	public static Person findMin(Person[] purchaser) {
		Person min = purchaser[0];
		
		for(int i=1; i<purchaser.length; i++) {
			if(Math.min(min.getBalance(), purchaser[i].getBalance()) == purchaser[i].getBalance()) {
				min = purchaser[i];
			}
		}
		
		return min;
	}
	
	public String toString() {
		//format dollar amounts to always have 2 decimal places
		DecimalFormat dollars = new DecimalFormat("#.00");
				
		return (this.name + (":\n\tOwing: $" + dollars.format(this.outstandingAmount) + "\n\tOwed: $" + dollars.format(this.owedAmount) + "\n\tBalance: $" + dollars.format(this.balance)));
	}
	
	public void changeBalance(double amount) {
		this.balance += amount;
	}
	
	//sets balance of person based on outstanding and paid amounts
	public void setBalance() {
		this.balance = this.outstandingAmount - this.owedAmount;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getOutstanding() {
		return this.outstandingAmount;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public double getOwedAmount() {
		return this.owedAmount;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void changeOwed(double amount) {
		this.owedAmount += amount;
	}
	
	public void setOutstanding(double amount) {
		this.outstandingAmount = amount;
	}
}
