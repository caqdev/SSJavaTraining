package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.dao.PublisherDAO;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
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
				return "Book Title cannot be empty and should be at most 45 char in length";
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
				return "Author name cannot be empty and should be at most 45 char in length";
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
				return "Borrower address cannot be empty and should be at most 45 char in length";
			}
			if(borrower.getBorrowerName() != null && borrower.getBorrowerName().length() > 45) {
				return "Borrower name cannot be empty and should be at most 45 char in length";
			}
			if(borrower.getBorrowerPhone() != null && borrower.getBorrowerPhone().length() > 45) {
				return "Borrower phone cannot be empty and should be at most 45 char in length";
			}
			borrower.setBorrowerCardNo(bdao.addBorrowerWithPk(borrower));
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
				return "Author name cannot be empty and should be at most 45 char in length";
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
				return "Branch name cannot be empty and should be at most 45 char in length";
			}
			if(libraryBranch.getBranchAddress() != null && libraryBranch.getBranchAddress().length() > 45) {
				return "Branch address cannot be empty and should be at most 45 char in length";
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
				return "Publisher name cannot be empty and should be at most 45 char in length";
			}
			if(publisher.getPublisherAddress() != null && publisher.getPublisherAddress().length() > 45) {
				return "Publisher address cannot be empty and should be at most 45 char in length";
			}
			publisher.setPublisherId(pdao.addPublisherWithPk(publisher));
			conn.commit();
			return "Publisher added successfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to add publisher - contact admin";
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

	public String updateBook(Book bookToUpdate) {
		try(Connection conn = conUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			bdao.updateBook(bookToUpdate);
			AuthorDAO adao = new AuthorDAO(conn);
			Book oldBookFromDB = bdao.readBookById(bookToUpdate.getBookId());
			//use old book and compare to updated book in order to invoke updates for join tables.
			conn.commit();
			return "Book Updated Successfully";
		} catch (ClassNotFoundException | IndexOutOfBoundsException | SQLException e) {
			e.printStackTrace();
			return "Unable to update book - contact admin";
		} 
	}

}
