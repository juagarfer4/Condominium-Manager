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
import domain.Owner;
import domain.Property;

import repositories.AdministratorRepository;
import repositories.PropertyRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PropertyServiceNegativeTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private PropertyRepository propertyRepository;

	// Tests --------------------------------------------------------------

	// List of properties of an owner: wrong size. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void listPropertiesOwnerNegativeTestWrongSize() {
		this.authenticate("owner1");

		Collection<Property> properties;
		Integer size;
		properties = propertyService.findAllPropertiesByOwner();
		size = properties.size();

		Assert.isTrue(size == 19);

		this.unauthenticate();
	}

	// List of properties of an owner: wrong size. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void listPropertiesOwnerNegativeTestNoOwner() {
		Collection<Property> properties;
		Integer size;
		properties = propertyService.findAllPropertiesByOwner();
		size = properties.size();

		Assert.isTrue(size == 1);
	}

	// List of properties of a block: wrong size. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void listPropertiesBlockNegativeTest() {
		this.authenticate("manager1");

		Collection<Property> properties;
		Integer size;
		properties = propertyService.findAllPropertyByBlock(16);
		size = properties.size();

		Assert.isTrue(size == 11);

		this.unauthenticate();
	}

	// Create a property: negative floor. ------------

	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void createPropertyNegativeTestNegativeFloor() {
		this.authenticate("administrator1");

		Property property;
		Integer floor;
		Owner owner;
		Collection<Property> properties;

		property = propertyService.create(52);
		floor = -2;
		owner = ownerService.findOne(19);
		properties = owner.getProperties();

		property.setFloor(floor);
		property.setDoor("a");
		property.setOwner(owner);

		propertyService.save(property);
		propertyRepository.flush();

		properties.add(property);

		this.unauthenticate();
	}

	// Create a property without being an administrator. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void createPropertyNegativeTestNoAdministrator() {
		Property property;
		Integer floor;
		Owner owner;
		Collection<Property> properties;

		property = propertyService.create(16);
		floor = 2;
		owner = ownerService.findOne(19);
		properties = owner.getProperties();

		property.setFloor(floor);
		property.setDoor("a");
		property.setOwner(owner);

		propertyService.save(property);
		propertyRepository.flush();

		properties.add(property);
	}

	// Create a property: no floor or door. ------------

	@Test(expected = java.lang.NullPointerException.class)
	public void createPropertyNegativeTestNoFloorOrDoor() {
		this.authenticate("administrator1");

		Property property;
		Owner owner;
		Collection<Property> properties;

		property = propertyService.create(16);
		owner = ownerService.findOne(13);
		properties = owner.getProperties();

		property.setOwner(owner);

		propertyService.save(property);
		propertyRepository.flush();

		properties.add(property);

		this.unauthenticate();
	}

	// Change the owner of a property: no owner. ------------

	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void editPropertyNegativeTest() {
		this.authenticate("administrator1");

		Property property;
		Owner owner;

		property = propertyService.findOne(53);
		owner = ownerService.findOne(99);

		property.setOwner(owner);

		propertyService.save(property);
		propertyRepository.flush();

		this.unauthenticate();
	}

}
