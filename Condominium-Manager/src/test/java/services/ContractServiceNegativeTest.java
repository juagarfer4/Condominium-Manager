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
import domain.Employee;
import domain.Contract;

import repositories.AdministratorRepository;
import repositories.ContractRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ContractServiceNegativeTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private ContractService contractService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ContractRepository contractRepository;

	// Tests --------------------------------------------------------------

	// List of employee communities of a community: wrong size. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void listContractsCommunityNegativeTest() {
		this.authenticate("manager1");

		Collection<Contract> contracts;
		Integer size;
		contracts = contractService
				.findAllContractByCommunity(15);
		size = contracts.size();

		Assert.isTrue(size == 24);

		this.unauthenticate();
	}

	// List of employee communities of an employee: wrong size. ------------

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void listContractsEmployeeNegativeTestSize() {
		this.authenticate("employee1");

		Collection<Contract> contracts;
		Integer size;
		contracts = contractService.findAllContractsByEmployee();
		size = contracts.size();

		Assert.isTrue(size == 10);

		this.unauthenticate();
	}

	// Create an employee community: negative salary. ------------

	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void createContractNegativeTestNegativeSalary() {
		this.authenticate("manager1");

		Contract contract;
		Double salary;
		Date arrivalDate;
		Employee employee;

		contract = contractService.create(51);
		salary = -1.0;
		arrivalDate = new Date(2014 - 1900, 12 - 1, 12);
		employee = employeeService.findOne(33);

		contract.setSalary(salary);
		contract.setArrivalDate(arrivalDate);
		contract.setEmployee(employee);

		contractService.save(contract);
		contractRepository.flush();

		this.unauthenticate();
	}

	// Set the departure date of an employee community: departure date before
	// arrival date. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void editContractCommunityPositiveTest() {
		this.authenticate("manager1");

		Contract contract;
		Date departureDate;

		contract = contractService.findOne(33);
		departureDate = new Date(2011 - 1900, 12 - 1, 12);

		contract.setDepartureDate(departureDate);

		contractService.save(contract);

		unauthenticate();
	}

}
