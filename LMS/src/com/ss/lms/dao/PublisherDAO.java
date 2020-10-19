/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Publisher;

/**
 * @author caq
 *
 */
public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void addPublisher(Publisher publisher) throws ClassNotFoundException, SQLException { //Still needs to be edited
		save("INSERT INTO tbl_publisher (publisherName, publisherAddress) VALUES (?, ?)", new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress() });
	}
	
	public Integer addPublisherWithPk(Publisher publisher) throws ClassNotFoundException, SQLException {//Still needs to be edited
		return saveWithPk("INSERT INTO tbl_publisher (publisherName, publisherAddress) VALUES (?, ?)", new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress() });
	}

	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {//Still needs to be edited
		save("UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ? WHERE publisherId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherId() });
	}

	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {//Still needs to be edited
		save("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[] { publisher.getPublisherId() });
	}
	
	public List<Publisher> readAllPublishers() throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_publisher", null);
	}
	
	public List<Publisher> readAllPublishersByName(String searchString) throws SQLException, ClassNotFoundException {
		searchString = "%"+searchString+"%";
		return read("SELECT * FROM tbl_publisher WHERE publisherName LIKE ?", new Object[] {searchString});
	}
	
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher p = new Publisher(rs.getInt("publisherId"), rs.getString("publisherName"), rs.getString("publisherAddress"));
			publishers.add(p);
		}
		return publishers;
	}

}
