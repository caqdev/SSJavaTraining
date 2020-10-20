package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopies;
import com.ss.lms.entity.LibraryBranch;

public class BookCopiesDAO extends BaseDAO<BookCopies> {

	public BookCopiesDAO(Connection conn) {
		super(conn);
	}

	public void addBookCopies(BookCopies bc) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES (?, ?, ?)", new Object[] {bc.getBookId(), bc.getBranchId(), bc.getNumberOfCopies()});
	}
	
	public void addBookCopyAtBranch(Integer bookId, Integer branchId) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book_copies SET noOfCopies = (noOfCopies+1) WHERE bookID = ? AND branchId = ?;", new Object[] {bookId, branchId});
		
	}
	public List<BookCopies> readBookCopiesAtBranch(Book book, LibraryBranch libraryBranch) throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[] { book.getBookId(), libraryBranch.getBranchId() });
	}
	
	public void updateBookCopies(BookCopies bc) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?", new Object[] { bc.getNumberOfCopies(), bc.getBookId(), bc.getBranchId() });
	}
	public List<BookCopies> extractData(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<BookCopies> copiesList = new ArrayList<>();
		while (rs.next()) {
			BookCopies bc = new BookCopies(rs.getInt("bookId"), rs.getInt("branchId"), rs.getInt("noOfCopies"));
			copiesList.add(bc);
		}
		return copiesList;
	}
	
	public void subtractBookCopyAtBranch(Integer bookId, Integer branchId) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book_copies SET noOfCopies = (noOfCopies-1) WHERE bookID = ? AND branchId = ?;", new Object[] {bookId, branchId});
		
	}
}
