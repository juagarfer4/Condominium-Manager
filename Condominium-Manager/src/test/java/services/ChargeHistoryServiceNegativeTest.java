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
import domain.ChargeHistory;
import domain.Owner;

import repositories.AdministratorRepository;
import repositories.ChargeHistoryRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ChargeHistoryServiceNegativeTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private ChargeHistoryService chargeHistoryService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private ChargeHistoryRepository chargeHistoryRepository;

	// Tests --------------------------------------------------------------

	// List of charge histories of a block: wrong size. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void listChargeHistorysNegativeTest() {
		this.authenticate("manager1");

		Collection<ChargeHistory> chargeHistories;
		Integer size;
		chargeHistories = chargeHistoryService.findAllChargeHistoryByBlock(16);
		size = chargeHistories.size();

		Assert.isTrue(size == 87);

		this.unauthenticate();
	}

	// Create a charge history: mandate ending before mandate beginning.
	// ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void createChargeHistoryNegativeTestDates() {
		this.authenticate("manager1");

		ChargeHistory chargeHistory;
		Date mandateEnding;
		Owner owner;

		chargeHistory = chargeHistoryService.create(52);
		mandateEnding = new Date(2011 - 1900, 5 - 1, 5);
		owner = ownerService.findOne(13);

		chargeHistory.setMandateEnding(mandateEnding);
		chargeHistory.setIsPresident(true);
		chargeHistory.setOwner(owner);

		chargeHistoryService.save(chargeHistory);

		this.unauthenticate();
	}

	// Create a charge history: no president or secretary. ------------

	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void createChargeHistoryNegativeTestBoolean() {
		this.authenticate("manager1");

		ChargeHistory chargeHistory;
		Date mandateEnding;
		Owner owner;

		chargeHistory = chargeHistoryService.create(52);
		mandateEnding = new Date(2016 - 1900, 5 - 1, 5);
		owner = ownerService.findOne(13);

		chargeHistory.setMandateEnding(mandateEnding);
		chargeHistory.setOwner(owner);

		chargeHistoryService.save(chargeHistory);
		chargeHistoryRepository.flush();

		this.unauthenticate();
	}

}
