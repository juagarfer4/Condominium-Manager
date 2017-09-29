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
import domain.Incidence;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class IncidenceServiceNegativeTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private IncidenceService incidenceService;

	// Tests --------------------------------------------------------------

	// List of incidences of a block: wrong size. ------------

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void listIncidencesBlockNegativeTest() {
		this.authenticate("owner2");

		Collection<Incidence> incidences;
		Integer size;
		incidences = incidenceService.findAllIncidenceByBlock(19);
		size = incidences.size();

		Assert.isTrue(size == 16);

		this.unauthenticate();
	}

	// List of incidences of a community: wrong size. ------------

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void listIncidencesCommunityNegativeTest() {
		this.authenticate("owner1");

		Collection<Incidence> incidences;
		Integer size;
		incidences = incidenceService.findAllIncidenceByCommunity(15);
		size = incidences.size();

		Assert.isTrue(size == 31);

		this.unauthenticate();
	}

	// Create an incidence for a community: no community. ------------

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void createIncidenceCommunityPositiveTest() {
		this.authenticate("owner1");

		Incidence incidence;

		incidence = incidenceService.createCommunity(71);
		incidence.setDescription("description");
		
		incidenceService.save(incidence);

		this.unauthenticate();
	}

	// Edit an incidence: invalid status. ------------

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void editIncidenceCommunityPositiveTest() {
		this.authenticate("manager1");

		Incidence incidence;

		incidence = incidenceService.findOne(23);
		incidence.setStatus("UNREAD");
		
		incidenceService.save(incidence);

		this.unauthenticate();
	}

}
