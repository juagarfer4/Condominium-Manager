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
import domain.NeighborsBoard;
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
public class NeighborsBoardServicePositiveTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private NeighborsBoardService neighborsBoardService;
	
	@Autowired
	private OwnerService ownerService;

	// Tests --------------------------------------------------------------

	// Create a neighbors board. ------------

	@Test
	public void createNeighborsBoardPositiveTest() {
		this.authenticate("manager1");

		NeighborsBoard neighborsBoard;

		neighborsBoard = neighborsBoardService.create(52);

		neighborsBoard.setRecord("record");

		neighborsBoardService.save(neighborsBoard);

		this.unauthenticate();
	}

	// List of neighbors boards of a block. ------------

	@Test
	public void listNeighborsBoardPositiveTest() {
		this.authenticate("manager1");

		Collection<NeighborsBoard> neighborsBoards;
		Integer size;

		neighborsBoards = neighborsBoardService
				.findAllNeighborsBoardByBlock(52);
		size = neighborsBoards.size();

		Assert.isTrue(size == 1);

		this.unauthenticate();
	}

	// Add an attendant to a neighbors board. ------------

	@Test
	public void addAttendantPositiveTest() {
		this.authenticate("manager1");

		NeighborsBoard neighborsBoard;
		Collection<Owner> attendants;
		Owner attendant;
		Collection<NeighborsBoard> neighborBoards;

		neighborsBoard = neighborsBoardService.findOne(72);
		attendants=neighborsBoard.getAttendants();
		attendant=ownerService.findOne(19);
		neighborBoards=attendant.getNeighborsBoards();

		attendants.add(attendant);
		neighborBoards.add(neighborsBoard);

		neighborsBoardService.save(neighborsBoard);
		ownerService.save(attendant);

		this.unauthenticate();
	}

}
