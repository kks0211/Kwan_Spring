package org.kwan.controller;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kwan.domain.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginVOTest {
	
	
	private static final Logger logger = LoggerFactory.getLogger(LoginVOTest.class);
	
	private static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void userIdtest() {
		LoginVO login = new LoginVO("", "qwer", "qwer", "qwer@qwe");
		
		Set<ConstraintViolation<LoginVO>> constraitViolations = validator.validate(login);
		assertThat(constraitViolations.size(), is(2));
		
		for(ConstraintViolation<LoginVO> constraitViolation : constraitViolations) {
			logger.info("vioration error : {}" , constraitViolation.getMessage());
		}
		
	}

}
