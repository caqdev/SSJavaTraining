package com.ss.lms.ui;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Genre;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.service.BorrowerService;
import com.ss.lms.service.LibrarianService;

public class BorrowerMenu {

	ConsoleHelper helper;
	Scanner scan;
	
	public BorrowerMenu(Scanner scan) {
		helper = new ConsoleHelper();
		this.scan = scan;
	}

	public void borrowerCardNoInput() {
		BorrowerService borrowerService = new BorrowerService();
		Integer cardNo = -1;
		boolean isInputInRange = false;
		Borrower borrower;
		while(!isInputInRange) {
			System.out.println("Enter your card number (Type 0 to go back):");
			cardNo = helper.readIntInput(scan);
			if(cardNo < 0) {
				System.out.println("Please enter 0 or a positive number");
				continue;
			}else {
				isInputInRange = true;
			}
		}
		if(cardNo == 0) {
			return;
		}
		borrower = borrowerService.getBorrowerById(cardNo);
		if(borrower == null) {
			System.out.println("Card No not found please enter another number");
		}
		mainBorrowerMenu(borrower);
		
	}

	private void checkoutMenu(Borrower borrower) {
		System.out.println("Pick the branch you want to checkout from:");
		LibraryBranch branchToCheckout = selectLibraryBranch();
		if(branchToCheckout == null) return;
		System.out.println("Pick which book you want to checkout:");
		Book bookToCheckout = selectBookToCheckoutFromBranch(branchToCheckout);
		if(bookToCheckout == null) return;
		BookLoan newBookLoan = new BookLoan(bookToCheckout, branchToCheckout, borrower);
		
		BorrowerService borrowerService = new BorrowerService();
		System.out.println(borrowerService.addNewBookLoan(newBookLoan));
		
	}

	private void mainBorrowerMenu(Borrower borrower) {
		// TODO Auto-generated method stub
		System.out.println("Hello " + borrower.getBorrowerName() +"!");
		System.out.println("What would you like to do?\n1) Check-out Book \n2) Return a Book \n3) Quit to previous");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
		case 1:
			checkoutMenu(borrower);
			break;
		case 2:
			returnMenu(borrower);
			break;
		case 3:
			return;
		default:
			System.out.println("Please enter a number from the list");
			mainBorrowerMenu(borrower);
		}
		mainBorrowerMenu(borrower);
	}
	
	private void returnMenu(Borrower borrower) {
		LibraryBranch branchToReturn = selectLibraryBranchForReturn(borrower);
		if(branchToReturn == null) return;
		Book bookToReturn = selectBookToReturnFromBranch(branchToReturn, borrower);
		if(bookToReturn == null) return;
		BorrowerService borrowerService = new BorrowerService();
		BookLoan loanToReturn = new BookLoan(bookToReturn, branchToReturn, borrower);
		System.out.println(borrowerService.returnBook(loanToReturn));
		
	}

	private LibraryBranch selectLibraryBranch() {
		BorrowerService borrowerService = new BorrowerService();
		System.out.println("Please select a library branch you want to checkout from:");
		List<LibraryBranch> libraryBranches = borrowerService.getLibraryBranches();
		int listCounter = 1;
		for (LibraryBranch lb : libraryBranches) {
			System.out.println(listCounter + ") " + lb.getBranchName() + " Address: " + lb.getBranchAddress());
			listCounter++;
		}
		System.out.println(listCounter + ") " + "Choose this go back.");
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
	
	private LibraryBranch selectLibraryBranchForReturn(Borrower borrower) {
		BorrowerService borrowerService = new BorrowerService();
		System.out.println("Pick the branch you want to return a book to:");
		List<LibraryBranch> libraryBranches = borrowerService.getLibraryBranchesForReturn(borrower);
		int listCounter = 1;
		for (LibraryBranch lb : libraryBranches) {
			System.out.println(listCounter + ") " + lb.getBranchName() + " Address: " + lb.getBranchAddress());
			listCounter++;
		}
		System.out.println(listCounter + ") " + "Choose this go back.");
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

	private Book selectBookToCheckoutFromBranch(LibraryBranch branch) {
		System.out.println("Please select a book from the list: ");
		BorrowerService borrowerService = new BorrowerService();
		try {
			List<Book> books = borrowerService.getBooksAvailableFromBranch(branch);
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

	private Book selectBookToReturnFromBranch(LibraryBranch branchToReturn, Borrower borrower) {
		System.out.println("Please select a book from the list to return: ");
		BorrowerService borrowerService = new BorrowerService();
		try {
			List<Book> books = borrowerService.getBooksAvailableToReturnToBranch(branchToReturn, borrower);
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
}
