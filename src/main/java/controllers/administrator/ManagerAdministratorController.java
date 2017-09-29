package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Manager;

import forms.ManagerForm;

import services.ManagerService;

@Controller
@RequestMapping("/amanager/administrator")
public class ManagerAdministratorController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private ManagerService managerService;

	// Constructors -----------------------------------------------------------

	public ManagerAdministratorController() {
		super();
	}

	// Listing ------------------------------------------------
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ManagerForm managerForm;

		managerForm = new ManagerForm();

		result = new ModelAndView("amanager/administrator/edit");
		result.addObject("managerForm", managerForm);
		result.addObject("actionURI", "amanager/administrator/edit.do");

		return result;
	}

	// Edition ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ManagerForm managerForm, BindingResult binding) {
		ModelAndView result;
		Manager manager;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(managerForm);
		} else {
			try {
				manager = managerService.reconstruct(managerForm);
				managerService.save(manager);
				result = new ModelAndView("redirect:/");
			} catch (Throwable oops) {
				result = createEditModelAndView(managerForm, "manager.commit.error");
				System.out.println(oops.getStackTrace());
			}
		}

		return result;
	}

	// Ancillary methods
	// ---------------------------------------------------------------

	public ModelAndView createEditModelAndView(ManagerForm managerForm) {
		ModelAndView result;

		result = createEditModelAndView(managerForm, null);

		return result;

	}

	public ModelAndView createEditModelAndView(ManagerForm managerForm,
			String message) {
		ModelAndView result;

		result = new ModelAndView("amanager/administrator/edit");
		result.addObject("managerForm", managerForm);
		result.addObject("message", message);

		return result;
	}

}
