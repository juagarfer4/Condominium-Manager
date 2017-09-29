package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Administrator;

import forms.AdministratorForm;

import services.AdministratorService;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministratorController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public AdministratorAdministratorController() {
		super();
	}

	// Listing ------------------------------------------------
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		AdministratorForm administratorForm;

		administratorForm = new AdministratorForm();

		result = new ModelAndView("administrator/administrator/edit");
		result.addObject("administratorForm", administratorForm);
		result.addObject("actionURI", "administrator/administrator/edit.do");

		return result;
	}

	// Edition ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid AdministratorForm administratorForm, BindingResult binding) {
		ModelAndView result;
		Administrator administrator;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(administratorForm);
		} else {
			try {
				administrator = administratorService.reconstruct(administratorForm);
				administratorService.save(administrator);
				result = new ModelAndView("redirect:/");
			} catch (Throwable oops) {
//				result = createEditModelAndView(administratorForm, "administrator.commit.error");
				result = createEditModelAndView(administratorForm, oops.getMessage());
				System.out.println(oops.getStackTrace());
			}
		}

		return result;
	}

	// Ancillary methods ---------------------------------------------------------------

	public ModelAndView createEditModelAndView(AdministratorForm administratorForm) {
		ModelAndView result;

		result = createEditModelAndView(administratorForm, null);

		return result;

	}

	public ModelAndView createEditModelAndView(AdministratorForm administratorForm, String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/administrator/edit");
		result.addObject("administratorForm", administratorForm);
		result.addObject("message", message);

		return result;
	}

}
