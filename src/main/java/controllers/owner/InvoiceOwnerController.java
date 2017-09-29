package controllers.owner;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InvoiceService;

import controllers.AbstractController;
import domain.Invoice;

@Controller
@RequestMapping("/invoice/owner")
public class InvoiceOwnerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private InvoiceService invoiceService;

	// Constructors ----------------------------------------------------------

	public InvoiceOwnerController() {
		super();
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int propertyId) {
		ModelAndView result;
		Collection<Invoice> invoices;

		invoices = invoiceService.findAllInvoicesByProperty(propertyId);

		result = new ModelAndView("invoice/owner/list");
		result.addObject("invoices", invoices);
		result.addObject("requestURI", "invoice/owner/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------------------------

	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int invoiceId) {
		ModelAndView result;
		Invoice invoice;

		invoice = invoiceService.findOne(invoiceId);

		result = createEditModelAndView(invoice);
		
		result = new ModelAndView("invoice/owner/edit");
		result.addObject("invoice", invoice);
		result.addObject("actionURI", "invoice/owner/edit.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Invoice invoice, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(invoice);
		} else {
			try {
				invoiceService.payInvoice(invoice);
//				result = new ModelAndView("redirect:/property/owner/list.do");
				result = new ModelAndView("redirect:list.do?propertyId="+invoice.getProperty().getId());
			} catch (Throwable oops) {
//				result = createEditModelAndView(invoice, "invoice.commit.error");
				result = createEditModelAndView(invoice, oops.getMessage());
				System.out.println(oops.getLocalizedMessage());
			}
		}

		result.addObject("actionURI", "invoice/owner/edit.do");

		return result;
	}
	
	@RequestMapping(value = "paypal", method = RequestMethod.GET)
	public ModelAndView PayPal(@RequestParam int invoiceId) {
		ModelAndView result;
		Invoice invoice;

		invoice = invoiceService.findOne(invoiceId);

		result = createEditModelAndView(invoice);
		
		result = new ModelAndView("invoice/owner/paypal");
		result.addObject("invoice", invoice);
		result.addObject("actionURI", "invoice/owner/paypal.do");

		return result;
	}

	// Ancillary methods -----------------------------------------------

	protected ModelAndView createEditModelAndView(Invoice invoice) {
		ModelAndView result;

		result = createEditModelAndView(invoice, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Invoice invoice, String message) {
		ModelAndView result;

		result = new ModelAndView("invoice/owner/edit");
		result.addObject("invoice", invoice);
		result.addObject("message", message);

		return result;
	}
	
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public ModelAndView success(@RequestParam int invoiceId) {
		ModelAndView result;
		Invoice invoice;

		invoice = invoiceService.findOne(invoiceId);
		
		invoiceService.payInvoice(invoice);
		
		result = new ModelAndView("invoice/owner/success");

		return result;
	}
	
	@RequestMapping(value = "/success", method = RequestMethod.POST)
	public ModelAndView success2(@RequestParam int invoiceId) {
		ModelAndView result;
		Invoice invoice;

		invoice = invoiceService.findOne(invoiceId);
		
		invoiceService.payInvoice(invoice);
		
		result = new ModelAndView("invoice/owner/success");

		return result;
	}

	@RequestMapping(value = "/failure", method = RequestMethod.GET)
	public ModelAndView failure() {
		ModelAndView result;

		result = new ModelAndView("invoice/owner/failure");

		return result;
	}

}
