package com.ss.lms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ss.lms.LmsBorrowerHibernateApplicationTests;
import com.ss.lms.entity.LibraryBranch;

public class LmsBorrowerServiceTests extends LmsBorrowerHibernateApplicationTests {

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
	}

	@Test
	void getLibraryBranchesTest() throws Exception {
		String uri = "/borrower/getLibraryBranches";

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		LibraryBranch[] branchList = super.mapFromJson(content, LibraryBranch[].class);
		assertTrue(branchList.length > 0);
	}
}
