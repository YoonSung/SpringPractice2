package spring.practice.domain;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {

	static Validator validator;
	Set<ConstraintViolation<User>> constraints;
	
	static User testUser;
	
	@BeforeClass
	public static void init() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		
		//testUser = new User("testUserId", "testPassword", "testName", "testEmail");
	}
	
	@Test
	public void emptyUserId() {
		//constraints = validator.validate(new User("", testUser.getPassword(), testUser.getName(), testUser.getEmail()));
	}

}
