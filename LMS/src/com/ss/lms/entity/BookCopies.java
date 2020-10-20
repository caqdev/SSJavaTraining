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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((numberOfCopies == null) ? 0 : numberOfCopies.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookCopies other = (BookCopies) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (numberOfCopies == null) {
			if (other.numberOfCopies != null)
				return false;
		} else if (!numberOfCopies.equals(other.numberOfCopies))
			return false;
		return true;
	}
	
	
}
