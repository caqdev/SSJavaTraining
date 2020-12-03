package com.ss.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ss.lms.repo.BookCopiesRepo;
import com.ss.lms.repo.BookRepo;
import com.ss.lms.repo.LibraryBranchRepo;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopies;
import com.ss.lms.entity.LibraryBranch;

@RestController
public class LibrarianService {

	@Autowired
	public BookRepo brepo;

	@Autowired
	public BookCopiesRepo bcrepo;

	@Autowired
	public LibraryBranchRepo lbrepo;

	@CrossOrigin(origins = "${message.origin}")
	@Transactional
	@RequestMapping(value = "/librarian/addBookCopies", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addBookCopies(@RequestBody BookCopies bc) {
		try {
			bcrepo.addBookCopies(bc.getBook().getBookId(), bc.getBranch().getBranchId(), bc.getNumberOfCopies());
			return new ResponseEntity<Object>(bc, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "${message.origin}")
	@Transactional
	@RequestMapping(value = "/librarian/setBookCopies", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> setBookCopies(@RequestBody BookCopies bc) {
		try {
			bcrepo.setBookCopies(bc.getBook().getBookId(), bc.getBranch().getBranchId(), bc.getNumberOfCopies());
			return new ResponseEntity<Object>(bc, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "${message.origin}")
	@RequestMapping(value = "/librarian/readBranchCopies", method = RequestMethod.GET, produces = "application/json")
	public List<BookCopies> getBranchCopies(@RequestParam Integer branchId) {
		try {
			return bcrepo.readBranchBookCopies(branchId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "${message.origin}")
	@RequestMapping(value = "/librarian/readNonBranchCopies", method = RequestMethod.GET, produces = "application/json")
	public List<Book> getNonBranchCopies(@RequestParam Integer branchId) {
		try {
			return brepo.readNotAvailableBooksAtBranch(branchId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "${message.origin}")
	@RequestMapping(value = "/librarian/getBooks", method = RequestMethod.GET, produces = "application/json")
	public List<Book> getBooks(@RequestParam(required = false) String searchString) {
		List<Book> books = null;
		if (searchString != null && searchString.length() > 0) {
			books = brepo.readAllBooksByTitle(searchString);
		} else {
			books = brepo.findAll();
		}
		return books;
	}

	@CrossOrigin(origins = "${message.origin}")
	@RequestMapping(value = "/librarian/getBookCopiesFromBranch", method = RequestMethod.GET, produces = "application/json")
	public BookCopies getBookCopiesAtBranch(@RequestParam int bookId, @RequestParam int branchId) {
		try {
			return bcrepo.readBranchCopies(bookId, branchId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "${message.origin}")
	@RequestMapping(value = "/librarian/getLibraryBranches", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> getLibraryBranches(@RequestParam(required = false) String searchString) {
		try {
			if (searchString != null) {
				return lbrepo.readAllLibraryBranchesByName(searchString);
			} else {
				return lbrepo.findAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "${message.origin}")
	@Transactional
	@RequestMapping(value = "/librarian/updateBookCopies", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updateBookCopies(@RequestBody BookCopies bc) {
		try {
			BookCopies updated = bcrepo.save(bc);
			return new ResponseEntity<Object>(updated, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "${message.origin}")
	@Transactional
	@RequestMapping(value = "/librarian/updateLibraryBranch", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {
		try {
			if (libraryBranch.getBranchName() != null && libraryBranch.getBranchName().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			if (libraryBranch.getBranchAddress() != null && libraryBranch.getBranchAddress().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			LibraryBranch updated = lbrepo.save(libraryBranch);
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
