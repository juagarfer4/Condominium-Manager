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

import domain.Employee;

import repositories.EmployeeRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class EmployeeServiceNegativeTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;

	// Tests --------------------------------------------------------------


	// Register a new employee of the system
	// Wrong email ------------

	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testNegativeCreateEmployeeEmail() {
		this.authenticate("administrator1");

		Employee employee;
		String name;
		String surname;
		String email;
		String phone;
		UserAccount userAccount;
		String username;
		String password;

		employee = employeeService.create();
		name = "name";
		surname = "surname";
		email = "email";
		phone = "900000000";
		userAccount=employee.getUserAccount();
		username="username";
		password="username";

		employee.setName(name);
		employee.setSurname(surname);
		employee.setEmail(email);
		employee.setPhone(phone);
		userAccount.setUsername(username);
		userAccount.setPassword(password);

		employeeService.save(employee);
		employeeRepository.flush();

		this.unauthenticate();

	}

	// Repeated username. -----------------------------

	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void testNegativeCreateEmployeeUsername() {
		this.authenticate("administrator1");

		Employee employee;
		String name;
		String surname;
		String email;
		String phone;
		UserAccount userAccount;
		String username;
		String password;

		employee = employeeService.create();
		name = "name";
		surname = "surname";
		email = "email@us.es";
		phone = "900000000";
		userAccount=employee.getUserAccount();
		username="employee2";
		password="employee2";

		employee.setName(name);
		employee.setSurname(surname);
		employee.setEmail(email);
		employee.setPhone(phone);
		userAccount.setUsername(username);
		userAccount.setPassword(password);

		employeeService.save(employee);
		employeeRepository.flush();

		this.unauthenticate();

	}

}
