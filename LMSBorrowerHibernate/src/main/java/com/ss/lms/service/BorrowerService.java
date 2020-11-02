package com.ss.lms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ss.lms.repo.BookCopiesRepo;
import com.ss.lms.repo.BookRepo;
import com.ss.lms.repo.BookLoanRepo;
import com.ss.lms.repo.BorrowerRepo;
import com.ss.lms.repo.LibraryBranchRepo;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;

@RestController
public class BorrowerService {

	@Autowired
	public BookRepo brepo;

	@Autowired
	public BookCopiesRepo bcrepo;

	@Autowired
	public BookLoanRepo blrepo;

	@Autowired
	public BorrowerRepo borrepo;

	@Autowired
	public LibraryBranchRepo lbrepo;

	@RequestMapping(value = "/borrower/getBooksAvailableFromBranch/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> getBooksAvailableFromBranch(@PathVariable Integer branchId) {
		try {
			List<Book> booksAvailable = brepo.readAvailableBooksAtBranch(branchId);
			return booksAvailable;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/borrower/getBooksAvailableToReturnToBranch/{branchId}/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> getBooksAvailableToReturnToBranch(@PathVariable Integer branchId, @PathVariable Integer cardNo) {
		try {
			List<Book> booksAvailable = brepo.readCheckedOutBooksAtBranchForReturn(branchId, cardNo);
			return booksAvailable;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/borrower/getBorrowerById/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public Borrower getBorrowerById(@PathVariable Integer cardNo) {
		try {
			Optional<Borrower> borrower = borrepo.findById(cardNo);
			return borrower.isPresent() ? borrower.get() : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/borrower/getLibraryBranches", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> getLibraryBranches() {
		try {
			return lbrepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/borrower/getLibraryBranchesForReturn/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> getLibraryBranchesForReturn(@PathVariable Integer cardNo) {
		try {
			return lbrepo.readAllLibraryBranchesWithLoanedBooks(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/borrower/getSingleLoan/{branchId}/{cardNo}/{bookId}", method = RequestMethod.GET, produces = "application/json")
	public BookLoan getLoan(@PathVariable Integer bookId, @PathVariable Integer branchId,
			@PathVariable Integer cardNo) {
		try {
			return blrepo.getSingleLoan(bookId, cardNo, branchId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	@RequestMapping(value = "/borrower/addNewBookLoan", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addNewBookLoan(@RequestBody BookLoan loan) {
		try {
			// Repo calls here do not clearAutomatically so that bookLoan object can be
			// loaded in it's entirety
			blrepo.addBookLoan(loan.getKey().getBookId(), loan.getKey().getBranchId(), loan.getKey().getCardNo());
			BookLoan updated = blrepo.getSingleLoan(loan.getKey().getBookId(), loan.getKey().getCardNo(),
					loan.getKey().getBranchId());
			bcrepo.subtractBookCopyAtBranch(updated.getBook().getBookId(), updated.getBranch().getBranchId());
			return new ResponseEntity<Object>(updated, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}

	}

	@Transactional
	@RequestMapping(value = "/borrower/bookLoanReturn", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> returnBook(@RequestBody BookLoan loan) {
		try {
			// Repo calls here do not clearAutomatically so that bookLoan object can be
			// loaded in it's entirety
			blrepo.returnBookLoan(loan.getKey().getBookId(), loan.getKey().getBranchId(), loan.getKey().getCardNo());
			bcrepo.addBookCopyAtBranch(loan.getBook().getBookId(), loan.getBranch().getBranchId());
			BookLoan updated = blrepo.getSingleLoan(loan.getKey().getBookId(), loan.getKey().getCardNo(),
					loan.getKey().getBranchId());
			return new ResponseEntity<Object>(updated, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	public ResponseEntity<Object> createDBErrorResponseUnprocessableEntity() {
		DBErrorResponse eResponse = new DBErrorResponse();
		eResponse.setHttpCode(HttpStatus.UNPROCESSABLE_ENTITY);
		eResponse.setHttpStatus("Unprocessable Entity");
		eResponse.setMessage("The requested DB action could not be performed. Please check the structure of the JSON.");
		return new ResponseEntity<Object>(eResponse, eResponse.getHttpCode());
	}
}
