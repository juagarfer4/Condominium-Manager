package controllers.manager;

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
@RequestMapping("/invoice/manager")
public class InvoiceManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private InvoiceService invoiceService;

	// Constructors ----------------------------------------------------------

	public InvoiceManagerController() {
		super();
	}

	// Listing ---------------------------------------------------------------
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int propertyId){
		ModelAndView result;
		Invoice invoice;
			
		invoice = invoiceService.create(propertyId);
			
		result = new ModelAndView("invoice/manager/edit");
		result.addObject("invoice", invoice);
		result.addObject("actionURI", "invoice/manager/edit.do");
		
		return result;
	}

	// Edition ---------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Invoice invoice, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			System.out.println(binding.getAllErrors().get(0));
			result=createEditModelAndView(invoice);
		}else{
			try{
				invoiceService.saveManager(invoice);
//				result=new ModelAndView("redirect:/community/manager/list.do");
				result=new ModelAndView("redirect:/property/manager/list.do?blockId="+invoice.getProperty().getBlock().getId());
			}catch(Throwable oops){
//				result=createEditModelAndView(invoice, "invoice.commit.error");
				result=createEditModelAndView(invoice, oops.getMessage());
				System.out.println(oops.getStackTrace());
			}
		}
			
		result.addObject("actionURI", "invoice/manager/edit.do");
		return result;
	}

	// Ancillary methods -----------------------------------------------
	
	public ModelAndView createEditModelAndView(Invoice invoice){
		ModelAndView result;
			
		result=createEditModelAndView(invoice, null);
			
		return result;
	}
		
	public ModelAndView createEditModelAndView(Invoice invoice, String message){
		ModelAndView result;
			
		result=new ModelAndView("invoice/manager/edit");
		result.addObject("invoice", invoice);
		result.addObject("message", message);
		
		return result;
	}

}
