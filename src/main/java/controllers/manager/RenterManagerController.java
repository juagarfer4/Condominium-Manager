package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Renter;

import services.RenterService;

@Controller
@RequestMapping("/renter/manager")
public class RenterManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private RenterService renterService;

	// Constructors -----------------------------------------------------------

	public RenterManagerController() {
		super();
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int propertyId) {
		ModelAndView result;
		Collection<Renter> renters;

		renters = renterService.findAllRentersByProperty(propertyId);

		result = new ModelAndView("renter/manager/list");
		result.addObject("renters", renters);
		result.addObject("requestURI", "renter/manager/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int propertyId) {
		ModelAndView result;
		Renter renter;

		renter = renterService.create(propertyId);

		result = new ModelAndView("renter/manager/edit");
		result.addObject("renter", renter);
		result.addObject("actionURI", "renter/manager/edit.do");

		return result;
	}

	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int renterId) {
		ModelAndView result;
		Renter renter;

		renter = renterService.findOne(renterId);

		Assert.notNull(renter);

		result = createEditModelAndView(renter);
		
		result = new ModelAndView("renter/manager/edit");
		result.addObject("renter", renter);
		result.addObject("actionURI", "renter/manager/edit.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Renter renter, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(renter);
		} else {
			try {
				renterService.save(renter);
				result = new ModelAndView("redirect:list.do?propertyId="+renter.getProperty().getId());
			} catch (Throwable oops) {
//				result = createEditModelAndView(renter, "renter.commit.error");
				result = createEditModelAndView(renter, oops.getMessage());
				System.out.println(oops.getLocalizedMessage());
			}
		}

		result.addObject("actionURI", "renter/manager/edit.do");

		return result;
	}

	// Ancillary methods ----------------------------------

	protected ModelAndView createEditModelAndView(Renter renter) {
		ModelAndView result;

		result = createEditModelAndView(renter, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Renter renter,
			String message) {
		ModelAndView result;

		result = new ModelAndView("renter/manager/edit");
		result.addObject("renter", renter);
		result.addObject("message", message);

		return result;
	}

}
