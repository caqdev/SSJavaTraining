package com.ss.lms;

import java.io.IOException;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LmsAdminHibernateApplication.class)
@WebAppConfiguration
public abstract class LmsAdminLibrarianHibernateApplicationTests {

	protected MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	ObjectMapper objectMapper;

	
	protected void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
	    objectMapper.registerModule(new JavaTimeModule());
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper.readValue(json, clazz);
	}

}
