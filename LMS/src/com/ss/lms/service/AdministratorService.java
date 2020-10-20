package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BookLoanDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.dao.PublisherDAO;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Genre;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.Publisher;

public class AdministratorService {

	public ConnectionUtil conUtil = new ConnectionUtil();

	public String addBook(Book book) {
		try (Connection conn = conUtil.getConnection()) {
			//conn = conUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			AuthorDAO adao = new AuthorDAO(conn);
			GenreDAO gdao = new GenreDAO(conn);
			if (book.getTitle() != null && book.getTitle().length() > 45) {
				return "Book Title cannot be empty and should be at most 45 characters in length";
			}
			book.setBookId(bdao.addBookWithPk(book));
			for (Author a : book.getAuthors()) {
				adao.addBookAuthors(book.getBookId(), a.getAuthorId());
			}
			for(Genre g: book.getGenres()) {
				gdao.addBookGenres(book.getBookId(), g.getGenreId());
			}
			conn.commit();
			return "Book added successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add book - contact admin.";
		} 
	}
	
	public String addAuthor(Author author) {
		try(Connection conn = conUtil.getConnection()){
			AuthorDAO adao = new AuthorDAO(conn);
			if (author.getAuthorName() != null && author.getAuthorName().length() > 45) {
				return "Author name cannot be empty and should be at most 45 characters in length";
			}
			author.setAuthorId(adao.addAuthorWithPk(author));
			conn.commit();
			return "Author added successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add author - contact admin";
		}
	}

	public String addBorrower(Borrower borrower) {
		try(Connection conn = conUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			if(borrower.getBorrowerAddress() != null && borrower.getBorrowerAddress().length() > 45) {
				return "Borrower address cannot be empty and should be at most 45 characters in length";
			}
			if(borrower.getBorrowerName() != null && borrower.getBorrowerName().length() > 45) {
				return "Borrower name cannot be empty and should be at most 45 characters in length";
			}
			if(borrower.getBorrowerPhone() != null && borrower.getBorrowerPhone().length() > 45) {
				return "Borrower phone cannot be empty and should be at most 45 characters in length";
			}
			borrower.setBorrowerCardNo(bdao.addBorrowerWithPk(borrower));
			conn.commit();
			return "Borrower added successfully, their card # is: " + borrower.getBorrowerCardNo();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add borrower - contact admin";
		}
	}

	public String addGenre(Genre genre) {
		try(Connection conn = conUtil.getConnection()){
			GenreDAO gdao = new GenreDAO(conn);
			if (genre.getGenreName() != null && genre.getGenreName().length() > 45) {
				return "Author name cannot be empty and should be at most 45 characters in length";
			}
			genre.setGenreId(gdao.addGenreWithPk(genre));
			conn.commit();
			return "Genre added successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add genre - contact admin";
		}
	}

	public String addLibraryBranch(LibraryBranch libraryBranch) {
		try(Connection conn = conUtil.getConnection()){
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			if (libraryBranch.getBranchName() != null && libraryBranch.getBranchName().length() > 45) {
				return "Branch name cannot be empty and should be at most 45 characters in length";
			}
			if(libraryBranch.getBranchAddress() != null && libraryBranch.getBranchAddress().length() > 45) {
				return "Branch address cannot be empty and should be at most 45 characters in length";
			}
			libraryBranch.setBranchId(lbdao.addLibraryBranchWithPk(libraryBranch));
			conn.commit();
			return "Branch added successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add branch - contact admin";
		}
	}

	public String addPublisher(Publisher publisher) {
		try(Connection conn = conUtil.getConnection()){
			PublisherDAO pdao = new PublisherDAO(conn);
			if (publisher.getPublisherName() != null && publisher.getPublisherName().length() > 45) {
				return "Publisher name cannot be empty and should be at most 45 characters in length";
			}
			if(publisher.getPublisherAddress() != null && publisher.getPublisherAddress().length() > 45) {
				return "Publisher address cannot be empty and should be at most 45 characters in length";
			}
			publisher.setPublisherId(pdao.addPublisherWithPk(publisher));
			conn.commit();
			return "Publisher added successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add publisher - contact admin";
		}
	}

	public String deleteAuthor(Author author) {
		try(Connection conn = conUtil.getConnection()) {
			AuthorDAO adao = new AuthorDAO(conn);
			adao.deleteAuthor(author);
			conn.commit();
			return "Author deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete author - contact admin";
		} 
	}

	public String deleteBook(Book book) {
		try(Connection conn = conUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			bdao.deleteBook(book);
			conn.commit();
			return "Book deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete book - contact admin";
		} 
	}

	public String deleteBorrower(Borrower borrower) {
		try(Connection conn = conUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			bdao.deleteBorrower(borrower);
			conn.commit();
			return "Borrower deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete borrower - contact admin";
		} 
	}

	public String deleteGenre(Genre genre) {
		try(Connection conn = conUtil.getConnection()) {
			GenreDAO gdao = new GenreDAO(conn);
			gdao.deleteGenre(genre);
			conn.commit();
			return "Genre deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete genre - contact admin";
		} 
	}

	public String deleteLibraryBranch(LibraryBranch branch) {
		try(Connection conn = conUtil.getConnection()) {
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			lbdao.deleteLibraryBranch(branch);
			conn.commit();
			return "Library Branch deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete library branch - contact admin";
		}
	}

	public String deletePublisher(Publisher publisher) {
		try(Connection conn = conUtil.getConnection()) {
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.deletePublisher(publisher);
			conn.commit();
			return "Library Branch deleted successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to delete library branch - contact admin";
		}
	}

	public String extendLoan(BookLoan bookLoan, int daysToExtend) {
		try(Connection conn = conUtil.getConnection()) {
			BookLoanDAO bldao = new BookLoanDAO(conn);
			bldao.extendLoanDueDate(bookLoan, daysToExtend); 
			BookLoan updatedLoan = bldao.readBookLoan(bookLoan);
			conn.commit();
			return "Due date has been extended until " + updatedLoan.getDueDate().toString();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to extend loan - contact admin";
		}
	}

	public List<BookLoan> getActiveBookLoans() {
		try(Connection conn = conUtil.getConnection()) {
			BookLoanDAO bldao = new BookLoanDAO(conn);
			return bldao.readActiveBookLoans();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Author> getAuthors(String searchString) {
		try(Connection conn = conUtil.getConnection()) {
			AuthorDAO adao = new AuthorDAO(conn);
			if (searchString != null) {
				return adao.readAllAuthorsByName(searchString);
			} else {
				return adao.readAllAuthors();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Book> getBooks(String searchString) {
		try(Connection conn = conUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			if (searchString != null) {
				return bdao.readAllBooksByName(searchString);
			} else {
				return bdao.readAllBooks();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Book getBookById(Integer bookId) {
		try(Connection conn = conUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			return bdao.readBookById(bookId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Borrower> getBorrowers() {
		try(Connection conn = conUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			return bdao.readAllBorrowers();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Borrower getBorrowerByCardNo(Integer cardNo) {
		try(Connection conn = conUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			List<Borrower> borrowers = bdao.readBorrowerById(cardNo);
			if(borrowers.size() == 0) {
				return null;
			}else {
				return borrowers.get(0);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Genre> getGenres(String searchString) {
		try(Connection conn = conUtil.getConnection()) {
			GenreDAO gdao = new GenreDAO(conn);
			if (searchString != null) {
				return gdao.readAllGenresByName(searchString);
			} else {
				return gdao.readAllGenres();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<LibraryBranch> getLibraryBranches(String searchString) {
		try(Connection conn = conUtil.getConnection()) {
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			if (searchString != null) {
				return lbdao.readAllLibraryBranchesByName(searchString);
			} else {
				return lbdao.readAllLibraryBranches();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public LibraryBranch getLibraryBrancheById(Integer branchId) {
		try(Connection conn = conUtil.getConnection()) {
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.readLibraryBranchById(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Publisher> getPublishers(String searchString) {
		try(Connection conn = conUtil.getConnection()) {
			PublisherDAO pdao = new PublisherDAO(conn);
			if (searchString != null) {
				return pdao.readAllPublishersByName(searchString);
			} else {
				return pdao.readAllPublishers();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String updateAuthor(Author author) {
		try(Connection conn = conUtil.getConnection()) {
			AuthorDAO adao = new AuthorDAO(conn);
			if(author.getAuthorName() != null && author.getAuthorName().length() > 45) {
				return "Author name must not be null and should be at most 45 characters in length";
			}
			adao.updateAuthor(author);
			conn.commit();
			return "Author updated successfully.";
		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String updateBook(Book book) {
		try(Connection conn = conUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			if (book.getTitle() != null && book.getTitle().length() > 45) {
				return "Book Title cannot be empty and should be at most 45 characters in length";
			}
			bdao.updateBook(book);
			AuthorDAO adao = new AuthorDAO(conn);
			Book oldBookFromDB = bdao.readBookById(book.getBookId());
			for(Author a: book.getAuthors()) {
				if(!oldBookFromDB.getAuthors().contains(a)) {
					oldBookFromDB.getAuthors().add(a);
					adao.addBookAuthors(book.getBookId(), a.getAuthorId());
				}
			}
			for(Author a: oldBookFromDB.getAuthors()) {
				if(!book.getAuthors().contains(a)) {
					adao.removeBookAuthors(book.getBookId(), a.getAuthorId());
				}
			}
			GenreDAO gdao = new GenreDAO(conn);
			for(Genre g: book.getGenres()) {
				if(!oldBookFromDB.getGenres().contains(g)) {
					oldBookFromDB.getGenres().add(g);
					gdao.addBookGenres(book.getBookId(), g.getGenreId());
				}
			}
			for(Genre g: oldBookFromDB.getGenres()) {
				if(!book.getGenres().contains(g)) {
					gdao.removeBookGenres(book.getBookId(), g.getGenreId());
				}
			}
			
			conn.commit();
			return "Book Updated Successfully";
		} catch (ClassNotFoundException | IndexOutOfBoundsException | SQLException e) {
			e.printStackTrace();
			return "Unable to update book - contact admin";
		} 
	}

	public String updateBorrower(Borrower borrower) {
		try(Connection conn = conUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			if(borrower.getBorrowerAddress() != null && borrower.getBorrowerAddress().length() > 45) {
				return "Borrower address cannot be empty and should be at most 45 characters in length";
			}
			if(borrower.getBorrowerName() != null && borrower.getBorrowerName().length() > 45) {
				return "Borrower name cannot be empty and should be at most 45 characters in length";
			}
			if(borrower.getBorrowerPhone() != null && borrower.getBorrowerPhone().length() > 45) {
				return "Borrower phone cannot be empty and should be at most 45 characters in length";
			}
			bdao.updateBorrower(borrower);
			conn.commit();
			return "Borrower updated successfully, their card # is: " + borrower.getBorrowerCardNo();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update borrower - contact admin";
		}
	}

	public String updateGenre(Genre genre) {
		try(Connection conn = conUtil.getConnection()){
			GenreDAO gdao = new GenreDAO(conn);
			if (genre.getGenreName() != null && genre.getGenreName().length() > 45) {
				return "Author name cannot be empty and should be at most 45 characters in length";
			}
			gdao.updateGenre(genre);
			conn.commit();
			return "Genre Updated successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update genre - contact admin";
		}
	}

	public String updateLibraryBranch(LibraryBranch libraryBranch) {
		try(Connection conn = conUtil.getConnection()){
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			if (libraryBranch.getBranchName() != null && libraryBranch.getBranchName().length() > 45) {
				return "Branch name cannot be empty and should be at most 45 characters in length";
			}
			if(libraryBranch.getBranchAddress() != null && libraryBranch.getBranchAddress().length() > 45) {
				return "Branch address cannot be empty and should be at most 45 characters in length";
			}
			lbdao.updateLibraryBranch(libraryBranch);
			conn.commit();
			return "Branch updated successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update branch - contact admin";
		}
	}

	public String updatePublisher(Publisher publisher) {
		try(Connection conn = conUtil.getConnection()){
			PublisherDAO pdao = new PublisherDAO(conn);
			if (publisher.getPublisherName() != null && publisher.getPublisherName().length() > 45) {
				return "Publisher name cannot be empty and should be at most 45 characters in length";
			}
			if(publisher.getPublisherAddress() != null && publisher.getPublisherAddress().length() > 45) {
				return "Publisher address cannot be empty and should be at most 45 characters in length";
			}
			pdao.updatePublisher(publisher);
			conn.commit();
			return "Publisher updated successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update publisher - contact admin";
		}
	}

}
