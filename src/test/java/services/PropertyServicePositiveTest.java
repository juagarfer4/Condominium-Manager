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
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class PropertyServicePositiveTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private OwnerService ownerService;

	// Tests --------------------------------------------------------------

	// List of properties of an owner. ------------

	@Test
	public void listPropertiesOwnerPositiveTest() {
		this.authenticate("owner1");

		Collection<Property> properties;
		Integer size;
		properties = propertyService.findAllPropertiesByOwner();
		size = properties.size();

		Assert.isTrue(size == 1);

		this.unauthenticate();
	}

	// List of properties of a block. ------------

	@Test
	public void listPropertiesBlockPositiveTest() {
		this.authenticate("manager1");

		Collection<Property> properties;
		Integer size;
		properties = propertyService.findAllPropertyByBlock(52);
		size = properties.size();

		Assert.isTrue(size == 3);

		this.unauthenticate();
	}

	// Create a property. ------------

	@Test
	public void createPropertyPositiveTest() {
		this.authenticate("administrator1");

		Property property;
		Integer floor;
		Owner owner;
		Collection<Property> properties;

		property = propertyService.create(52);
		floor=2;
		owner=ownerService.findOne(19);
		properties=owner.getProperties();
		
		property.setFloor(floor);
		property.setDoor("b");
		property.setOwner(owner);
		
		propertyService.save(property);
		
		properties.add(property);

		this.unauthenticate();
	}

	// Change the owner of a property. ------------

	@Test
	public void editPropertyPositiveTest() {
		this.authenticate("administrator1");

		Property property;
		Owner owner;

		property = propertyService.findOne(53);
		owner=ownerService.findOne(19);

		property.setOwner(owner);

		propertyService.save(property);

		this.unauthenticate();
	}

}
