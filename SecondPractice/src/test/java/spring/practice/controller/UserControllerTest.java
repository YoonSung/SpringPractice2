package spring.practice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import spring.practice.dao.UserDao;
import spring.practice.domain.User;

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
	
	@Test
	public void createWithAlreadyExistsUserId() throws Exception {
		String userId = "testUserId";
		String password = "testPassword";
		String name = "testName";
		String email = "testEmail";
		
		when(userDao.findById(userId)).thenReturn(new User(userId, password, name, email));
		
		mockMvc.perform(post("/users")
				.param("userId", userId)
				.param("password", password)
				.param("name", name)
				.param("email", email)
		)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("errorMessage"))
			.andExpect(forwardedUrl("/user/form"));
	}

}
