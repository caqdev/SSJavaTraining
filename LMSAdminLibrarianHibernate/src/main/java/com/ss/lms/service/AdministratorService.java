package com.ss.lms.service;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Genre;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.Publisher;
import com.ss.lms.repo.AuthorRepo;
import com.ss.lms.repo.BookLoanRepo;
import com.ss.lms.repo.BookRepo;
import com.ss.lms.repo.BorrowerRepo;
import com.ss.lms.repo.GenreRepo;
import com.ss.lms.repo.LibraryBranchRepo;
import com.ss.lms.repo.PublisherRepo;
import com.ss.lms.request.LoanExtensionRequest;

@RestController
public class AdministratorService {

	@Autowired
	public AuthorRepo arepo;
	
	@Autowired
	public BookRepo brepo;
	
	@Autowired
	public BookLoanRepo blrepo;
	
	@Autowired
	public BorrowerRepo borrepo;
	
	@Autowired
	public GenreRepo grepo;
	
	@Autowired
	public LibraryBranchRepo lbrepo;
	
	@Autowired
	public PublisherRepo prepo;
	
	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addAuthor(@RequestBody Author author) {
		try {
			if (author.getAuthorName() != null && author.getAuthorName().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			author = arepo.save(author);
			return new ResponseEntity<Object>(author, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/addBook", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addBook(@RequestBody Book book) {
		try {
			if (book.getTitle() != null && book.getTitle().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			book = brepo.save(book);
			return new ResponseEntity<Object>(book, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		} 
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/addBorrower", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addBorrower(@RequestBody Borrower borrower) {
		try {
			if(borrower.getBorrowerAddress() != null && borrower.getBorrowerAddress().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			if(borrower.getBorrowerName() != null && borrower.getBorrowerName().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			if(borrower.getBorrowerPhone() != null && borrower.getBorrowerPhone().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			borrower = borrepo.save(borrower);
			return new ResponseEntity<Object>(borrower, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/addGenre", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addGenre(@RequestBody Genre genre) {
		try {
			if (genre.getGenreName() != null && genre.getGenreName().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			genre = grepo.save(genre);
			return new ResponseEntity<Object>(genre, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/addLibraryBranch", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addLibraryBranch(@RequestBody LibraryBranch libraryBranch) {
		try {
			if (libraryBranch.getBranchName() != null && libraryBranch.getBranchName().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			if(libraryBranch.getBranchAddress() != null && libraryBranch.getBranchAddress().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			libraryBranch = lbrepo.save(libraryBranch);
			return new ResponseEntity<Object>(libraryBranch, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/addPublisher", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addPublisher(@RequestBody Publisher publisher) {
		try {
			if (publisher.getPublisherName() != null && publisher.getPublisherName().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			if(publisher.getPublisherAddress() != null && publisher.getPublisherAddress().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			if(publisher.getPublisherPhone() != null && publisher.getPublisherPhone().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			publisher = prepo.save(publisher);//formerly addPublisherWithPk
			return new ResponseEntity<Object>(publisher, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/deleteAuthor", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> deleteAuthor(@RequestBody Author author) {
		try {
			arepo.delete(author);
			return new ResponseEntity<Object>(author, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		} 
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/deleteBook", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> deleteBook(@RequestBody Book book) {
		try {
			brepo.delete(book);
			return new ResponseEntity<Object>(book, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		} 
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/deleteBorrower", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> deleteBorrower(@RequestBody Borrower borrower) {
		try {
			borrepo.delete(borrower);
			return new ResponseEntity<Object>(borrower, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		} 
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/deleteGenre", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> deleteGenre(@RequestBody Genre genre) {
		try {
			grepo.delete(genre);
			return new ResponseEntity<Object>(genre, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		} 
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/deleteLibraryBranch", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> deleteLibraryBranch(@RequestBody LibraryBranch branch) {
		try {
			lbrepo.delete(branch);
			return new ResponseEntity<Object>(branch, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/deletePublisher", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> deletePublisher(@RequestBody Publisher publisher) {
		try {
			prepo.delete(publisher);
			return new ResponseEntity<Object>(publisher, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/extendBookLoan", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> extendLoan(@RequestBody LoanExtensionRequest request) {
		try {
			BookLoan bookLoan = request.getBookLoan();
			Integer daysToExtend = request.getDaysToExtend();
			blrepo.extendLoanDueDate(bookLoan.getBook().getBookId(), bookLoan.getBorrower().getBorrowerCardNo(), bookLoan.getBranch().getBranchId(), daysToExtend); 
			BookLoan updatedLoan = blrepo.getSingleLoan(bookLoan.getBook().getBookId(), bookLoan.getBorrower().getBorrowerCardNo(), bookLoan.getBranch().getBranchId());
			return new ResponseEntity<Object>(updatedLoan, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}
	
	/*
	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/changeLoanDueDate", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> changeLoanDueDate(@RequestBody BookLoan bookLoan) {
		try {
			blrepo.changeLoanDueDate(bookLoan.getBook().getBookId(), bookLoan.getBorrower().getBorrowerCardNo(), bookLoan.getBranch().getBranchId(), bookLoan.getDueDate());
			BookLoan updatedLoan = blrepo.getSingleLoan(bookLoan.getBook().getBookId(), bookLoan.getBorrower().getBorrowerCardNo(), bookLoan.getBranch().getBranchId());
			return new ResponseEntity<Object>(updatedLoan, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}
	*/

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/getActiveBookLoans", method = RequestMethod.GET, produces = "application/json")
	public List<BookLoan> getActiveBookLoans() {
		try {
			return blrepo.getActiveBookLoans();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/getAuthors", method = RequestMethod.GET, produces = "application/json")
	public List<Author> getAuthors(@RequestParam(required = false) String searchString) {
		try {
			if (searchString != null && searchString.length() > 0) {
				return arepo.readAllAuthorsByName(searchString);
			} else {
				return arepo.findAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/getBooks", method = RequestMethod.GET, produces = "application/json")
	public List<Book> getBooks(@RequestParam(required = false) String searchString) {
		List<Book> books = null;
		if (searchString != null && searchString.length() > 0) {
			books = brepo.readAllBooksByTitle(searchString);
		} else {
			books = brepo.findAll();
		}
		return books;
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/getSingleBook/{bookId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getBookById(@PathVariable Integer bookId) {
		try {
			return new ResponseEntity<Object>(brepo.findById(bookId), HttpStatus.OK);
		} catch (Exception e) {
			DBErrorResponse eResponse = new DBErrorResponse();
			eResponse.setHttpCode(HttpStatus.BAD_REQUEST);
			eResponse.setHttpStatus("Bad request, invalid ID");
			eResponse.setMessage("The requested DB action could not be performed. Please check the ID");
			return new ResponseEntity<Object>(eResponse, eResponse.getHttpCode());
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/getBorrowers", method = RequestMethod.GET, produces = "application/json")
	public List<Borrower> getBorrowers() {
		try {
			return borrepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/getSingleBorrower/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getBorrowerByCardNo(@PathVariable Integer cardNo) {
		try {
			Optional<Borrower> borrower = borrepo.findById(cardNo);
			return new ResponseEntity<Object>(borrower, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			DBErrorResponse eResponse = new DBErrorResponse();
			eResponse.setHttpCode(HttpStatus.BAD_REQUEST);
			eResponse.setHttpStatus("Bad request, invalid ID");
			eResponse.setMessage("The requested DB action could not be performed. Please check the ID");
			return new ResponseEntity<Object>(eResponse, eResponse.getHttpCode());
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/getGenres", method = RequestMethod.GET, produces = "application/json")
	public List<Genre> getGenres(@RequestParam(required = false) String searchString) {
		try {
			if (searchString != null && searchString.length() > 0) {
				return grepo.readAllGenresByName(searchString);
			} else {
				return grepo.findAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/getLibraryBranches", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> getLibraryBranches(@RequestParam(required = false) String searchString) {
		try {
			if (searchString != null && searchString.length() > 0) {
				return lbrepo.readAllLibraryBranchesByName(searchString);
			} else {
				return lbrepo.findAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/getSingleLibraryBranch/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getLibraryBranchesById(@PathVariable Integer branchId) {
		try {
			Optional<LibraryBranch> branch = lbrepo.findById(branchId);
			return new ResponseEntity<Object>(branch, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			DBErrorResponse eResponse = new DBErrorResponse();
			eResponse.setHttpCode(HttpStatus.BAD_REQUEST);
			eResponse.setHttpStatus("Bad request, invalid ID");
			eResponse.setMessage("The requested DB action could not be performed. Please check the ID");
			return new ResponseEntity<Object>(eResponse, eResponse.getHttpCode());
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/getPublishers", method = RequestMethod.GET, produces = "application/json")
	public List<Publisher> getPublishers(@RequestParam(required = false) String searchString) {
		try {
			if (searchString != null) {
				return prepo.readAllPublishersByName(searchString);
			} else {
				return prepo.findAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/findGenreName", method = RequestMethod.GET, produces = "application/json")
	public boolean findGenreName(@RequestParam String genreName) {
		if(grepo.findGenreName(genreName).size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/updateAuthor", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updateAuthor(@RequestBody Author author) {
		try {
			if (author.getAuthorName() != null && author.getAuthorName().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			Author updated = arepo.save(author);
			return new ResponseEntity<Object>(updated, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/updateBook", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updateBook(@RequestBody Book book) {
		try {
			if (book.getTitle() != null && book.getTitle().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			Book updated = brepo.save(book);
			return new ResponseEntity<Object>(updated, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		} 
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/updateBorrower", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updateBorrower(@RequestBody Borrower borrower) {
		try {
			if(borrower.getBorrowerAddress() != null && borrower.getBorrowerAddress().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			if(borrower.getBorrowerName() != null && borrower.getBorrowerName().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			if(borrower.getBorrowerPhone() != null && borrower.getBorrowerPhone().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			Borrower updated = borrepo.save(borrower);
			return new ResponseEntity<Object>(updated, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/updateGenre", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updateGenre(@RequestBody Genre genre) {
		try {
			if (genre.getGenreName() != null && genre.getGenreName().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			Genre updated = grepo.save(genre);
			return new ResponseEntity<Object>(updated, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/updateLibraryBranch", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {
		try {
			if (libraryBranch.getBranchName() != null && libraryBranch.getBranchName().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			if(libraryBranch.getBranchAddress() != null && libraryBranch.getBranchAddress().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			LibraryBranch updated = lbrepo.save(libraryBranch);
			return new ResponseEntity<Object>(updated, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return createDBErrorResponseUnprocessableEntity();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Transactional
	@RequestMapping(value = "/updatePublisher", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updatePublisher(@RequestBody Publisher publisher) {
		try {
			if (publisher.getPublisherName() != null && publisher.getPublisherName().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			if(publisher.getPublisherAddress() != null && publisher.getPublisherAddress().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			if(publisher.getPublisherPhone() != null && publisher.getPublisherPhone().length() > 45) {
				return createDBErrorResponseUnprocessableEntity();
			}
			Publisher updated = prepo.save(publisher);
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
