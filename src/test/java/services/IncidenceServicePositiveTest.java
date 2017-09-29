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
@TransactionConfiguration(defaultRollback = false)
public class IncidenceServicePositiveTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private IncidenceService incidenceService;

	// Tests --------------------------------------------------------------

	// List of incidences of a block. ------------

	@Test
	public void listIncidencesBlockPositiveTest() {
		this.authenticate("owner2");

		Collection<Incidence> incidences;
		Integer size;
		incidences = incidenceService.findAllIncidenceByBlock(61);
		size = incidences.size();

		Assert.isTrue(size == 1);

		this.unauthenticate();
	}

	// List of incidences of a community. ------------

	@Test
	public void listIncidencesCommunityPositiveTest() {
		this.authenticate("owner1");

		Collection<Incidence> incidences;
		Integer size;
		incidences = incidenceService.findAllIncidenceByCommunity(51);
		size = incidences.size();

		Assert.isTrue(size == 1);

		this.unauthenticate();
	}

	// Create an incidence for a block. ------------

	@Test
	public void createIncidenceBlockPositiveTest() {
		this.authenticate("owner1");

		Incidence incidence;

		incidence = incidenceService.createBlock(16);
		incidence.setName("name");
		incidence.setDescription("description");
		
		incidenceService.save(incidence);

		this.unauthenticate();
	}

	// Create an incidence for a community. ------------

	@Test
	public void createIncidenceCommunityPositiveTest() {
		this.authenticate("owner1");

		Incidence incidence;

		incidence = incidenceService.createCommunity(51);
		incidence.setName("name");
		incidence.setDescription("description");
		
		incidenceService.save(incidence);

		this.unauthenticate();
	}

	// Edit an incidence. ------------

	@Test
	public void editIncidenceCommunityPositiveTest() {
		this.authenticate("manager1");

		Incidence incidence;

		incidence = incidenceService.findOne(68);
		incidence.setStatus("PENDING");
		
		incidenceService.save(incidence);

		this.unauthenticate();
	}

}
