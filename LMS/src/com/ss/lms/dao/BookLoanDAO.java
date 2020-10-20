package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;

public class BookLoanDAO extends BaseDAO<BookLoan>{

	public BookLoanDAO(Connection conn) {
		super(conn);
	}

	
	public void addBookLoan(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?, ?, ?, now(),date_add(now(), INTERVAL 7 DAY))", 
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo()});	
	}

	public void extendLoanDueDate(BookLoan bookLoan, int daysToExtend) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book_loans SET dueDate = (DATE_ADD(dueDate, INTERVAL ? DAY)) WHERE bookId = ? AND branchId = ? AND cardNo = ?",
			new Object[] { daysToExtend, bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo() });
		
	}


	public List<BookLoan> readActiveBookLoans() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_loans WHERE dateIn IS NULL", new Object[] {});
	}


	public BookLoan readBookLoan(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?", new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo() }).get(0);
	}
	
	public BookLoan readBookLoan(Book book, LibraryBranch branch, Borrower borrower) throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?", new Object[] { book.getBookId(), branch.getBranchId(), borrower.getBorrowerCardNo() }).get(0);
	}
	
	public void returnBookLoan(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book_loans SET dateIn = now() WHERE bookId = ? AND branchId = ? AND cardNo = ?",
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo() });
	}
	
	@Override
	public List<BookLoan> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<BookLoan> loans = new ArrayList<>();
		while(rs.next()) {
			BookLoan l = new BookLoan(rs.getInt("bookId"), rs.getInt("branchId"), rs.getInt("cardNo"), rs.getDate("dateOut"), rs.getDate("dueDate"));
			loans.add(l);
		}
		return loans;
	}
}
