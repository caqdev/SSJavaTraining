package com.ss.lms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.lms.entity.Genre;

@Repository
public interface GenreRepo extends JpaRepository<Genre, Integer> {

	@Query(" FROM Genre WHERE genreName LIKE %:genreName%")
	List<Genre> readAllGenresByName(@Param("genreName") String genreName);
	
	@Query(" FROM Genre WHERE genreName = :genreName")
	List<Genre> findGenreName(@Param("genreName") String genreName);

}
