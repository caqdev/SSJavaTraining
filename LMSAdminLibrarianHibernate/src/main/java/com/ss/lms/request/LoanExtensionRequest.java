package com.ss.lms.request;

import java.io.Serializable;

import com.ss.lms.entity.BookLoan;

public class LoanExtensionRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 283826398676233168L;

	private BookLoan bookLoan;
	private Integer daysToExtend;

	public BookLoan getBookLoan() {
		return bookLoan;
	}

	public void setBookLoan(BookLoan bookLoan) {
		this.bookLoan = bookLoan;
	}

	public Integer getDaysToExtend() {
		return daysToExtend;
	}

	public void setDaysToExtend(Integer daysToExtend) {
		this.daysToExtend = daysToExtend;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookLoan == null) ? 0 : bookLoan.hashCode());
		result = prime * result + ((daysToExtend == null) ? 0 : daysToExtend.hashCode());
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
		LoanExtensionRequest other = (LoanExtensionRequest) obj;
		if (bookLoan == null) {
			if (other.bookLoan != null)
				return false;
		} else if (!bookLoan.equals(other.bookLoan))
			return false;
		if (daysToExtend == null) {
			if (other.daysToExtend != null)
				return false;
		} else if (!daysToExtend.equals(other.daysToExtend))
			return false;
		return true;
	}

}
