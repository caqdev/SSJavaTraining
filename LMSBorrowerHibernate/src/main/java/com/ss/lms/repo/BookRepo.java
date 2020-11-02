package com.ss.lms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ss.lms.entity.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "INSERT INTO Book (title, publisher) SELECT title, publisher FROM Book")
	Integer duplicateBooks();

	@Query(" FROM Book WHERE title LIKE %:title%")
	List<Book> readAllBooksByTitle(@Param("title") String searchString);

//	@Query(" FROM Book b JOIN BookCopies AS bc WHERE bc.key.branchId = :branchId AND bc.numberOfCopies > 0")
//	List<Book> readAvailableBooksAtBranch(@Param("branchId") Integer branchId);

	@Query(value = "SELECT b.* FROM tbl_book b INNER JOIN tbl_book_copies bc ON b.bookId = bc.bookId WHERE bc.branchId = :branchId AND bc.noOfCopies > 0", nativeQuery = true)
	List<Book> readAvailableBooksAtBranch(@Param("branchId") Integer branchId);

	@Query(value = "SELECT tb.* FROM tbl_book tb INNER JOIN tbl_book_loans tbl ON tb.bookId=tbl.bookId WHERE tbl.branchId = :branchId AND tbl.cardNo = :cardNo AND tbl.dateIn IS NULL", nativeQuery = true)
	List<Book> readCheckedOutBooksAtBranchForReturn(@Param("branchId") Integer branchId,
			@Param("cardNo") Integer cardNo);
}