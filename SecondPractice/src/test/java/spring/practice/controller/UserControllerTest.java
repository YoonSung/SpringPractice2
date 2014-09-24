package spring.practice.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class UserControllerTest {

	private MockMvc mockMvc;
	
	@Test
	public void form() throws Exception {
		mockMvc = standaloneSetup(new UserController()).build();
		
		mockMvc.perform(get("/users/form"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("/user/form"));
	}

}
