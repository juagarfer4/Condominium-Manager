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

import domain.Administrator;
import domain.Community;

import repositories.AdministratorRepository;
import repositories.CommunityRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CommunityServiceNegativeTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private CommunityRepository communityRepository;

	// Tests --------------------------------------------------------------

	// List of communities: wrong size. ------------

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void listCommunitiesNegativeTest() {
		this.authenticate("administrator1");

		Collection<Community> communities;
		Integer size;
		communities = communityService.findAll();
		size = communities.size();

		Assert.isTrue(size == 29);

		this.unauthenticate();
	}

	// Create a community without attributes. ------------

	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void createACommunityNegativeTestNoAttributes() {
		this.authenticate("administrator1");

		Community community;
		
		community=communityService.create();
		
		communityService.save(community);
		communityRepository.flush();

		this.unauthenticate();
	}

	// Edit a community. ------------

	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void editACommunityNegativeTestBlank() {
		this.authenticate("administrator1");

		Community community;

		community = communityService.findOne(51);
		community.setName("");
		community.setAddress("");
		
		communityService.save(community);
		communityRepository.flush();

		this.unauthenticate();
	}

}
