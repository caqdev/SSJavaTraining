/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Author;

/**
 * @author ppradhan
 *
 */
public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) {
		super(conn);
	}

	public void addAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_author (authorName) VALUES (?)", new Object[] { author.getAuthorName() });
	}

	public Integer addAuthorWithPk(Author author) throws ClassNotFoundException, SQLException {
		return saveWithPk("INSERT INTO tbl_author (authorName) VALUES (?)", new Object[] { author.getAuthorName() });
	}

	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_author SET authorName = ? WHERE authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_author WHERE authorId = ?", new Object[] { author.getAuthorId() });
	}

	public List<Author> readAllAuthors() throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_author", null);
	}
	
	public List<Author> readAllAuthorsByName(String searchString) throws SQLException, ClassNotFoundException {
		searchString = "%"+searchString+"%";
		return read("SELECT * FROM tbl_author WHERE authorName LIKE ?", new Object[] {searchString});
	}
	
	public void addBookAuthors(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[] { bookId, authorId });
	}
	
	public void removeBookAuthors(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_authors WHERE bookId = ? AND authorId = ?", new Object[] { bookId, authorId });
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			authors.add(new Author(rs.getInt("authorId"), rs.getString("authorName")));
			//also populate the books written by this Author
		}
		return authors;
	}
}
