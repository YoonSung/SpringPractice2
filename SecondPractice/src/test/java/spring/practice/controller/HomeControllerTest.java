package spring.practice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HomeControllerTest {

	private MockMvc mockMvc;
	
	@Test
	public void root() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();
		
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(forwardedUrl("home"));
	}

}
