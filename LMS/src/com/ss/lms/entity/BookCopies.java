package com.ss.lms.entity;

public class BookCopies {

	private Integer bookId;
	private Integer branchId;
	private Integer numberOfCopies;
	
	public BookCopies(Integer bookId, Integer branchId, Integer numberOfCopies) {
		super();
		this.bookId = bookId;
		this.branchId = branchId;
		this.numberOfCopies = numberOfCopies;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getNumberOfCopies() {
		return numberOfCopies;
	}

	public void setNumberOfCopies(Integer numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}
	
	
}
