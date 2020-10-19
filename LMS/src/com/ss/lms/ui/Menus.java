package com.ss.lms.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Genre;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.Publisher;
import com.ss.lms.service.AdministratorService;

public class Menus {

	Scanner scan;
	ConsoleHelper helper;
	public Menus() {
		this.scan = new Scanner(System.in);
		this.helper = new ConsoleHelper();
	}
	
	public void administratorMenu() {
		System.out.println("What would you like to interact with:");
		System.out.println("1) Books \n2) Authors \n3) Genres \n4) Publishers \n5) Library Branches \n6) Borrowers \n7) Book Loans ");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
			case 1:
				adminBookMenu();
				break;
			case 2:
				adminAuthorMenu();
				break;
			case 3:
				adminGenreMenu();
				break;
			case 4:
				adminPublisherMenu();
				break;
			case 5:
				adminLibraryBranchMenu();
				break;
			case 6:
				adminBorrowerMenu();
				break;
			case 7:
				adminLoanMenu();
				break;
			default:
				System.out.println("Please Enter a number from the list");
				administratorMenu();
		}
	}
	
	public void adminAuthorMenu() {
		System.out.println("What author related action would you like to do?");
		System.out.println("1) Add \n2) Update \n3) Delete \n4) List all");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
			case 1:
				adminAddAuthorMenu();
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			default:
				
				break;
				
		}
	}

	public void adminBookMenu() {
		System.out.println("What book related action would you like to do?");
		System.out.println("1) Add \n2) Update \n3) Delete \n4) List all");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
			case 1:
				adminAddBookMenu();
				break;
			case 2:
				adminUpdateBookMenu();
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			default:
				
				break;
		}
	}
	
	private void adminBorrowerMenu() {
		System.out.println("What borrower related action would you like to do?");
		System.out.println("1) Add \n2) Update \n3) Delete \n4) List all");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
			case 1:
				adminAddBorrowerMenu();
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			default:
				
				break;
		}
	}

	private void adminGenreMenu() {
		System.out.println("What genre related action would you like to do?");
		System.out.println("1) Add \n2) Update \n3) Delete \n4) List all");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
			case 1:
				adminAddGenreMenu();
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			default:
				
				break;
		}
	}

	private void adminLibraryBranchMenu() {
		System.out.println("What library branch related action would you like to do?");
		System.out.println("1) Add \n2) Update \n3) Delete \n4) List all");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
			case 1:
				adminAddLibraryBranchMenu();
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			default:
				
				break;	
		}
	}

	private void adminLoanMenu() {
		System.out.println("Would you like to override a duedate?");
		System.out.println("1) Yes \n2) Go back");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
			case 1:
				adminOverrideDueDate();
				break;
			case 2:
				administratorMenu();
				break;
			default:
				System.out.println("Please choose an option from the list.");
				adminLoanMenu();
				break;	
		}
	}

	private void adminPublisherMenu() {
		System.out.println("What publisher related action would you like to do?");
		System.out.println("1) Add \n2) Update \n3) Delete \n4) List all");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
			case 1:
				adminAddPublisherMenu();
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			default:
				
				break;
				
		}		
	}

	public Author adminAddAuthorMenu() {
		System.out.println("Enter the name of the new Author:");
		String newAuthorName = helper.acceptProperLengthInput(scan, 45);
		Author newAuthor = new Author();
		newAuthor.setAuthorName(newAuthorName);
		
		return dbAddAuthor(newAuthor);
	}

	public Book adminAddBookMenu() {
		System.out.println("Please enter the Title of the new book:");
		String newBookTitle = helper.acceptProperLengthInput(scan, 45);
		Publisher newBookPublisher = selectPublisher();
		//AuthorBlock
		List<Author> authors = new ArrayList<>();
		boolean shouldAddAuthor = true;
		while(shouldAddAuthor) {
			
			Author authorToAdd = selectAuthor(null);
			if(authors.contains(authorToAdd)) {
				System.out.println("Please select a different author that hasn't already been associated");
			} else {
				//maybe add print out for selected author
				authors.add(authorToAdd); 
			}
				
			System.out.println("Do you want to add another author? (y/n)");
			shouldAddAuthor = helper.handleYesNoPrompt(scan);
		}
		//GenreBlock
		List<Genre> genres = new ArrayList<>();
		boolean shouldAddGenre = true;
		while(shouldAddGenre) {
			Genre genreToAdd = selectGenre(null);
			if(genres.contains(genreToAdd)) {
				System.out.println("Please select a different genre that hasn't already been associated");
			} else {//maybe add print out for selected genre
				genres.add(genreToAdd); 
			}
			System.out.println("Do you want to add another genre? (y/n)");
			shouldAddGenre = helper.handleYesNoPrompt(scan);
		}
		Book newBook = new Book();
		newBook.setAuthors(authors);
		newBook.setGenres(genres);
		newBook.setPublisher(newBookPublisher);
		newBook.setTitle(newBookTitle);
				
		return dbAddBook(newBook);	
	}

	private Borrower adminAddBorrowerMenu() {
		System.out.println("Please enter the name of the new Borrower:");
		String newBorrowerName = helper.acceptProperLengthInput(scan, 45);
		System.out.println("Please enter the address of the borrower");
		String newBorrowerAddress = helper.acceptProperLengthInput(scan, 45);
		System.out.println("Please enter the borrower's phone number");
		String newBorrowerPhone = helper.acceptProperLengthInput(scan, 45);
		
		Borrower newBorrower = new Borrower();
		newBorrower.setBorrowerAddress(newBorrowerAddress);
		newBorrower.setBorrowerName(newBorrowerName);
		newBorrower.setBorrowerPhone(newBorrowerPhone);
		
		return dbAddBorrower(newBorrower);
		
	}

	private Genre adminAddGenreMenu() {
		System.out.println("Enter the name of the new Genre:");
		String newGenreName = helper.acceptProperLengthInput(scan, 45);
		Genre newGenre = new Genre();
		newGenre.setGenreName(newGenreName);
		
		return dbAddGenre(newGenre);
	}

	private LibraryBranch adminAddLibraryBranchMenu() {
		System.out.println("Enter the name of the new library branch:");
		String newLibraryName = helper.acceptProperLengthInput(scan, 45);
		System.out.println("Enter the address of the new branch:");
		String newLibraryAddress = helper.acceptProperLengthInput(scan, 45);
		LibraryBranch newLibraryBranch = new LibraryBranch();
		newLibraryBranch.setBranchAddress(newLibraryAddress);
		newLibraryBranch.setBranchName(newLibraryName);
		
		return dbAddLibraryBranch(newLibraryBranch);
	}

	public Publisher adminAddPublisherMenu() {
		System.out.println("Enter the name of the new publisher:");
		String newPublisherName = helper.acceptProperLengthInput(scan, 45);
		System.out.println("Enter the address of the new publisher:");
		String newPublisherAddress = helper.acceptProperLengthInput(scan, 45);
		Publisher newPublisher = new Publisher();
		newPublisher.setPublisherAddress(newPublisherAddress);
		newPublisher.setPublisherName(newPublisherName);
		
		return dbAddPublisher(newPublisher);
	}
	
	
	private void adminOverrideDueDate() {
		// TODO Auto-generated method stub
		
	}

	private Book adminUpdateBookMenu() {
		Book bookToUpdate = selectBook();
		boolean shouldMakeChanges = true;
		while(shouldMakeChanges) {
			System.out.println("Do want do you want to change?");
			System.out.println("1) Title \n2) Authors \n3) Genres \n4) Publisher");
			int userInput = -1;
			boolean isInputInRange = false;
			while(!isInputInRange) {
				userInput = helper.readIntInput(scan);
				if(userInput < 1 || userInput > 4) {
					System.out.println("Please select a number from the list");
				} else {
					isInputInRange = true;
				}
			}
			switch(userInput) {
				case 1:
					System.out.println("Enter the new Title for the book");
					String newTitle = helper.acceptProperLengthInput(scan, 45);
					bookToUpdate.setTitle(newTitle);
					break;
				case 2:
					adminUpdateBookAuthorsMenu(bookToUpdate);
					break;
				case 3:
					adminUpdateBookGenresMenu(bookToUpdate);
					break;
				case 4:
					adminUpdateBookPublisherMenu(bookToUpdate);
					break;
			}
			System.out.println("Do you want to make more changes? (y/n)");
			shouldMakeChanges = helper.handleYesNoPrompt(scan);
		}
		//Push changes to DB
		
		return dbUpdateBook(bookToUpdate);
	}

	private void adminUpdateBookAuthorsMenu(Book bookToUpdate) {
		System.out.println("Do you want to add or remove authors?\n1) Add \n2) Remove");
		int menuSelection = helper.readIntInput(scan);
		switch(menuSelection) {
			case 1:
				List<Author> authors = bookToUpdate.getAuthors();
				boolean shouldAddAuthor = true;
				while(shouldAddAuthor) {
					Author authorToAdd = selectAuthor(null);
					if(authors.contains(authorToAdd)) {
						System.out.println("Please select a different author that hasn't already been associated");
					} else {
						//maybe add print out for selected author
						authors.add(authorToAdd); 
					}
					
					System.out.println("Do you want to add another author? (y/n)");
					shouldAddAuthor = helper.handleYesNoPrompt(scan);
				}
				break;
			case 2:
				List<Author> authorsToRemove = bookToUpdate.getAuthors();
				boolean shouldRemoveAuthor = true;
				while(shouldRemoveAuthor) {
					if(authorsToRemove.size() > 0) {
						Author authorToRemove = selectAuthor(authorsToRemove);
						authorsToRemove.remove(authorToRemove); 
						System.out.println("Do you want to remove another author? (y/n)");
						shouldRemoveAuthor = helper.handleYesNoPrompt(scan);
					} else {
						System.out.println("You do not have any more authors to remove. Returning to update menu.");
						shouldRemoveAuthor = false;
					}
				}
				break;
			default:
				adminUpdateBookAuthorsMenu(bookToUpdate);
				break;
		}
	}
	
	private void adminUpdateBookGenresMenu(Book bookToUpdate) {
		System.out.println("Do you want to add or remove genres?\n1) Add \n2) Remove");
		int menuSelection = helper.readIntInput(scan);
		switch(menuSelection) {
			case 1:
				List<Genre> genres = bookToUpdate.getGenres();
				boolean shouldAddGenre = true;
				while(shouldAddGenre) {
					Genre genreToAdd = selectGenre(null);
					if(genres.contains(genreToAdd)) {
						System.out.println("Please select a different genre that hasn't already been associated");
					} else {
						//maybe add print out for selected author
						genres.add(genreToAdd); 
					}
					
					System.out.println("Do you want to add another genre? (y/n)");
					shouldAddGenre = helper.handleYesNoPrompt(scan);
				}
				break;
			case 2:
				List<Genre> genresToRemove = bookToUpdate.getGenres();
				boolean shouldRemoveGenre = true;
				while(shouldRemoveGenre) {
					if(genresToRemove.size() > 0) {
						Genre genreToRemove = selectGenre(genresToRemove);
						genresToRemove.remove(genreToRemove); 		
						System.out.println("Do you want to remove another genre? (y/n)");
						shouldRemoveGenre = helper.handleYesNoPrompt(scan);
					} else {
						System.out.println("You do not have any more genres to remove. Returning to update menu.");
						shouldRemoveGenre = false;
					}
				}
				break;
			default:
				adminUpdateBookGenresMenu(bookToUpdate);
				break;
		}
	}

	private void adminUpdateBookPublisherMenu(Book bookToUpdate) {
		System.out.println("Select the new publisher for the book");
		Publisher newPublisher = selectPublisher();
		bookToUpdate.setPublisher(newPublisher);
	}

	private Book selectBook() {
		System.out.println("Please select a book from the list: ");
		AdministratorService adminService = new AdministratorService();
		try {
			List<Book> books = adminService.getBooks(null);
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
				System.out.println(listCounter + ") " + b.getTitle() + " by " + listOfAuthors + " Genres: " + listOfGenres + 
					" Published by: " + b.getPublisher().getPublisherName());
				listCounter++;
			}
			System.out.println(listCounter + ") Go Back" );
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
				bookSelected = books.get(userInput);
				return bookSelected;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	private Author dbAddAuthor(Author authorToAdd) {
		// TODO Auto-generated method stub
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.addAuthor(authorToAdd));
		return authorToAdd;
		
	}

	private Book dbAddBook(Book bookToAdd) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.addBook(bookToAdd)); 
		return bookToAdd;
	}
	
	private Borrower dbAddBorrower(Borrower borrowerToAdd) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.addBorrower(borrowerToAdd));
		return borrowerToAdd;
	}

	private Genre dbAddGenre(Genre genreToAdd) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.addGenre(genreToAdd));
		return genreToAdd;

	}

	private LibraryBranch dbAddLibraryBranch(LibraryBranch libraryBranchToAdd) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.addLibraryBranch(libraryBranchToAdd));
		return libraryBranchToAdd;

	}

	private Publisher dbAddPublisher(Publisher publisherToAdd) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.addPublisher(publisherToAdd));
		return publisherToAdd;

	}

	private Book dbUpdateBook(Book bookToUpdate) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.updateBook(bookToUpdate));
		return bookToUpdate;
	}

	public void borrowerMenu() {
		
	}
	
	public void librarianMenu() {
	
	}	
	
	
	public void mainMenu() {
		System.out.println("Welcome to the SS Library Management System. What type of User are you?");
		System.out.println("1) Librarian \n2) Administrator \n3) Borrower");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
		case 1:
			librarianMenu();
			break;
		case 2:
			administratorMenu();
			break;
		case 3:
			borrowerMenu();
			break;
		default:
			System.out.println("Please Enter a number from the list");
			mainMenu();
		}
	}
	
	
	
	public void showBooks() {
		AdministratorService adminService = new AdministratorService();
		List<Book> books = adminService.getBooks(null);
		for (Book b : books) {
			System.out.println("Book Title: " + b.getTitle());
		}
	}
	
	private Author selectAuthor(List<Author> authors) {
		boolean shouldSkipAddingNewAuthor = true;
		if(authors == null) {
			AdministratorService adminService = new AdministratorService();
			System.out.println("Please select an author from the list:");
			authors = adminService.getAuthors(null);
			shouldSkipAddingNewAuthor = false;
		}
		int listCounter = 1;
		for(Author a: authors) {
			System.out.println(listCounter + ") " + a.getAuthorName());
			listCounter++;
		}
		if(!shouldSkipAddingNewAuthor) {
			System.out.println(listCounter + ") " + "Choose this to add a new author");
		} else {
			listCounter--;
		}
		int userInput = -1;
		boolean isInputInRange = false;
		do {
			userInput = helper.readIntInput(scan);
			if(userInput < 1 || userInput > listCounter) {
				System.out.println("Please select a number from 1 to " + listCounter);
			} else {
				isInputInRange = true;
			}
		} while (!isInputInRange);
		if(!shouldSkipAddingNewAuthor && userInput == listCounter) {
			return adminAddAuthorMenu();
		}
		return authors.get(userInput-1);
		
	}
	private Genre selectGenre(List<Genre> genres) {
		boolean shouldSkipAddingNewGenre = true;
		if(genres == null) {
			AdministratorService adminService = new AdministratorService();
			System.out.println("Please select a genre from the list:");
			genres = adminService.getGenres(null);
			shouldSkipAddingNewGenre = false;
		}
		int listCounter = 1;
		for(Genre a: genres) {
			System.out.println(listCounter + ") " + a.getGenreName());
			listCounter++;
		}
		if(!shouldSkipAddingNewGenre) {
			System.out.println(listCounter + ") " + "Choose this to add a new genre");
		} else {
			listCounter--;
		}
		int userInput = -1;
		boolean isInputInRange = false;
		do {
			userInput = helper.readIntInput(scan);
			if(userInput < 1 || userInput > listCounter) {
				System.out.println("Please select a number from 1 to " + listCounter);
			} else {
				isInputInRange = true;
			}
		} while (!isInputInRange);
		if(!shouldSkipAddingNewGenre && userInput == listCounter) {
			return adminAddGenreMenu();
		}
		return genres.get(userInput-1);
	}

	private Publisher selectPublisher() {
		AdministratorService adminService = new AdministratorService();
		System.out.println("Please select a publisher from the list:");
		List<Publisher> publishers = adminService.getPublishers(null);
		int listCounter = 1;
		for (Publisher p : publishers) {
			System.out.println(listCounter + ") " + p.getPublisherName() + " Address: " + p.getPublisherAddress());
			listCounter++;
		}
		System.out.println(listCounter + ") Choose this to add a new publisher");
		//listCounter++;
		//System.out.println(listCounter + ") Return to main admin menu");
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
			return adminAddPublisherMenu();
		}
		return publishers.get(userInput-1);
		
		
		
		
	}

}
