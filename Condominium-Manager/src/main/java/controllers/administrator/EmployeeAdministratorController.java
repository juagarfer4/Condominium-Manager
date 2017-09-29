package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Employee;

import forms.EmployeeForm;

import services.EmployeeService;

@Controller
@RequestMapping("/employee/administrator")
public class EmployeeAdministratorController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private EmployeeService employeeService;

	// Constructors -----------------------------------------------------------

	public EmployeeAdministratorController() {
		super();
	}

	// Listing ------------------------------------------------
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EmployeeForm employeeForm;

		employeeForm = new EmployeeForm();

		result = new ModelAndView("employee/administrator/edit");
		result.addObject("employeeForm", employeeForm);
		result.addObject("actionURI", "employee/administrator/edit.do");

		return result;
	}

	// Edition ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid EmployeeForm employeeForm, BindingResult binding) {
		ModelAndView result;
		Employee employee;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(employeeForm);
		} else {
			try {
				employee = employeeService.reconstruct(employeeForm);
				employeeService.save(employee);
				result = new ModelAndView("redirect:/");
			} catch (Throwable oops) {
//				result = createEditModelAndView(employeeForm, "employee.commit.error");
				result = createEditModelAndView(employeeForm, oops.getMessage());
				System.out.println(oops.getStackTrace());
			}
		}

		return result;
	}

	// Ancillary methods
	// ---------------------------------------------------------------

	public ModelAndView createEditModelAndView(EmployeeForm employeeForm) {
		ModelAndView result;

		result = createEditModelAndView(employeeForm, null);

		return result;

	}

	public ModelAndView createEditModelAndView(EmployeeForm employeeForm,
			String message) {
		ModelAndView result;

		result = new ModelAndView("employee/administrator/edit");
		result.addObject("employeeForm", employeeForm);
		result.addObject("message", message);

		return result;
	}

}
