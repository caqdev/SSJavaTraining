package com.ss.lms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.ss.lms.entity.BookCopies;
import com.ss.lms.entity.BookCopiesKey;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;

@CrossOrigin
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

	@CrossOrigin(origins = "${message.origin}")
	@RequestMapping(value = "/borrower/getActiveLoansForBorrower/{borrowerCardNo}", method = RequestMethod.GET, produces = "application/json")
	public List<BookLoan> getActiveLoansForBorrower(@PathVariable Integer borrowerCardNo) {
		try {
			List<BookLoan> borrowerActiveLoans = blrepo.getActiveBookLoansForBorrower(borrowerCardNo);
			return borrowerActiveLoans;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "${message.origin}")
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

	@CrossOrigin(origins = "${message.origin}")
	@RequestMapping(value = "/borrower/getBooksAvailableFromBranchForBorrower/{branchId}/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> getBooksAvailableFromBranchForBorrower(@PathVariable Integer branchId,
			@PathVariable Integer cardNo) {
		try {
			List<Book> booksAvailable = brepo.readAvailableBooksAtBranchForBorrower(branchId, cardNo);
			return booksAvailable;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "${message.origin}")
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

	@CrossOrigin(origins = "${message.origin}")
	@RequestMapping(value = "/borrower/getBorrowerById/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getBorrowerById(@PathVariable Integer cardNo) {
		try {
			Optional<Borrower> borrower = borrepo.findById(cardNo);
			ResponseEntity<Object> response;
			if (borrower.isPresent()) {
				response = new ResponseEntity<Object>(borrower.get(), HttpStatus.OK);
			} else {
				response = createDBErrorResponseUnprocessableEntity();
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "${message.origin}")
	@RequestMapping(value = "/borrower/getLibraryBranches", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> getLibraryBranches() {
		try {
			return lbrepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "${message.origin}")
	@RequestMapping(value = "/borrower/getLibraryBranchesWithAvailableBooks", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> getLibraryBranchesWithAvailableBooks() {
		try {
			return lbrepo.readAllLibraryBranchesWithAvailableBooks();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "${message.origin}")
	@RequestMapping(value = "/borrower/getLibraryBranchesForReturn/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> getLibraryBranchesForReturn(@PathVariable Integer cardNo) {
		try {
			return lbrepo.readAllLibraryBranchesWithLoanedBooks(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "${message.origin}")
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

	@CrossOrigin(origins = "${message.origin}")
	@Transactional
	@RequestMapping(value = "/borrower/addNewBookLoan", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addNewBookLoan(@RequestBody BookLoan loan) {
		try {
			// Repo calls here do not clearAutomatically so that bookLoan object can be
			// loaded in it's entirety
			ResponseEntity<Object> response;
			
			LocalDate dateOut = LocalDate.now();
			loan.setDateOut(dateOut);
			loan.setDueDate(dateOut.plusDays(7));
			loan.setDateIn(null);
			blrepo.save(loan);
			
			BookCopiesKey branchBookCopiesKey = new BookCopiesKey();
			branchBookCopiesKey.setBookId(loan.getKey().getBookId());
			branchBookCopiesKey.setBranchId(loan.getKey().getBranchId());
			Optional<BookCopies> copiesToUpdateOptional = bcrepo.findById(branchBookCopiesKey);
			
			if(copiesToUpdateOptional.isPresent()) {
				BookCopies bc = copiesToUpdateOptional.get();
				bc.setNumberOfCopies(bc.getNumberOfCopies()-1);
				bcrepo.save(bc);
				response = new ResponseEntity<Object>(loan, HttpStatus.OK);
			} else {
				response = createDBErrorResponseUnprocessableEntity();
			}

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}

	}

	@CrossOrigin(origins = "${message.origin}")
	@Transactional
	@RequestMapping(value = "/borrower/bookLoanReturn", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> returnBook(@RequestBody BookLoan loan) {
		try {
			// Repo calls here do not clearAutomatically so that bookLoan object can be
			// loaded in it's entirety
			ResponseEntity<Object> response;
		 
			loan.setDateIn(LocalDate.now());
			blrepo.save(loan);
			
			BookCopiesKey branchBookCopiesKey = new BookCopiesKey();
			branchBookCopiesKey.setBookId(loan.getKey().getBookId());
			branchBookCopiesKey.setBranchId(loan.getKey().getBranchId());
			Optional<BookCopies> copiesToUpdateOptional = bcrepo.findById(branchBookCopiesKey);
			
			if(copiesToUpdateOptional.isPresent()) {
				BookCopies bc = copiesToUpdateOptional.get();
				bc.setNumberOfCopies(bc.getNumberOfCopies()+1);
				bcrepo.save(bc);
				response = new ResponseEntity<Object>(loan, HttpStatus.OK);
			} else {
				response = createDBErrorResponseUnprocessableEntity();
			}

			return response;
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
