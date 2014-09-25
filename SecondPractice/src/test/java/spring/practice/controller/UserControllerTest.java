package spring.practice.controller;

import static org.junit.Assert.*;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
		String email = "testEmail@naver.com";
		
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
			.andExpect(model().attributeExists("user"))
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
			//.andDo(print())
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("errorMessage"))
			.andExpect(model().attributeExists("user"))
			.andExpect(forwardedUrl("/user/form"));
	}
	
	@Test
	public void createWithEmptyUserName() throws Exception {
		mockMvc.perform(post("/users"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("user"))
			.andExpect(forwardedUrl("/user/form"));
	}
	
	@Test
	public void loginForm() throws Exception {
		mockMvc.perform(get("/users/login/form"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("authentication"))
			.andExpect(forwardedUrl("/user/login"));
	}
	
	@Test
	public void loginWithValidParameter() throws Exception {
		
		when(userDao.findById(testUser.getUserId())).thenReturn(testUser);
		
		mockMvc.perform(post("/users/login")
				.param("userId", testUser.getUserId())
				.param("password", testUser.getPassword())
				.param("name", testUser.getName())
				.param("email", testUser.getEmail())
		)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void loginWithInvalidParameter() throws Exception {
		
		mockMvc.perform(post("/users/login")
		)
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("authentication"))
			.andExpect(forwardedUrl("/user/login"));
	}
	
	@Test
	public void loginWithNotExistsUserId() throws Exception {
		
		when(userDao.findById(testUser.getUserId())).thenReturn(null);
		
		mockMvc.perform(post("/users/login")
				.param("userId", testUser.getUserId())
				.param("password", testUser.getPassword())
				.param("name", testUser.getName())
				.param("email", testUser.getEmail())
		)
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("authentication"))
			.andExpect(model().attributeExists("errorMessage"))
			.andExpect(forwardedUrl("/user/login"));
	}
	
	@Test
	public void loginWithWrongPassword() throws Exception {
		
		when(userDao.findById(testUser.getUserId())).thenReturn(testUser);
		
		mockMvc.perform(post("/users/login")
				.param("userId", testUser.getUserId())
				.param("password", "wrong")
				.param("name", testUser.getName())
				.param("email", testUser.getEmail())
		)
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("authentication"))
			.andExpect(model().attributeExists("errorMessage"))
			.andExpect(forwardedUrl("/user/login"));
	}
	
	@Test
	public void loginTestForNormalCase() throws Exception {
		
		when(userDao.findById(testUser.getUserId())).thenReturn(testUser);
		
		ResultActions resultActions = mockMvc.perform(post("/users/login")
				.param("userId", testUser.getUserId())
				.param("password", testUser.getPassword())
				.param("name", testUser.getName())
				.param("email", testUser.getEmail())
		)
			.andExpect(status().isFound())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("authentication"))
			.andExpect(redirectedUrl("/"));
		
		MockHttpServletRequest mockRequest = resultActions.andReturn().getRequest();
		MockHttpSession mockSession = (MockHttpSession) mockRequest.getSession();
		
		String userIdFromSession = mockSession.getAttribute("userId").toString();
		
		assertEquals(testUser.getUserId(), userIdFromSession);
	}
	
	@Test
	public void modifyForm() throws Exception {
		
	}
}
