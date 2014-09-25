package spring.practice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import spring.practice.dao.UserDao;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	UserDao userDao;
	
	@InjectMocks
	private UserController userController;
	
	
	@Before
	public void setUp() throws Exception {
		mockMvc = standaloneSetup(userController).build();
	}
	
	@Test
	public void form() throws Exception {
		mockMvc.perform(get("/users/form"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("/user/form"));
	}
	
	@Test
	public void createWithValidParameter() throws Exception {
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
