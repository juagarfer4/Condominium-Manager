package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Employee;
import domain.Contract;

import services.ContractService;
import services.EmployeeService;

@Controller
@RequestMapping("/contract/manager")
public class ContractManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private ContractService contractService;

	@Autowired
	private EmployeeService employeeService;

	// Constructors -----------------------------------------------------------

	public ContractManagerController() {
		super();
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int communityId) {
		ModelAndView result;
		Collection<Contract> contracts;

		contracts = contractService
				.findAllContractByCommunity(communityId);

		result = new ModelAndView("contract/manager/list");
		result.addObject("contracts", contracts);
		result.addObject("requestURI",
				"contract/manager/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int communityId) {
		ModelAndView result;
		Contract contract;
		Collection<Employee> employees;

		contract = contractService.create(communityId);
		employees = employeeService.findAll();

		result = new ModelAndView("contract/manager/edit");
		result.addObject("contract", contract);
		result.addObject("employees", employees);
		result.addObject("actionURI", "contract/manager/edit.do");

		return result;
	}

	// Edition ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int contractId) {
		ModelAndView result;
		Contract contract;

		contract = contractService
				.findOne(contractId);
		
		result = new ModelAndView("contract/manager/edit");
		result.addObject("contract", contract);
		result.addObject("actionURI", "contract/manager/edit.do");

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Contract contract,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(contract);
		} else {
			try {

				contractService.save(contract);
				result = new ModelAndView("redirect:list.do?communityId="+contract.getCommunity().getId());
			} catch (Throwable oops) {
//				result = createEditModelAndView(contract, "contract.commit.error");
				result = createEditModelAndView(contract, oops.getMessage());
				System.out.println(oops.getStackTrace());
			}
		}

		return result;
	}

	// Ancillary methods
	// ---------------------------------------------------------------

	public ModelAndView createEditModelAndView(
			Contract contract) {
		ModelAndView result;

		result = createEditModelAndView(contract, null);

		return result;

	}

	public ModelAndView createEditModelAndView(
			Contract contract, String message) {
		ModelAndView result;
		Collection<Employee> employees;

		employees = employeeService.findAll();

		result = new ModelAndView("contract/manager/edit");
		result.addObject("contract", contract);
		result.addObject("employees", employees);
		result.addObject("message", message);

		return result;
	}

}
