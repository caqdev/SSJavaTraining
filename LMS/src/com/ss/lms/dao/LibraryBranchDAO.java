/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.LibraryBranch;

/**
 * @author ppradhan
 *
 */
public class LibraryBranchDAO extends BaseDAO<LibraryBranch>{

	public LibraryBranchDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void addLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException { //Still needs to be edited
		save("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?, ?)", new Object[] { libraryBranch.getBranchName(), libraryBranch.getBranchAddress() });
	}
	
	public Integer addLibraryBranchWithPk(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {//Still needs to be edited
		return saveWithPk("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?, ?)", new Object[] { libraryBranch.getBranchName(), libraryBranch.getBranchAddress() });
	}

	public void updateLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {//Still needs to be edited
		save("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?",
				new Object[] { libraryBranch.getBranchName(), libraryBranch.getBranchAddress(), libraryBranch.getBranchId() });
	}

	public void deleteLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {//Still needs to be edited
		save("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] { libraryBranch.getBranchId() });
	}
	
	public List<LibraryBranch> readAllLibraryBranchs() throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_library_branch", null);
	}
	
	public List<LibraryBranch> readAllLibraryBranchsByName(String searchString) throws SQLException, ClassNotFoundException {
		searchString = "%"+searchString+"%";
		return read("SELECT * FROM tbl_library_branch WHERE branchName LIKE ?", new Object[] {searchString});
	}
	
	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<LibraryBranch> branches = new ArrayList<>();
		while (rs.next()) {
			LibraryBranch p = new LibraryBranch(rs.getInt("branchId"), rs.getString("branchName"), rs.getString("branchAddress"));
			branches.add(p);
		}
		return branches;
	}
}
