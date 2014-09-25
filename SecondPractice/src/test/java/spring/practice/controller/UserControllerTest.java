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
	
	User testUser;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = standaloneSetup(userController).build();
		
		String userId = "testUserId";
		String password = "testPassword";
		String name = "testName";
		String email = "testEmail";
		
		testUser = new User(userId, password, name, email);
	}
	
	@Test
	public void form() throws Exception {
		mockMvc.perform(get("/users/form"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("/user/form"));
	}
	
	@Test
	public void createWithValidParameter() throws Exception {
		
		when(userDao.create(testUser)).thenReturn(1);
		
		mockMvc.perform(post("/users")
				.param("userId", testUser.getUserId())
				.param("password", testUser.getPassword())
				.param("name", testUser.getName())
				.param("email", testUser.getEmail())
		)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void createWithAlreadyExistsUserId() throws Exception {
		
		when(userDao.findById(testUser.getUserId())).thenReturn(testUser);
		
		mockMvc.perform(post("/users")
				.param("userId", testUser.getUserId())
				.param("password", testUser.getPassword())
				.param("name", testUser.getName())
				.param("email", testUser.getEmail())
		)
			//.andDo(print())
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("errorMessage"))
			.andExpect(forwardedUrl("/user/form"));
	}

	@Test
	public void createWithUnExpectedDatabaseError() throws Exception {
		
		when(userDao.create(testUser)).thenReturn(0);
		
		mockMvc.perform(post("/users")
				.param("userId", testUser.getUserId())
				.param("password", testUser.getPassword())
				.param("name", testUser.getName())
				.param("email", testUser.getEmail())
		)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("errorMessage"))
			.andExpect(forwardedUrl("/user/form"));
	}
}
