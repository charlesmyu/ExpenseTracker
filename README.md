# ExpenseTracker

**This is still a work in progress

Program which tracks shared expenses of a group of people (i.e. a group of friends travelling, or roommates over the period of a month). 
Program has functionality to then determine how to pay one another back to even out those costs. 

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
	The compile file will contain the statement of all expenses, balances owing and owed for each purchaser, as well as amounts and 	transfer paths to even costs (i.e. Person A pays person B $x).
	You will be prompted for two file names: one for the save file, and one for the compile file.
				
Compile Notes:
	The compile file cannot be used as an import file at the beginning of the program.
	If there is an individual who you wish to split costs with, but has not purchased anything, you must enter them in the expense 		list at least once with a purchase amount of $0. This is because the program will only divide expenses amongst those who have 		been entered into the list
			
			
Add:
	Adds items to the current entries list. It will prompt you for the various entry properties.
	
	Purchase Date: Must be entered in format YYYY-MM-DD
	Description: Must not contain any spaces
	Amount Spent: Enter decimal number only
	Purchaser: Must not contain any spaces
			
Change:
	Program will prompt you for the desired entry you wish to modify (target entry #) and then ask you which field you would like to 
	modify. Any info modified must follow the same rules as outlined in the add function. You can modify multiple times following 
	one change command, as the program will ask whether you wish to continue modifying or not.
			
Delete:
  	Program will prompt for the desired entry to delete (target entry #) and then confirm you wish to delete it. 
			
				
Please note that if any file name is not specified with a specific path name, it will be saved to the program's ExpensesTracker folder.
