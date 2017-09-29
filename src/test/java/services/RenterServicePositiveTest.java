package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import domain.Renter;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class RenterServicePositiveTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private RenterService renterService;

	// Tests --------------------------------------------------------------

	// List of renters of a property. ------------

	@Test
	public void listRentersPositiveTest() {
		this.authenticate("manager1");

		Collection<Renter> renters;
		Integer size;
		renters = renterService.findAllRentersByProperty(62);
		size = renters.size();

		Assert.isTrue(size == 2);

		this.unauthenticate();
	}

	// Create a renter. ------------

	@Test
	public void createRenterPositiveTest() {
		this.authenticate("manager1");

		Renter renter;
		String name;
		String surname;
		String email;
		Date arrivalDate;

		renter = renterService.create(53);
		name="NAME";
		surname="SURNAME";
		email="email@us.es";
		arrivalDate=new Date(2015 - 1900, 8 - 1, 8);
		
		renter.setName(name);
		renter.setSurname(surname);
		renter.setEmail(email);
		renter.setArrivalDate(arrivalDate);
		
		renterService.save(renter);
	}
	
	// Set the departure date of a renter. ---------------
	
	@Test
	public void editRenterPositiveTest() {
		this.authenticate("manager1");

		Renter renter;
		Date departureDate;

		renter = renterService.findOne(63);
		departureDate=new Date(2016 - 1900, 8 - 1, 8);
		
		renter.setDepartureDate(departureDate);
		
		renterService.save(renter);
	}

}
