package com.ss.lms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.lms.entity.Publisher;

@Repository
public interface PublisherRepo extends JpaRepository<Publisher, Integer> {

	@Query(" FROM Publisher WHERE publisherName LIKE %:pubName%")
	List<Publisher> readAllPublishersByName(@Param("pubName") String searchString);

}
