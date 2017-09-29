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
import domain.Invoice;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class InvoiceServicePositiveTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private InvoiceService invoiceService;

	// Tests --------------------------------------------------------------

	// List of invoices of an owner. ------------

	@Test
	public void listInvoicesPositiveTest() {
		this.authenticate("owner1");

		Collection<Invoice> invoices;
		Integer size;
		invoices = invoiceService.findAllInvoicesByProperty(53);
		size = invoices.size();

		Assert.isTrue(size == 1);

		this.unauthenticate();
	}

	// Create an invoice. ------------

	@Test
	public void createInvoicePositiveTest() {
		this.authenticate("manager1");

		Invoice invoice;
		Double amount;

		invoice = invoiceService.create(53);
		amount = 1.0;

		invoice.setAmount(amount);

		invoiceService.saveManager(invoice);

		this.unauthenticate();
	}
	
	// List of invoices of an community in a year. ------------

		@Test
		public void findAllInvoiceByCommunityAndYearPositiveTest() {
			this.authenticate("owner1");

			Collection<Invoice> invoices;
			Integer size;
			invoices = invoiceService.findAllInvoiceByCommunityAndYear(51, 2015);
			size = invoices.size();

			Assert.isTrue(size == 3);

			this.unauthenticate();
		}
		// total amount of invoices of an community in a year. ------------	
		@Test
		public void findAllTotalAmountInvoiceByCommunityAndYearPositiveTest() {
			this.authenticate("owner1");

			Double amount;
			amount = invoiceService.findAllTotalAmountInvoiceByCommunityAndYear(60, 2015);
			

			Assert.isTrue(amount == 800);
			System.out.println(amount);

			this.unauthenticate();
		}


}
