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
import domain.Block;

import repositories.AdministratorRepository;
import repositories.BlockRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BlockServiceNegativeTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private BlockService blockService;

	@Autowired
	private BlockRepository blockRepository;

	// Tests --------------------------------------------------------------

	// List of blocks of a community: wrong size. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void listBlocksNegativeTest() {
		this.authenticate("administrator1");

		Collection<Block> blocks;
		Integer size;
		blocks = blockService.findAllBlocksByCommunity(15);
		size = blocks.size();

		Assert.isTrue(size == 10);

		this.unauthenticate();
	}

	// Create a block: negative numbers. ------------

	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void createBlockNegativeTest() {
		this.authenticate("administrator1");

		Block block;
		Integer number;
		Integer numberOfFloors;
		Integer numberOfDoors;

		block = blockService.create(51);
		number = -2;
		numberOfFloors = -1;
		numberOfDoors = -1;

		block.setNumber(number);
		block.setNumberOfFloors(numberOfFloors);
		block.setNumberOfDoors(numberOfDoors);

		blockService.save(block);
		blockRepository.flush();

		this.unauthenticate();
	}

	// Edit a block: negative numbers. ------------

	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void editBlockNegativeTest() {
		this.authenticate("administrator1");

		Block block;
		Integer numberOfFloors;
		Integer numberOfDoors;

		block = blockService.findOne(52);
		numberOfFloors = -1;
		numberOfDoors = -1;

		block.setNumberOfFloors(numberOfFloors);
		block.setNumberOfDoors(numberOfDoors);

		blockService.save(block);
		blockRepository.flush();

		this.unauthenticate();
	}

}
