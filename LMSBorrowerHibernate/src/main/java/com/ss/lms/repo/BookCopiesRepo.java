package com.ss.lms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ss.lms.entity.BookCopies;
import com.ss.lms.entity.BookCopiesKey;

@Repository
public interface BookCopiesRepo extends JpaRepository<BookCopies, BookCopiesKey> {

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES (:bookId, :branchId, :numCopies)", nativeQuery = true)
	void addBookCopies(@Param("bookId") Integer bookId, @Param("branchId") Integer branchId,
			@Param("numCopies") Integer numberOfCopies);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "REPLACE INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES (:bookId, :branchId, :noOfCopies)", nativeQuery = true)
	void setBookCopies(@Param("bookId") Integer bookId, @Param("branchId") Integer branchId,
			@Param("noOfCopies") Integer noOfCopies);
	
	@Query(" FROM BookCopies WHERE bookId = :bookId AND branchId = :branchId")
	BookCopies readBranchCopies (@Param("bookId") Integer bookId, @Param("branchId") Integer branchId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE tbl_book_copies SET noOfCopies = (noOfCopies+1) WHERE bookID = :bookId AND branchId = :branchId", nativeQuery = true)
	void addBookCopyAtBranch(@Param("bookId") Integer bookId, @Param("branchId") Integer branchId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE tbl_book_copies SET noOfCopies = (noOfCopies-1) WHERE bookID = :bookId AND branchId = :branchId", nativeQuery = true)
	void subtractBookCopyAtBranch(@Param("bookId") Integer bookId, @Param("branchId") Integer branchId);

	// add later
	
	@Query(" FROM BookCopies WHERE branchId = :branchId")
	List<BookCopies> readBranchBookCopies(@Param("branchId") Integer branchId);
}
