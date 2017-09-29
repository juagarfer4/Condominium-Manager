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
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ChargeHistoryServicePositiveTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private ChargeHistoryService chargeHistoryService;
	
	@Autowired
	private OwnerService ownerService;

	// Tests --------------------------------------------------------------

	// List of charge histories of a block. ------------

	@Test
	public void listChargeHistorysPositiveTest() {
		this.authenticate("manager1");

		Collection<ChargeHistory> chargeHistories;
		Integer size;
		chargeHistories = chargeHistoryService.findAllChargeHistoryByBlock(52);
		size = chargeHistories.size();

		Assert.isTrue(size == 1);

		this.unauthenticate();
	}

	// Create a charge history. ------------

	@Test
	public void createChargeHistoryPositiveTest() {
		this.authenticate("manager1");

		ChargeHistory chargeHistory;
		Date mandateEnding;
		Owner owner;

		chargeHistory = chargeHistoryService.create(52);
		mandateEnding = new Date(2016 - 1900, 5 - 1, 5);
		owner=ownerService.findOne(19);

		chargeHistory.setMandateEnding(mandateEnding);
		chargeHistory.setIsPresident(true);
		chargeHistory.setOwner(owner);

		chargeHistoryService.save(chargeHistory);

		this.unauthenticate();
	}

}
