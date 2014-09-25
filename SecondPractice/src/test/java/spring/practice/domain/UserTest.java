package spring.practice.domain;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class UserTest {

	static Validator validator;
	Set<ConstraintViolation<User>> constraints;
	
	static User testUser;
	
	@BeforeClass
	public static void init() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		
		testUser = new User("testUserId", "testPassword", "testName", "testEmail@naver.com");
	}
	
	@Test
	public void emptyUserId() {
		constraints = validator.validate(new User("", testUser.getPassword(), testUser.getName(), testUser.getEmail()));
		assertThat(constraints.size(), is(2));
	}
	
	@Test
	public void emptyPassword() {
		constraints = validator.validate(new User(testUser.getUserId(), "", testUser.getName(), testUser.getEmail()));
		assertThat(constraints.size(), is(2));
	}
	
	@Test
	public void emptyName() {
		constraints = validator.validate(new User(testUser.getUserId(), testUser.getPassword(), "", testUser.getEmail()));
		assertThat(constraints.size(), is(1));
	}
	
	@Test
	public void emptyEmail() {
		constraints = validator.validate(new User(testUser.getUserId(), testUser.getPassword(), testUser.getName(), ""));
		assertThat(constraints.size(), is(1));
	}
	
	@Test
	public void invalidEmailFormat() {
		constraints = validator.validate(new User(testUser.getUserId(), testUser.getPassword(), testUser.getName(), "testEmail"));
		assertThat(constraints.size(), is(1));
	}
}
