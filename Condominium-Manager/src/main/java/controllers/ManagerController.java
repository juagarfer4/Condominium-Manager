package controllers;

import java.util.Collection;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InvoiceService;
import services.PaymentService;

import controllers.AbstractController;
import domain.Invoice;
import domain.Payment;

@Controller
@RequestMapping("/amanager")
public class ManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private PaymentService paymentService;

	// Constructors ----------------------------------------------------------

	public ManagerController() {
		super();
	}

	// Listing ---------------------------------------------------------------



	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int communityId) {
		ModelAndView result;
		Date date;
		int year;
		Collection<Invoice> invoices;
		Collection<Payment> payments;
		Double totalInvoices;
		Double totalPayments;
		
		date=new Date(System.currentTimeMillis());
		year= 1900+ date.getYear();

		invoices = invoiceService.findAllInvoiceByCommunityAndYear(communityId,year);
		payments = paymentService.findAllPaymentByCommunityAndYear(communityId, year);
		totalInvoices = invoiceService.findAllTotalAmountInvoiceByCommunityAndYear(communityId,year);
		totalPayments = paymentService.findAllTotalAmountPaymentByCommunityAndYear(communityId, year);

		result = new ModelAndView("amanager/dashboard");
		result.addObject("invoices", invoices);
		result.addObject("payments", payments);
		result.addObject("totalInvoices", totalInvoices);
		result.addObject("totalPayments", totalPayments);
		result.addObject("requestURI", "amanager/dashboard.do");

		return result;
	}

	// Creation ---------------------------------------------------------------
	
	// Edition ---------------------------------------------------------------

				
	// Ancillary methods ----------------------------------
				


}
