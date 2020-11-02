/**
 * 
 */
package com.ss.lms.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author ppradhan, caq
 *
 */
@Entity
@Table(name = "tbl_book")
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3165668192838948756L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookId")
	private Integer bookId;

	@Column(name = "title", length = 45)
	@NonNull
	private String title;

	@ManyToMany
	@JoinTable(name = "tbl_book_authors", joinColumns = { @JoinColumn(name = "bookId") }, inverseJoinColumns = {
			@JoinColumn(name = "authorId") })
	private List<Author> authors;

	@ManyToMany
	@JoinTable(name = "tbl_book_genres", joinColumns = { @JoinColumn(name = "bookId") }, inverseJoinColumns = {
			@JoinColumn(name = "genre_id") })
	private List<Genre> genres;

//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "tbl_book_copies", joinColumns = {@JoinColumn(name = "bookId") }, inverseJoinColumns= {@JoinColumn(name = "branchId")})
//	private List<LibraryBranch> libraryBranches;

	@ManyToOne
	@JoinColumn(name = "pubId")
	private Publisher publisher;

	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<BookLoan> loans;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

//	public List<LibraryBranch> getLibraryBranches() {
//		return libraryBranches;
//	}
//
//	public void setLibraryBranches(List<LibraryBranch> libraryBranches) {
//		this.libraryBranches = libraryBranches;
//	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<BookLoan> getLoans() {
		return loans;
	}

	public void setLoans(List<BookLoan> loans) {
		this.loans = loans;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Book other = (Book) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
