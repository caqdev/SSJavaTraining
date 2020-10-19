package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower>{

	public BorrowerDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException { //Still needs to be edited
		save("INSERT INTO tbl_borrower (name, address, phone) VALUES (?, ?, ?)", new Object[] { borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone() });
	}
	
	public Integer addBorrowerWithPk(Borrower borrower) throws ClassNotFoundException, SQLException {//Still needs to be edited
		return saveWithPk("INSERT INTO tbl_borrower (name, address, phone) VALUES (?, ?, ?)", new Object[] { borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone() });
	}

	public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {//Still needs to be edited
		save("UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?",
				new Object[] { borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone(), borrower.getBorrowerCardNo() });
	}

	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {//Still needs to be edited
		save("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] { borrower.getBorrowerCardNo() });
	}
	
	public List<Borrower> readAllBorrowers() throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_borrower", null);
	}
	
	public List<Borrower> readAllBorrowersByName(String searchString) throws SQLException, ClassNotFoundException {
		searchString = "%"+searchString+"%";
		return read("SELECT * FROM tbl_borrower WHERE name LIKE ?", new Object[] {searchString});
	}
	
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower b = new Borrower(rs.getInt("cardNo"), rs.getString("name"), rs.getString("address"), rs.getString("phone"));
			borrowers.add(b);
		}
		return borrowers;
	}
}
