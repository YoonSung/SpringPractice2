package spring.practice.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class UserControllerTest {

	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = standaloneSetup(new UserController()).build();
	}
	
	@Test
	public void form() throws Exception {
		mockMvc.perform(get("/users/form"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("/user/form"));
	}
	
	@Test
	public void create() throws Exception {
		mockMvc.perform(post("/users")
				.param("userId", "TestName")
				.param("password", "testPassword")
				.param("name", "testName")
				.param("email", "testEmail")
		)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/"));
	}

}
