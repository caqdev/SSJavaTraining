package com.ss.lms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ss.lms.LmsBorrowerHibernateApplicationTests;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.BookLoanKey;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;

public class LmsBorrowerServiceTests extends LmsBorrowerHibernateApplicationTests {

	Logger logger = LoggerFactory.getLogger(LmsBorrowerServiceTests.class);
	
	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
	}

	@Test
	void getActiveLoansForBorrowerTest() throws Exception {
		String uri = "/borrower/getActiveLoansForBorrower/111";
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		BookLoan[] loansList = super.mapFromJson(content, BookLoan[].class);
		assertTrue(loansList.length > 0);
	}
	
	@Test
	void getBooksAvailableFromBranchTest() throws Exception {
		String uri = "/borrower/getBooksAvailableFromBranch/3";
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		BookLoan[] branchList = super.mapFromJson(content, BookLoan[].class);
		assertTrue(branchList.length > 0);
	}
	
	@Test
	void getBooksAvailableFromBranchForBorrowerTest() throws Exception {
		String uri = "/borrower/getBooksAvailableFromBranchForBorrower/3/111";
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		BookLoan[] bookList = super.mapFromJson(content, BookLoan[].class);
		assertTrue(bookList.length > 0);
	}
	
	@Test
	void getBooksAvailableToReturnToBranchTest() throws Exception {
		String uri = "/borrower/getBooksAvailableToReturnToBranch/3/111";
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		BookLoan[] bookList = super.mapFromJson(content, BookLoan[].class);
		assertTrue(bookList.length > 0);
	}
	
	@Test 
	void getBorrowerByIdTest() throws Exception {
		String uri = "/borrower/getBorrowerById/111";
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Borrower borrower = super.mapFromJson(content, Borrower.class);
		assertNotNull(borrower);
	}

	@Test
	void getLibraryBranchesTest() throws Exception {
		String uri = "/borrower/getLibraryBranches";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		LibraryBranch[] branchList = super.mapFromJson(content, LibraryBranch[].class);
		assertTrue(branchList.length > 0);
	}
	
	@Test
	void getLibraryBranchesWithAvailableBooksTest() throws Exception {
		String uri = "/borrower/getLibraryBranchesWithAvailableBooks";
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		LibraryBranch[] branchList = super.mapFromJson(content, LibraryBranch[].class);
		assertTrue(branchList.length > 0);
	}
	
	@Test
	void getLibraryBranchesForReturnTest() throws Exception {
		String uri = "/borrower/getLibraryBranchesForReturn/111";
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		LibraryBranch[] branchList = super.mapFromJson(content, LibraryBranch[].class);
		assertTrue(branchList.length > 0);
	}
	
	@Test
	void getSingleLoanTest() throws Exception {
		String uri = "/borrower/getSingleLoan/3/111/1";
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		BookLoan loan = super.mapFromJson(content, BookLoan.class);
		assertNotNull(loan);
	}
	
	@Test 
	void addNewBookLoanTest() throws Exception {
		String uri = "/borrower/addNewBookLoan";
		BookLoan loanToAdd = new BookLoan();
		Book bookToLoan = new Book();
		bookToLoan.setBookId(1);
		bookToLoan.setTitle("Anton Reiser");
		Borrower loanBorrower = new Borrower();
		loanBorrower.setBorrowerCardNo(111);
		loanBorrower.setBorrowerName("Johnathan Cena");
		
		LibraryBranch checkoutBranch = new LibraryBranch();
		checkoutBranch.setBranchName("Federal Library");
		checkoutBranch.setBranchId(3);

		loanToAdd.setBook(bookToLoan);
		loanToAdd.setBorrower(loanBorrower);
		loanToAdd.setBranch(checkoutBranch);
		
		BookLoanKey blk = new BookLoanKey();
		blk.setBookId(bookToLoan.getBookId());
		blk.setBranchId(checkoutBranch.getBranchId());
		blk.setCardNo(loanBorrower.getBorrowerCardNo());
		loanToAdd.setKey(blk);
		
		String newLoanStr = super.mapToJson(loanToAdd);
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(newLoanStr).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		BookLoan loan = super.mapFromJson(content, BookLoan.class);
		assertNotNull(loan);
		assertTrue(content.contains("Anton Reiser"));
		assertTrue(content.contains("Johnathan Cena"));
		assertTrue(content.contains("Federal Library"));
		assertNull(loan.getDateIn());
	}
	
	@Test
	void bookLoanReturnTest() throws Exception {
		String uri = "/borrower/bookLoanReturn";
		
		BookLoan loanToReturn = new BookLoan();
		Book bookToReturn = new Book();
		bookToReturn.setBookId(1);
		bookToReturn.setTitle("Anton Reiser");
		Borrower loanBorrower = new Borrower();
		loanBorrower.setBorrowerName("Johnathan Cena");
		loanBorrower.setBorrowerCardNo(111);
		LibraryBranch returnBranch = new LibraryBranch();
		returnBranch.setBranchName("Federal Library");
		returnBranch.setBranchId(3);
		
		loanToReturn.setBook(bookToReturn);
		loanToReturn.setBorrower(loanBorrower);
		loanToReturn.setBranch(returnBranch);
		
		BookLoanKey blk = new BookLoanKey();
		blk.setBookId(bookToReturn.getBookId());
		blk.setBranchId(returnBranch.getBranchId());
		blk.setCardNo(loanBorrower.getBorrowerCardNo());
		loanToReturn.setKey(blk);
		
		
		String returnLoanStr = super.mapToJson(loanToReturn);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(returnLoanStr).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		BookLoan loan = super.mapFromJson(content, BookLoan.class);
		assertNotNull(loan);
		assertTrue(content.contains("Anton Reiser"));
		assertTrue(content.contains("Johnathan Cena"));
		assertTrue(content.contains("Federal Library"));
		assertNotNull(loan.getDateIn());
	}
}
