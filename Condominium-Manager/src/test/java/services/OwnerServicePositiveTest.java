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

import domain.Owner;

import repositories.OwnerRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class OwnerServicePositiveTest extends AbstractTest {

	// Service under test ------------------------------------------------

	
	@Autowired
	private OwnerService ownerService;
	

	@Autowired
	private OwnerRepository ownerRepository;
	
	// Tests --------------------------------------------------------------

	
	//Register a new owner of the system
	
	@Test
	public void testPositiveCreateOwner() {
		this.authenticate("administrator1");
		
		Owner owner;
		String name;
		String surname;
		String email;
		String phone;
		UserAccount userAccount;
		String username;
		String password;

		owner = ownerService.create();
		name = "name";
		surname = "surname";
		email = "email@us.es";
		phone = "900000000";
		userAccount=owner.getUserAccount();
		username="username";
		password="username";

		owner.setName(name);
		owner.setSurname(surname);
		owner.setEmail(email);
		owner.setPhone(phone);
		userAccount.setUsername(username);
		userAccount.setPassword(password);

		ownerService.save(owner);
		ownerRepository.flush();
		
		this.unauthenticate();
		
	}
	
}
