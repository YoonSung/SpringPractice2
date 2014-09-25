package spring.practice.domain;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AuthenticationTest {

	private static Validator validator;
	private Authentication testAuthentication;
	private Set<ConstraintViolation<Authentication>> constraints;
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationTest.class);
	
	@BeforeClass
	public static void init() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@After
	public void print() {
		for (ConstraintViolation<Authentication> constraint: constraints) {
			log.info("Violation Message : {}", constraint.getMessage());
		}
	}
	
	@Before
	public void setUp() {
		this.testAuthentication = new Authentication("testUserId", "testPassword");
	}
	
	@Test
	public void emptyUserId() {
		this.constraints = validator.validate(new Authentication("", testAuthentication.getPassword()));
		assertThat(constraints.size(), is(2));
	}

	@Test
	public void emptyPassword() {
		this.constraints = validator.validate(new Authentication(testAuthentication.getUserId(), ""));
		assertThat(constraints.size(), is(2));
	}
}
