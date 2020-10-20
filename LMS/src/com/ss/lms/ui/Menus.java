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
		System.out.println("1) Books \n2) Authors \n3) Genres \n4) Publishers \n5) Library Branches \n6) Borrowers \n7) Book Loans \n8) Exit admin menu");
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
			case 8:
				return;
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
				adminUpdateAuthorMenu();
				break;
			case 3:
				adminDeleteAuthorMenu();
				break;
			case 4:
				adminDisplayAllAuthors();
				break;
			default:
				adminAuthorMenu();
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
				adminDeleteBookMenu();
				break;
			case 4:
				adminDisplayAllBooks();
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
				adminUpdateBorrowerMenu();
				break;
			case 3:
				adminDeleteBorrowerMenu();
				break;
			case 4:
				adminDisplayAllBorrowers();
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
				adminUpdateGenreMenu();
				break;
			case 3:
				adminDeleteGenreMenu();
				break;
			case 4:
				adminDisplayAllGenres();
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
				adminUpdateLibraryBranchMenu();
				break;
			case 3:
				adminDeleteLibraryBranchMenu();
				break;
			case 4:
				adminDisplayAllLibraryBranches();
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
				adminUpdatePublisherMenu();
				break;
			case 3:
				adminDeletePublisherMenu();
				break;
			case 4:
				adminDisplayAllPublishers();
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
		Publisher newBookPublisher = selectPublisher(false);
		//AuthorBlock
		List<Author> authors = new ArrayList<>();
		boolean shouldAddAuthor = true;
		while(shouldAddAuthor) {
			
			Author authorToAdd = selectAuthor(null, false);
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
			Genre genreToAdd = selectGenre(null, false);
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
	
	private Author adminDeleteAuthorMenu() {
		Author authorToDelete = selectAuthor(null, true);
		System.out.println("Do you want to delete the selected author. (All references in the system to this author will be gone)\n1) Yes \n2) No");
		int userInput = -1;
		boolean isInputInRange = false;
		while(!isInputInRange) {
			userInput = helper.readIntInput(scan);
			if(userInput < 1 || userInput > 2) {
				System.out.println("Please select a number from the list");
			} else {
				isInputInRange = true;
			}
		}
		switch(userInput) {
			case 1:
				AdministratorService adminService = new AdministratorService();
				System.out.println(adminService.deleteAuthor(authorToDelete));
				return authorToDelete;
			case 2:
				return null;
		}
		return null;
	}
	private Book adminDeleteBookMenu() {
		Book bookToDelete = selectBook(true);
		System.out.println("Do you want to delete the selected book. (All references in the system to this book will be gone)\n1) Yes \n2) No");
		int userInput = -1;
		boolean isInputInRange = false;
		while(!isInputInRange) {
			userInput = helper.readIntInput(scan);
			if(userInput < 1 || userInput > 2) {
				System.out.println("Please select a number from the list");
			} else {
				isInputInRange = true;
			}
		}
		switch(userInput) {
			case 1:
				AdministratorService adminService = new AdministratorService();
				System.out.println(adminService.deleteBook(bookToDelete));
				return bookToDelete;
			case 2:
				return null;
		}
		return null;
		
	}

	private Borrower adminDeleteBorrowerMenu() {
		Borrower borrowerToDelete = selectBorrower(true);
		System.out.println("Do you want to delete the selected borrower. (All references in the system to this borrower will be gone)\n1) Yes \n2) No");
		int userInput = -1;
		boolean isInputInRange = false;
		while(!isInputInRange) {
			userInput = helper.readIntInput(scan);
			if(userInput < 1 || userInput > 2) {
				System.out.println("Please select a number from the list");
			} else {
				isInputInRange = true;
			}
		}
		switch(userInput) {
			case 1:
				AdministratorService adminService = new AdministratorService();
				System.out.println(adminService.deleteBorrower(borrowerToDelete));
				return borrowerToDelete;
			case 2:
				return null;
		}
		return null;
	}

	private Genre adminDeleteGenreMenu() {
		Genre genreToDelete = selectGenre(null, true);
		System.out.println("Do you want to delete the selected genre. (All references in the system to this genre will be gone)\n1) Yes \n2) No");
		int userInput = -1;
		boolean isInputInRange = false;
		while(!isInputInRange) {
			userInput = helper.readIntInput(scan);
			if(userInput < 1 || userInput > 2) {
				System.out.println("Please select a number from the list");
			} else {
				isInputInRange = true;
			}
		}
		switch(userInput) {
			case 1:
				AdministratorService adminService = new AdministratorService();
				System.out.println(adminService.deleteGenre(genreToDelete));
				return genreToDelete;
			case 2:
				return null;
		}
		return null;
		
	}

	private LibraryBranch adminDeleteLibraryBranchMenu() {
		LibraryBranch branchToDelete = selectLibraryBranch(true);
		System.out.println("Do you want to delete the selected library branch. (All references in the system to this library branch will be gone)\n1) Yes \n2) No");
		int userInput = -1;
		boolean isInputInRange = false;
		while(!isInputInRange) {
			userInput = helper.readIntInput(scan);
			if(userInput < 1 || userInput > 2) {
				System.out.println("Please select a number from the list");
			} else {
				isInputInRange = true;
			}
		}
		switch(userInput) {
			case 1:
				AdministratorService adminService = new AdministratorService();
				System.out.println(adminService.deleteLibraryBranch(branchToDelete));
				return branchToDelete;
			case 2:
				return null;
		}
		return null;
	}

	private Publisher adminDeletePublisherMenu() {
		Publisher publisherToDelete = selectPublisher(true);
		System.out.println("Do you want to delete the selected publisher. (All references in the system to this publisher will be gone)\n1) Yes \n2) No");
		int userInput = -1;
		boolean isInputInRange = false;
		while(!isInputInRange) {
			userInput = helper.readIntInput(scan);
			if(userInput < 1 || userInput > 2) {
				System.out.println("Please select a number from the list");
			} else {
				isInputInRange = true;
			}
		}
		switch(userInput) {
			case 1:
				AdministratorService adminService = new AdministratorService();
				System.out.println(adminService.deletePublisher(publisherToDelete));
				return publisherToDelete;
			case 2:
				return null;
		}
		return null;
		
	}

	private void adminDisplayAllAuthors() {
		AdministratorService adminService = new AdministratorService();
		List<Author> authors = adminService.getAuthors(null);
		int listCounter = 1;
		for(Author a: authors) {
			System.out.println(listCounter + ") " + a.getAuthorName());
			listCounter++;
		}
		System.out.println("Enter any text to exit");
		helper.acceptProperLengthInput(scan, 9999);
	}

	private void adminDisplayAllBooks() {
		AdministratorService adminService = new AdministratorService();
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
			System.out.println(listCounter + ") " + b.getTitle() + " by " + listOfAuthors + "Genres: " + listOfGenres + 
				"Published by: " + b.getPublisher().getPublisherName());
			listCounter++;
		}
		System.out.println("Enter any text to exit");
		helper.acceptProperLengthInput(scan, 9999);
		
	}

	private void adminDisplayAllBorrowers() {
		AdministratorService adminService = new AdministratorService();
		List<Borrower> borrowers = adminService.getBorrowers();
		int listCounter = 1;
		for(Borrower b: borrowers) {
			System.out.println(listCounter + ") " + b.getBorrowerName() + " Address: " + b.getBorrowerAddress() + " Phone: " + b.getBorrowerPhone());
			listCounter++;
		}
		System.out.println("Enter any text to exit");
		helper.acceptProperLengthInput(scan, 9999);

	}

	private void adminDisplayAllGenres() {
		AdministratorService adminService = new AdministratorService();
		List<Genre> genres = adminService.getGenres(null);
		int listCounter = 1;
		for(Genre a: genres) {
			System.out.println(listCounter + ") " + a.getGenreName());
			listCounter++;
		}
		System.out.println("Enter any text to exit");
		helper.acceptProperLengthInput(scan, 9999);
	}

	private void adminDisplayAllLibraryBranches() {
		AdministratorService adminService = new AdministratorService();
		List<LibraryBranch> libraryBranches = adminService.getLibraryBranches(null);
		int listCounter = 1;
		for (LibraryBranch lb : libraryBranches) {
			System.out.println(listCounter + ") " + lb.getBranchName() + " Address: " + lb.getBranchAddress());
			listCounter++;
		}
		System.out.println("Enter any text to exit");
		helper.acceptProperLengthInput(scan, 9999);
	}

	private void adminDisplayAllPublishers() {
		AdministratorService adminService = new AdministratorService();
		List<Publisher> publishers = adminService.getPublishers(null);
		int listCounter = 1;
		for (Publisher p : publishers) {
			System.out.println(listCounter + ") " + p.getPublisherName() + " Address: " + p.getPublisherAddress());
			listCounter++;
		}
		System.out.println("Enter any text to exit");
		helper.acceptProperLengthInput(scan, 9999);
	}

	private void adminOverrideDueDate() {
		// TODO Auto-generated method stub
		
	}

	private Author adminUpdateAuthorMenu() {
		Author authorToUpdate = selectAuthor(null, true);
		System.out.println("Enter the new name for the Author:");
		String newAuthorName = helper.acceptProperLengthInput(scan, 45);
		authorToUpdate.setAuthorName(newAuthorName);
		
		return dbUpdateAuthor(authorToUpdate);
	}
	
	private Book adminUpdateBookMenu() {
		Book bookToUpdate = selectBook(true);
		boolean shouldMakeChanges = true;
		while(shouldMakeChanges) {
			System.out.println("What do want do you want to change?");
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
			System.out.println("Do you want to make more changes to this book? (y/n)");
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
					Author authorToAdd = selectAuthor(null, true);
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
						Author authorToRemove = selectAuthor(authorsToRemove, true);
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
					Genre genreToAdd = selectGenre(null, true);
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
						Genre genreToRemove = selectGenre(genresToRemove, true);
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
		Publisher newPublisher = selectPublisher(true);
		bookToUpdate.setPublisher(newPublisher);
	}

	private Borrower adminUpdateBorrowerMenu() {
		Borrower borrowerToUpdate = selectBorrower(true);
		int menuSelection = -1;
		boolean shouldMakeChanges = true;
		
		while(shouldMakeChanges) {
			System.out.println("What borrower information do you wish to update?");
			System.out.println("1) Name\n2) Address \n3) Phone \n4) Go back (Uncommitted changes will be lost)");
			menuSelection = helper.readIntInput(scan);
			boolean isInputInRange = false;
			while(!isInputInRange) {
				if(menuSelection < 1 || menuSelection > 4) {
					System.out.println("Please select a number from the list");
				} else {
					isInputInRange = true;
				}
			}
			switch(menuSelection) {
				case 1:
					System.out.println("Enter the new name for the borrower:");
					String newBorrowerName = helper.acceptProperLengthInput(scan, 45);
					borrowerToUpdate.setBorrowerName(newBorrowerName);
					break;
				case 2:
					System.out.println("Enter the new Address for the borrower:");
					String newBorrowerAddress = helper.acceptProperLengthInput(scan, 45);
					borrowerToUpdate.setBorrowerAddress(newBorrowerAddress);
					break;
				case 3:
					System.out.println("Enter the new phone number for the borrower:");
					String newBorrowerPhone = helper.acceptProperLengthInput(scan, 45);
					borrowerToUpdate.setBorrowerPhone(newBorrowerPhone);
					break;
				case 4:
					return null;
			}
			System.out.println("Do you want to make more changes to this borrower? (y/n)");
			shouldMakeChanges = helper.handleYesNoPrompt(scan);
		}
		
		return dbUpdateBorrower(borrowerToUpdate);
		
	}

	private Genre adminUpdateGenreMenu() {
		// TODO Auto-generated method stub
		Genre genreToUpdate = selectGenre(null, true);
		System.out.println("Enter the new name for the Genre:");
		String newGenreName = helper.acceptProperLengthInput(scan, 45);
		genreToUpdate.setGenreName(newGenreName);
		
		return dbUpdateGenre(genreToUpdate);
	}

	private LibraryBranch adminUpdateLibraryBranchMenu() {
		LibraryBranch branchToUpdate = selectLibraryBranch(true);
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
		
		return dbUpdateLibraryBranch(branchToUpdate);
		
	}
	
	private Publisher adminUpdatePublisherMenu() {
		Publisher publisherToUpdate = selectPublisher(true);
		int menuSelection = -1;
		boolean shouldMakeChanges = true;
		while(shouldMakeChanges) {
			System.out.println("What publisher information do you wish to update?");
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
					System.out.println("Enter the new name for the publisher:");
					String newPublisherName = helper.acceptProperLengthInput(scan, 45);
					publisherToUpdate.setPublisherName(newPublisherName);
					break;
				case 2:
					System.out.println("Enter the new address for the publisher:");
					String newPublisherAddress = helper.acceptProperLengthInput(scan, 45);
					publisherToUpdate.setPublisherAddress(newPublisherAddress);
					break;
				case 3:
					return null;
			}
			System.out.println("Do you want to make more changes to this library branch? (y/n)");
			shouldMakeChanges = helper.handleYesNoPrompt(scan);
		}
		return dbUpdatePublisher(publisherToUpdate);
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



	private Author dbUpdateAuthor(Author authorToUpdate) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.updateAuthor(authorToUpdate));
		return authorToUpdate;
	}

	private Book dbUpdateBook(Book bookToUpdate) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.updateBook(bookToUpdate));
		return bookToUpdate;
	}

	private Borrower dbUpdateBorrower(Borrower borrowerToUpdate) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.updateBorrower(borrowerToUpdate));
		return borrowerToUpdate;
	}

	private Genre dbUpdateGenre(Genre genreToUpdate) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.updateGenre(genreToUpdate));
		return genreToUpdate;
	}

	private LibraryBranch dbUpdateLibraryBranch(LibraryBranch branchToUpdate) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.updateLibraryBranch(branchToUpdate));
		return branchToUpdate;
	}
	
	private Publisher dbUpdatePublisher(Publisher publisherToUpdate) {
		AdministratorService adminService = new AdministratorService();
		System.out.println(adminService.updatePublisher(publisherToUpdate));
		return publisherToUpdate;
	}	
	
	public void mainMenu() {
		System.out.println("Welcome to the SS Library Management System. What type of User are you?");
		System.out.println("1) Librarian \n2) Administrator \n3) Borrower \n4) Exit");
		int userInput = helper.readIntInput(scan);
		switch(userInput) {
		case 1:
			LibrarianMenu lm = new LibrarianMenu(scan);
			lm.mainLibrarianMenu();
			break;
		case 2:
			administratorMenu();
			break;
		case 3:
			BorrowerMenu bm = new BorrowerMenu(scan);
			bm.borrowerCardNoInput();
			break;
		case 4:
			System.out.println("Thanks for using the app.");
			return;
		default:
			System.out.println("Please Enter a number from the list");
			mainMenu();
		}
		mainMenu();
	}
	
	
	
	public void showBooks() {
		AdministratorService adminService = new AdministratorService();
		List<Book> books = adminService.getBooks(null);
		for (Book b : books) {
			System.out.println("Book Title: " + b.getTitle());
		}
	}
	
	private Author selectAuthor(List<Author> authors, boolean shouldSkipAddingNewAuthor) {
		if(authors == null) {
			AdministratorService adminService = new AdministratorService();
			authors = adminService.getAuthors(null);
		}
		System.out.println("Please select an author from the list:");
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
	private Book selectBook(boolean shouldSkipAddingNewBook) {
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
				System.out.println(listCounter + ") " + b.getTitle() + " by " + listOfAuthors + "Genres: " + listOfGenres + 
					"Published by: " + b.getPublisher().getPublisherName());
				listCounter++;
			}
			if(!shouldSkipAddingNewBook) {
				System.out.println(listCounter + ") " + "Choose this to add a new book");
			} else {
				listCounter--;
			}
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
			if(!shouldSkipAddingNewBook && userInput == listCounter) {
				return null;
			} else {
				bookSelected = books.get(userInput-1);
				return bookSelected;
			}
		} catch (Exception e) {
			return null;
		}
	}

	private Borrower selectBorrower(boolean shouldSkipAddingNewBorrower) {
		AdministratorService adminService = new AdministratorService();
		System.out.println("Please select a borrower to update");
		List<Borrower> borrowers = adminService.getBorrowers();
		
		int listCounter = 1;
		for(Borrower b: borrowers) {
			System.out.println(listCounter + ") " + b.getBorrowerName() + " Address: " + b.getBorrowerAddress() + " Phone: " + b.getBorrowerPhone());
			listCounter++;
		}
		if(!shouldSkipAddingNewBorrower) {
			System.out.println(listCounter + ") " + "Choose this to add a new borrower");
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
		if(!shouldSkipAddingNewBorrower && userInput == listCounter) {
			return adminAddBorrowerMenu();
		}
		return borrowers.get(userInput-1);
		
	}

	private Genre selectGenre(List<Genre> genres, boolean shouldSkipAddingNewGenre) {
		if(genres == null) {
			AdministratorService adminService = new AdministratorService();
			genres = adminService.getGenres(null);
		}
		System.out.println("Please select a genre from the list:");
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

	private LibraryBranch selectLibraryBranch(boolean shouldSkipAddingNewBranch) {
		AdministratorService adminService = new AdministratorService();
		System.out.println("Please select a library branch from the list:");
		List<LibraryBranch> libraryBranches = adminService.getLibraryBranches(null);
		int listCounter = 1;
		for (LibraryBranch lb : libraryBranches) {
			System.out.println(listCounter + ") " + lb.getBranchName() + " Address: " + lb.getBranchAddress());
			listCounter++;
		}
		if(!shouldSkipAddingNewBranch) {
			System.out.println(listCounter + ") " + "Choose this to add a new library branch");
		} else {
			listCounter--;
		}
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
		if(!shouldSkipAddingNewBranch && userInput == listCounter) {
			return adminAddLibraryBranchMenu();
		}
		return libraryBranches.get(userInput-1);
	}

	private Publisher selectPublisher(boolean shouldSkipAddingNewPublisher) {
		AdministratorService adminService = new AdministratorService();
		System.out.println("Please select a publisher from the list:");
		List<Publisher> publishers = adminService.getPublishers(null);
		int listCounter = 1;
		for (Publisher p : publishers) {
			System.out.println(listCounter + ") " + p.getPublisherName() + " Address: " + p.getPublisherAddress());
			listCounter++;
		}
		if(!shouldSkipAddingNewPublisher) {
			System.out.println(listCounter + ") " + "Choose this to add a new publisher");
		} else {
			listCounter--;
		}
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
		if(!shouldSkipAddingNewPublisher && userInput == listCounter) {
			return adminAddPublisherMenu();
		}
		return publishers.get(userInput-1);
		
		
		
		
	}

}
