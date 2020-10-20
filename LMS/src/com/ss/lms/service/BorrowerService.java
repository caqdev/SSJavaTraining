package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.entity.Borrower;

public class BorrowerService {

	public ConnectionUtil conUtil = new ConnectionUtil();
	
	public Borrower getBorrowerById(Integer cardNo) {
		try(Connection conn = conUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			List<Borrower> dbResults = bdao.readBorrowerById(cardNo);
			if(dbResults.size() == 0) {
				return null;
			} else {
				return dbResults.get(0);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
