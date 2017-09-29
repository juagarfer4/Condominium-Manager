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
import repositories.InvoiceRepository;
import repositories.ManagerRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class InvoiceServiceNegativeTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private InvoiceRepository invoiceRepository;

	// Tests --------------------------------------------------------------

	// List of invoices of an owner: wrong size. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void listInvoicesNegativeTest() {
		this.authenticate("owner1");

		Collection<Invoice> invoices;
		Integer size;
		invoices = invoiceService.findAllInvoicesByProperty(17);
		size = invoices.size();

		Assert.isTrue(size == 65);

		this.unauthenticate();
	}

	// Pay an invoice which is already paid. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void payInvoiceNegativeTestPaid() {
		this.authenticate("owner2");

		Invoice invoice;

		invoice = invoiceService.findOne(30);

		invoiceService.payInvoice(invoice);

		this.unauthenticate();
	}

	// Pay an invoice from other owner. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void payInvoiceNegativeTestOther() {
		this.authenticate("owner2");

		Invoice invoice;

		invoice = invoiceService.findOne(29);

		invoiceService.payInvoice(invoice);

		this.unauthenticate();
	}

	// Create an invoice with negative amount. ------------

	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void createInvoiceNegativeTestNegative() {
		this.authenticate("manager1");

		Invoice invoice;
		Double amount;

		invoice = invoiceService.create(53);
		amount = -1.0;

		invoice.setAmount(amount);

		invoiceService.saveManager(invoice);
		invoiceRepository.flush();

		this.unauthenticate();
	}

	// Create an invoice as an owner. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void createInvoiceNegativeTestOwner() {
		this.authenticate("owner1");

		Invoice invoice;
		Double amount;

		invoice = invoiceService.create(17);
		amount = 1.0;

		invoice.setAmount(amount);

		invoiceService.saveManager(invoice);

		this.unauthenticate();
	}

	// List of invoices of an community in a year. ------------

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void findAllInvoiceByCommunityAndYearNegativeTest() {
		this.authenticate("owner1");

		Collection<Invoice> invoices;
		Integer size;
		invoices = invoiceService.findAllInvoiceByCommunityAndYear(51, 2015);
		size = invoices.size();

		Assert.isTrue(size == 0);

		this.unauthenticate();
	}

	// total amount of invoices of an community in a year. ------------
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void findAllTotalAmountInvoiceByCommunityAndYearNegativeTest() {
		this.authenticate("owner1");

		Double amount;
		amount = invoiceService.findAllTotalAmountInvoiceByCommunityAndYear(51,
				2015);

		Assert.isTrue(amount == 0);

		this.unauthenticate();
	}

}
