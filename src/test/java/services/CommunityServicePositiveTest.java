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
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CommunityServicePositiveTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private CommunityService communityService;

	// Tests --------------------------------------------------------------

	// List of communities. ------------

	@Test
	public void listCommunitiesPositiveTest() {
		this.authenticate("administrator1");

		Collection<Community> communities;
		Integer size;
		communities = communityService.findAll();
		size = communities.size();

		Assert.isTrue(size == 2);

		this.unauthenticate();
	}

	// Create a community. ------------

	@Test
	public void createACommunityPositiveTest() {
		this.authenticate("administrator1");

		Community community;
		Double budget;
		
		community=communityService.create();
		budget=1.0;
		
		community.setName("name");
		community.setAddress("address");
		community.setBudget(budget);
		
		communityService.save(community);

		this.unauthenticate();
	}

	// Edit a community. ------------

	@Test
	public void editACommunityPositiveTest() {
		this.authenticate("administrator1");

		Community community;

		community = communityService.findOne(51);
		community.setName("name");
		community.setAddress("address");
		
		communityService.save(community);

		this.unauthenticate();
	}

}
