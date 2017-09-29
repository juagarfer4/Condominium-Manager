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
import domain.Payment;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class PaymentServicePositiveTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private PaymentService paymentService;

	// Tests --------------------------------------------------------------

	// List of payments of a community. ------------

	@Test
	public void listPaymentsPositiveTest() {
		this.authenticate("manager1");

		Collection<Payment> payments;
		Integer size;
		payments = paymentService.findAllPaymentByCommunity(51);
		size = payments.size();

		Assert.isTrue(size == 2);

		this.unauthenticate();
	}

	// Create a payment. ------------

	@Test
	public void createPaymentPositiveTest() {
		this.authenticate("manager1");

		Payment payment;
		Double amount;
		
		payment = paymentService.create(51);
		amount=1.0;
		
		payment.setAmount(amount);
		payment.setDescription("description");
		
		paymentService.save(payment);

		this.unauthenticate();
	}
	
	// List of payments of an community in a year. ------------

			@Test
			public void findAllPaymentByCommunityAndYearPositiveTest() {
				this.authenticate("owner1");

				Collection<Payment> payments;
				Integer size;
				payments = paymentService.findAllPaymentByCommunityAndYear(51, 2015);
				size = payments.size();

				Assert.isTrue(size == 2);

				this.unauthenticate();
			}

			// total amount of payments of an community in a year. ------------
			@Test
			public void findAllTotalAmountInvoiceByCommunityAndYearPositiveTest() {
				this.authenticate("owner1");

				Double amount;
				amount = paymentService.findAllTotalAmountPaymentByCommunityAndYear(51,
						2015);

				Assert.isTrue(amount == 90);

				this.unauthenticate();
			}


}
