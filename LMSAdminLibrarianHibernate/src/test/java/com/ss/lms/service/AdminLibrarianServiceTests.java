package com.ss.lms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.ss.lms.LmsAdminLibrarianHibernateApplicationTests;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Genre;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.Publisher;
import com.ss.lms.request.LoanExtensionRequest;

public class AdminLibrarianServiceTests extends LmsAdminLibrarianHibernateApplicationTests {
	
	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
	}
	/* AUTHOR CUD CALLS */
	@Test
	@Transactional
	public void AuthorTests() throws Exception {
		/* CREATE */
	   String uri = "/addAuthor";
	   Author author = new Author();
	   author.setAuthorName("Tony The Tiger");
	   
	   /* converts author object to json object */
	   String inputJson = super.mapToJson(author);
	   
	   /* REST call with content */
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   
	   /* returns author object */
	   String content = mvcResult.getResponse().getContentAsString();
	   assertEquals(true, content.contains("Tony The Tiger"));
	   
	   /* retrieve primary key */
	   Author temp = super.mapFromJson(content, Author.class);
	   Integer authorId = temp.getAuthorId();
	   /* UPDATE */
	   uri = "/updateAuthor";
	   author.setAuthorId(authorId);
	   author.setAuthorName("Thomas Jefferson");
	   inputJson = super.mapToJson(author);
	   mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   content = mvcResult.getResponse().getContentAsString();
	   assertEquals(true, content.contains("Thomas Jefferson"));
	   /* DELETE */
	   uri = "/deleteAuthor";
	   inputJson = super.mapToJson(author);
	   mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
			      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   content = mvcResult.getResponse().getContentAsString();
	}
	
	/* BOOK CUD CALLS */
	@Test
	@Transactional
	public void BookTests() throws Exception {
		/* CREATE */
	   String uri = "/addBook";
	   Book book = new Book();
	   book.setTitle("The Giving Tree");
	   /* set book publisher */
	   Publisher publisher = new Publisher();
	   publisher.setPublisherId(1);
	   book.setPublisher(publisher);
	   
	   String inputJson = super.mapToJson(book);
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   
	   String content = mvcResult.getResponse().getContentAsString();
	   assertEquals(true, content.contains("The Giving Tree"));
	   
		Book temp = super.mapFromJson(content, Book.class);
		Integer bookId = temp.getBookId();
	   /* UPDATE */
	   uri = "/updateBook";
	   book.setBookId(bookId);;
	   book.setTitle("To Kill a Mockingbird");
	   inputJson = super.mapToJson(book);
	   mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   content = mvcResult.getResponse().getContentAsString();
	   assertEquals(true, content.contains("To Kill a Mockingbird"));
	   /* DELETE */
	   uri = "/deleteBook";
	   inputJson = super.mapToJson(book);
	   mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
			      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   content = mvcResult.getResponse().getContentAsString();
	}
		
	/* BORROWER CUD CALLS */
	@Test
	@Transactional
	public void BorrowerTests() throws Exception {
		/* CREATE */
	   String uri = "/addBorrower";
	   Borrower borrower = new Borrower();
	   borrower.setBorrowerName("Thomas Edison");
	   borrower.setBorrowerAddress("12 West Pine Drive");
	   borrower.setBorrowerPhone("123-456-7890");
	   
	   String inputJson = super.mapToJson(borrower);
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   
	   String content = mvcResult.getResponse().getContentAsString();
	   assertEquals(true, 
			   content.contains("Thomas Edison") && 
			   content.contains("12 West Pine Drive") &&
			   content.contains("123-456-7890"));
	   
	   Borrower temp = super.mapFromJson(content, Borrower.class);
	   Integer cardNo = temp.getBorrowerCardNo();
	   /* UPDATE */
	   uri = "/updateBorrower";
	   borrower.setBorrowerCardNo(cardNo);
	   borrower.setBorrowerName("Nikola Tesla");
	   borrower.setBorrowerAddress("15 East Pine Drive");
	   borrower.setBorrowerPhone("431-456-7890");
	   inputJson = super.mapToJson(borrower);
	   mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   content = mvcResult.getResponse().getContentAsString();
	   assertEquals(true, 
			   content.contains("Nikola Tesla") && 
			   content.contains("15 East Pine Drive") &&
			   content.contains("431-456-7890"));
	   /* DELETE */
	   uri = "/deleteBorrower";
	   inputJson = super.mapToJson(borrower);
	   mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
			      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   content = mvcResult.getResponse().getContentAsString();
	}

	/* Library Branch CUD Calls */
	@Test
	@Transactional
	public void BranchTests() throws Exception {
		/* CREATE */
		String uri = "/addLibraryBranch";
		LibraryBranch libraryBranch = new LibraryBranch();
		libraryBranch.setBranchName("Lincoln Library");
		libraryBranch.setBranchAddress("321 Elm Road");

		String inputJson = super.mapToJson(libraryBranch);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(true, content.contains("Lincoln Library") && content.contains("321 Elm Road"));

		LibraryBranch temp = super.mapFromJson(content, LibraryBranch.class);
		Integer libraryBranchId = temp.getBranchId();
		/* UPDATE */
		uri = "/updateLibraryBranch";
		libraryBranch.setBranchId(libraryBranchId);
		libraryBranch.setBranchName("Baguette Library");
		libraryBranch.setBranchAddress("444 Croissant Drive");
		inputJson = super.mapToJson(libraryBranch);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
		assertEquals(true, content.contains("Baguette Library") && content.contains("444 Croissant Drive"));
		/* DELETE */
		uri = "/deleteLibraryBranch";
		inputJson = super.mapToJson(libraryBranch);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
	}

	/* Genre CUD Calls */
	
	@Test
	@Transactional
	public void GenreTests() throws Exception {
		/* CREATE */
		String uri = "/addGenre";
		Genre genre = new Genre();
		genre.setGenreName("Barnacles");

		String inputJson = super.mapToJson(genre);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(true, content.contains("Barnacles"));

		Genre temp = super.mapFromJson(content, Genre.class);
		Integer genreId = temp.getGenreId();
		/* UPDATE */
		uri = "/updateGenre";
		genre.setGenreId(genreId);
		genre.setGenreName("Persimmons");
		inputJson = super.mapToJson(genre);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
		assertEquals(true, content.contains("Persimmons"));
		/* DELETE */
		uri = "/deleteGenre";
		inputJson = super.mapToJson(genre);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
	}

	/* Publisher CUD Calls */
	@Test
    @Transactional
	public void PublisherTests() throws Exception {
		/* CREATE */	
		String uri = "/addPublisher";
		Publisher publisher = new Publisher();
		publisher.setPublisherName("Lincoln");
		publisher.setPublisherAddress("321 Elm Road");
		publisher.setPublisherPhone("312-138-9248");

		String inputJson = super.mapToJson(publisher);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(true,
				content.contains("Lincoln") && content.contains("321 Elm Road") && content.contains("312-138-9248"));
		
		Publisher temp = super.mapFromJson(content, Publisher.class);
		Integer publisherId = temp.getPublisherId();
		/* UPDATE */
		uri = "/updatePublisher";
		publisher.setPublisherId(publisherId);
		publisher.setPublisherName("Baguette");
		publisher.setPublisherAddress("444 Croissant Drive");
		publisher.setPublisherPhone("835-138-9248");
		inputJson = super.mapToJson(publisher);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
		assertEquals(true, content.contains("Baguette") && content.contains("444 Croissant Drive")
				&& content.contains("835-138-9248"));
		/* DELETE */
		uri = "/deletePublisher";
		inputJson = super.mapToJson(publisher);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
	}
	
	/* Book Loan Extension Test */
	@Test
    @Transactional
	void bookLoanExtensionTest() throws Exception {
		/* Retrieve all active book loans */
		String uri = "/getActiveBookLoans";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		BookLoan[] loanList = super.mapFromJson(content, BookLoan[].class);
		assertTrue(loanList.length > 0);
		
		/* retrieve arbitrary active loan */
		BookLoan loan = loanList[0];
		LoanExtensionRequest extension = new LoanExtensionRequest();
		extension.setBookLoan(loan);
		extension.setDaysToExtend(1);
		uri = "/extendBookLoan";
		/* revert extension (-1 day) */
		String inputJson = super.mapToJson(extension);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		assertEquals(200, status);
		/* reduce book loan by 1 day */
		extension.setDaysToExtend(-1);
		inputJson = super.mapToJson(extension);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
	}
		
	/* READ TESTS */
	
	@Test
    @Transactional
	void getLibraryBranchesTest() throws Exception {
		String uri = "/getLibraryBranches";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		LibraryBranch[] branchList = super.mapFromJson(content, LibraryBranch[].class);
		assertTrue(branchList.length > 0);
	}
	
	
	@Test
    @Transactional
	void getActiveBookLoans() throws Exception {
		String uri = "/getActiveBookLoans";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		BookLoan[] loanList = super.mapFromJson(content, BookLoan[].class);
		assertTrue(loanList.length > 0);
	}
	
	
	@Test
    @Transactional
	void getAuthorsTest() throws Exception {
		String uri = "/getAuthors";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Author[] authorList = super.mapFromJson(content, Author[].class);
		assertTrue(authorList.length > 0);
	}
	
	@Test
    @Transactional
	void getBooksTest() throws Exception {
		String uri = "/getBooks";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Book[] bookList = super.mapFromJson(content, Book[].class);
		assertTrue(bookList.length >= 0);
	}
	
	@Test
    @Transactional
	void getBorrowersTest() throws Exception {
		String uri = "/getBorrowers";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Borrower[] borrowerList = super.mapFromJson(content, Borrower[].class);
		assertTrue(borrowerList.length > 0);
	}
	
	@Test
    @Rollback(true)
	void getBranchesTest() throws Exception {
		String uri = "/getLibraryBranches";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		LibraryBranch[] branchList = super.mapFromJson(content, LibraryBranch[].class);
		assertTrue(branchList.length > 0);
	}
	
	@Test
    @Rollback(true)
	void getGenresTest() throws Exception {
		String uri = "/getGenres";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Genre[] genreList = super.mapFromJson(content, Genre[].class);
		assertTrue(genreList.length > 0);
	}
	
	@Test
    @Rollback(true)
	void getPublishersTest() throws Exception {
		String uri = "/getPublishers";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Publisher[] publisherList = super.mapFromJson(content, Publisher[].class);
		assertTrue(publisherList.length > 0);
	}
	
}
