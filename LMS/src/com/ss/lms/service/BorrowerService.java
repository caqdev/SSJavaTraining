package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BookLoanDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;

public class BorrowerService {

	public ConnectionUtil conUtil = new ConnectionUtil();
	
	public List<Book> getBooksAvailableFromBranch(LibraryBranch branch) {
		try(Connection conn = conUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			List<Book> booksAvailable = bdao.readAvailableBooksAtBranch(branch);
			return booksAvailable;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Book> getBooksAvailableToReturnToBranch(LibraryBranch libraryBranch, Borrower borrower) {
		try(Connection conn = conUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			List<Book> booksAvailable = bdao.readCheckedOutBooksAtBranchForReturn(libraryBranch, borrower);
			return booksAvailable;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Borrower getBorrowerById(Integer cardNo) {
		try(Connection conn = conUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			List<Borrower> dbResults = bdao.readBorrowerById(cardNo);
			if(dbResults.size() == 0) {
				return null;
			} else {
				return dbResults.get(0);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<LibraryBranch> getLibraryBranches() {
		try(Connection conn = conUtil.getConnection()) {
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.readAllLibraryBranches();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<LibraryBranch> getLibraryBranchesForReturn(Borrower borrower) {
		try(Connection conn = conUtil.getConnection()) {
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.readAllLibraryBranchesWithLoanedBooks(borrower);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BookLoan getLoan(Book book, LibraryBranch branch, Borrower borrower) {
		try(Connection conn = conUtil.getConnection()) {
			BookLoanDAO bldao = new BookLoanDAO(conn);
			return bldao.readBookLoan(book, branch, borrower);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String addNewBookLoan(BookLoan loan) {
		try(Connection conn = conUtil.getConnection()) {
			BookLoanDAO bldao = new BookLoanDAO(conn);
			bldao.addBookLoan(loan); 
			BookLoan updated = bldao.readBookLoan(loan);
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			bcdao.subtractBookCopyAtBranch(updated.getBookId(), updated.getBranchId());
			conn.commit();
			return "Loan has processed your due date is " + updated.getDueDate().toString();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to process loan - contact admin";
		}
		
	}

	public String returnBook(BookLoan loan) {
		try(Connection conn = conUtil.getConnection()) {
			BookLoanDAO bldao = new BookLoanDAO(conn);
			bldao.returnBookLoan(loan); 
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			bcdao.addBookCopyAtBranch(loan.getBookId(), loan.getBranchId());
			conn.commit();
			return "Book has been returned";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Unable to return book - contact admin";
		}
	}

}
