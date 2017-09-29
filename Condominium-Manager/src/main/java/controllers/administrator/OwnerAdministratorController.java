package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Owner;

import forms.OwnerForm;

import services.OwnerService;

@Controller
@RequestMapping("/owner/administrator")
public class OwnerAdministratorController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private OwnerService ownerService;

	// Constructors -----------------------------------------------------------

	public OwnerAdministratorController() {
		super();
	}

	// Listing ------------------------------------------------
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		OwnerForm ownerForm;

		ownerForm = new OwnerForm();

		result = new ModelAndView("owner/administrator/edit");
		result.addObject("ownerForm", ownerForm);
		result.addObject("actionURI", "owner/administrator/edit.do");

		return result;
	}

	// Edition ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid OwnerForm ownerForm, BindingResult binding) {
		ModelAndView result;
		Owner owner;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(ownerForm);
		} else {
			try {
				owner = ownerService.reconstruct(ownerForm);
				ownerService.save(owner);
				result = new ModelAndView("redirect:/");
			} catch (Throwable oops) {
//				result = createEditModelAndView(ownerForm, "owner.commit.error");
				result = createEditModelAndView(ownerForm, oops.getMessage());
				System.out.println(oops.getStackTrace());
			}
		}

		return result;
	}

	// Ancillary methods ---------------------------------------------------------------

	public ModelAndView createEditModelAndView(OwnerForm ownerForm) {
		ModelAndView result;

		result = createEditModelAndView(ownerForm, null);

		return result;

	}

	public ModelAndView createEditModelAndView(OwnerForm ownerForm, String message) {
		ModelAndView result;

		result = new ModelAndView("owner/administrator/edit");
		result.addObject("ownerForm", ownerForm);
		result.addObject("message", message);

		return result;
	}

}
