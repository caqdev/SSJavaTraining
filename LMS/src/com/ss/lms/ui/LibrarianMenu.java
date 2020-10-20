package com.ss.lms.ui;

import java.util.List;
import java.util.Scanner;

import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopies;
import com.ss.lms.entity.Genre;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.service.AdministratorService;
import com.ss.lms.service.LibrarianService;

public class LibrarianMenu {

	ConsoleHelper helper;
	Scanner scan;
	public LibrarianMenu(Scanner scan) {
		this.helper = new ConsoleHelper();
		this.scan = scan;
	}
	
	private void addBookCopiesMenu(LibraryBranch branch) {
		LibrarianService librarianService = new LibrarianService();
		Book bookToUpdateCopies = selectBook();
		if(bookToUpdateCopies == null) {
			return;
		}
		//Display how many of the selected book there are
		BookCopies bc = librarianService.getBookCopiesAtBranch(bookToUpdateCopies, branch);
		boolean createNewEntry = (bc == null);
		int numberOfCopies = -1;
		if(createNewEntry) {
			numberOfCopies = 0;
			bc = new BookCopies(bookToUpdateCopies.getBookId(), branch.getBranchId(), -1);
		} else {
			numberOfCopies = bc.getNumberOfCopies();
		}
		System.out.println("Existing number of copies: " + numberOfCopies);
		//accept new input for amount of copies total
		boolean isInputInRange = false;
		int newTotal = -1;
		while(!isInputInRange) {
			System.out.println("Enter new total number of copies: ");
			newTotal = helper.readIntInput(scan);
			if(newTotal < 0) {
				System.out.println("Please enter 0 or a positive number");
			} else {
				isInputInRange = true;
			}
		}
		//hit the DB
		bc.setNumberOfCopies(newTotal);
		if(createNewEntry) {
			System.out.println(librarianService.addBookCopies(bc));
		} else {
			System.out.println(librarianService.updateBookCopies(bc));
		}
	}

	public void branchActionMenu(LibraryBranch branch) {
		System.out.println("Enter the branch you manage.\n1) Update the details of the library \n2) Add copies of Book to the branch \n3) Quit to previous");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
		case 1:
			updateLibraryBranchDetailsMenu(branch);
			break;
		case 2:
			addBookCopiesMenu(branch);
		case 3:
			return;
		default:
			System.out.println("Please Enter a number from the list");
			branchActionMenu(branch);
		}
		branchActionMenu(branch);
	}
	
	public void branchSelectMenu() {
		LibraryBranch branchToWorkIn = selectLibraryBranch();
		if(branchToWorkIn == null) {
			return;
		}
		branchActionMenu(branchToWorkIn);
	}
	
	
	public void mainLibrarianMenu() {
		System.out.println("Enter the branch you manage.\n1) Enter Branch you manage \n2) Quit to previous");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
		case 1:
			branchSelectMenu();
			break;
		case 2:
			return;
		default:
			System.out.println("Please Enter a number from the list");
			mainLibrarianMenu();
		}
		mainLibrarianMenu();
	}

	private Book selectBook() {
		System.out.println("Please select a book from the list: ");
		LibrarianService librarianService = new LibrarianService();
		try {
			List<Book> books = librarianService.getBooks(null);
			int listCounter = 1;
			for(Book b: books) {
				String listOfAuthors = "";
				for(Author a: b.getAuthors()) {
					listOfAuthors = listOfAuthors + a.getAuthorName() + ", ";
				}
				String listOfGenres = "";
				for(Genre g: b.getGenres()) {
					listOfGenres = listOfGenres + g.getGenreName() + ", ";
				}
				System.out.println(listCounter + ") " + b.getTitle() + " by " + listOfAuthors + "Genres: " + listOfGenres + 
					"Published by: " + b.getPublisher().getPublisherName());
				listCounter++;
			}
			System.out.println(listCounter + ") " + "Choose this to go back");
			
			int userInput = -1;
			boolean inputIsInRange = false;
			while(!inputIsInRange) {
				userInput = helper.readIntInput(scan);
				if(userInput < 1 || userInput > listCounter) {
					System.out.println("Please choose a number from 1 - " + listCounter);
				} else {
					inputIsInRange = true;
				}
			}
			Book bookSelected;
			if(userInput == listCounter) {
				return null;
			} else {
				bookSelected = books.get(userInput-1);
				return bookSelected;
			}
		} catch (Exception e) {
			return null;
		}
	}

	private LibraryBranch selectLibraryBranch() {
		LibrarianService librarianService = new LibrarianService();
		System.out.println("Please select a library branch from the list:");
		List<LibraryBranch> libraryBranches = librarianService.getLibraryBranches(null);
		int listCounter = 1;
		for (LibraryBranch lb : libraryBranches) {
			System.out.println(listCounter + ") " + lb.getBranchName() + " Address: " + lb.getBranchAddress());
			listCounter++;
		}
		System.out.println(listCounter + ") " + "Choose this go back to the branch select menu.");
		int userInput = -1;
		boolean isInputInRange = false;
		do {
			userInput = helper.readIntInput(scan);
			if(userInput < 1 || userInput > listCounter) {
				System.out.println("Please select a number from 1 to " + listCounter);
			} else{
				isInputInRange = true;
			}
		} while (!isInputInRange);
		if(userInput == listCounter) {
			return null;
		}
		return libraryBranches.get(userInput-1);
	}

	private LibraryBranch updateLibraryBranchDetailsMenu(LibraryBranch branchToUpdate) {
		int menuSelection = -1;
		boolean shouldMakeChanges = true;
		
		while(shouldMakeChanges) {
			System.out.println("What library branch information do you wish to update?");
			System.out.println("1) Name\n2) Address \n3) Go back (Uncommitted changes will be lost)");
			menuSelection = helper.readIntInput(scan);
			boolean isInputInRange = false;
			while(!isInputInRange) {
				if(menuSelection < 1 || menuSelection > 3) {
					System.out.println("Please select a number from the list");
				} else {
					isInputInRange = true;
				}
			}
			switch(menuSelection) {
				case 1:
					System.out.println("Enter the new name for the library branch:");
					String newBranchName = helper.acceptProperLengthInput(scan, 45);
					branchToUpdate.setBranchName(newBranchName);
					break;
				case 2:
					System.out.println("Enter the new address for the library branch:");
					String newBranchAddress = helper.acceptProperLengthInput(scan, 45);
					branchToUpdate.setBranchAddress(newBranchAddress);
					break;
				case 3:
					return null;
			}
			System.out.println("Do you want to make more changes to this library branch? (y/n)");
			shouldMakeChanges = helper.handleYesNoPrompt(scan);
		}
		
		LibrarianService librarianService = new LibrarianService();
		System.out.println(librarianService.updateLibraryBranch(branchToUpdate));
		return branchToUpdate;
	}
	
	
}
