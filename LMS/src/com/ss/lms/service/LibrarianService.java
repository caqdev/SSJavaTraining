package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopies;
import com.ss.lms.entity.LibraryBranch;

public class LibrarianService {

	public ConnectionUtil conUtil = new ConnectionUtil();
	
	public String addBookCopies(BookCopies bc) {
		try(Connection conn = conUtil.getConnection()) {
			BookCopiesDAO bdao = new BookCopiesDAO(conn);
			bdao.addBookCopies(bc);
			conn.commit();
			return "Number of copies successfully updated";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update number of copies - contact admin";
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

	public BookCopies getBookCopiesAtBranch(Book book, LibraryBranch libraryBranch) {
		try(Connection conn = conUtil.getConnection()) {
			BookCopiesDAO bdao = new BookCopiesDAO(conn);
			List<BookCopies> results = bdao.readBookCopiesAtBranch(book, libraryBranch);
			if(results.size() == 0) {
				return null;
			} else {
				return results.get(0);
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

	public String updateBookCopies(BookCopies bc) {
		try(Connection conn = conUtil.getConnection()) {
			BookCopiesDAO bdao = new BookCopiesDAO(conn);
			bdao.updateBookCopies(bc);
			conn.commit();
			return "Number of copies successfully updated";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to update number of copies - contact admin";
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

}
