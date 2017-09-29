package controllers.employee;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Contract;

import services.ContractService;

@Controller
@RequestMapping("/contract/employee")
public class ContractEmployeeController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private ContractService contractService;

	// Constructors -----------------------------------------------------------

	public ContractEmployeeController() {
		super();
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Contract> contracts;

		contracts = contractService.findAllContractsByEmployee();

		result = new ModelAndView("contract/employee/list");
		result.addObject("contracts", contracts);
		result.addObject("requestURI", "contract/employee/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------

	// Edition ----------------------------------------------------

	// Ancillary methods -----------------------------------------

}
