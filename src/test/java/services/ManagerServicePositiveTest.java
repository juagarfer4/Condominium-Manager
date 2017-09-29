package services;


import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.mchange.util.AssertException;

import domain.Manager;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ManagerServicePositiveTest extends AbstractTest {

	// Service under test ------------------------------------------------
	
	@Autowired
	private ManagerService managerService;
	
	
	// Tests --------------------------------------------------------------

	
	// Register a new manager. ---------
	
	@Test
	public void testPositiveCreateManager() {
		this.authenticate("administrator1");
		
		Manager manager;
		String name;
		String surname;
		String email;
		String phone;
		UserAccount userAccount;
		String username;
		String password;

		manager = managerService.create();
		name = "name";
		surname = "surname";
		email = "email@us.es";
		phone = "900000000";
		userAccount=manager.getUserAccount();
		username="username";
		password="username";

		manager.setName(name);
		manager.setSurname(surname);
		manager.setEmail(email);
		manager.setPhone(phone);
		userAccount.setUsername(username);
		userAccount.setPassword(password);

		managerService.save(manager);
		
		this.unauthenticate();
		
	}
	
}
