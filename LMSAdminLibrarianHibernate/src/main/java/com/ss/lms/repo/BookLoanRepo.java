package com.ss.lms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.BookLoanKey;

@Repository
public interface BookLoanRepo extends JpaRepository<BookLoan, BookLoanKey> {

//	@Query("UPDATE BookLoan SET dueDate = (DATE_ADD(dueDate, INTERVAL ? DAY)) WHERE bookId = ? AND branchId = ? AND cardNo = ?")
//	void extendLoanDueDate(BookLoan bookLoan, int daysToExtend);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE tbl_book_loans SET dueDate = (DATE_ADD(dueDate, INTERVAL :daysToExtend DAY)) WHERE bookId = :bookId AND branchId = :branchId AND cardNo = :cardNo", nativeQuery = true)
	void extendLoanDueDate(@Param("bookId") Integer bookId, @Param("cardNo") Integer borrowerCardNo,
			@Param("branchId") Integer branchId, @Param("daysToExtend") Integer daysToExtend);

	@Query(" FROM BookLoan WHERE bookId = :bookId AND branchId = :branchId AND cardNo = :cardNo")
	BookLoan getSingleLoan(@Param("bookId") Integer bookId, @Param("cardNo") Integer borrowerCardNo,
			@Param("branchId") Integer branchId);

	@Query(" FROM BookLoan WHERE dateIn IS NULL")
	List<BookLoan> getActiveBookLoans();

	@Query(" FROM BookLoan WHERE cardNo = :cardNo AND dateIn IS NULL")
	List<BookLoan> getActiveBookLoansForBorrower(@Param("cardNo") Integer borrowerCardNo);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES (:bookId, :branchId, :cardNo, now(),date_add(now(), INTERVAL 7 DAY))", nativeQuery = true)
	void addBookLoan(@Param("bookId") Integer bookId, @Param("branchId") Integer branchId,
			@Param("cardNo") Integer cardNo);

	@Modifying
	@Transactional
	@Query(value = "UPDATE tbl_book_loans SET dateIn = now() WHERE bookId = :bookId AND branchId = :branchId AND cardNo = :cardNo", nativeQuery = true)
	void returnBookLoan(@Param("bookId") Integer bookId, @Param("branchId") Integer branchId,
			@Param("cardNo") Integer cardNo);

	/* change due date */
	@Modifying
	@Transactional
	@Query(value = "UPDATE tbl_book_loans SET dueDate = :dueDate WHERE bookId = :bookId AND branchId = :branchId AND cardNo = :cardNo", nativeQuery = true)
	void returnBookLoan(@Param("bookId") Integer bookId, @Param("branchId") Integer branchId,
			@Param("cardNo") Integer cardNo, @Param("dueDate") Integer dueDate);

}
