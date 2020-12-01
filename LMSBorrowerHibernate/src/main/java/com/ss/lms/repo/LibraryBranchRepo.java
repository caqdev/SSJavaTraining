package com.ss.lms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.lms.entity.LibraryBranch;

@Repository
public interface LibraryBranchRepo extends JpaRepository<LibraryBranch, Integer> {

	@Query(" FROM LibraryBranch WHERE branchName LIKE %:branchName%")
	List<LibraryBranch> readAllLibraryBranchesByName(@Param("branchName") String searchString);

	@Query(value="SELECT lb.* FROM tbl_library_branch lb WHERE (SELECT COUNT(bc.bookId) FROM tbl_book_copies bc "
			+ "WHERE bc.branchId = lb.branchId AND bc.noOfCopies > 0) > 0", nativeQuery = true)
	List<LibraryBranch> readAllLibraryBranchesWithAvailableBooks();
	
	@Query(value = "SELECT DISTINCT tlb.* FROM tbl_library_branch tlb INNER JOIN tbl_book_loans tbl ON tlb.branchId = tbl.branchId WHERE tbl.cardNo = :cardNo AND tbl.dateIn IS NULL", nativeQuery = true)
	List<LibraryBranch> readAllLibraryBranchesWithLoanedBooks(@Param("cardNo") Integer cardNo);

}
