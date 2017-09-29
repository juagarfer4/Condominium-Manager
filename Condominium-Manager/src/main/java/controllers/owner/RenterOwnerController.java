package controllers.owner;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Renter;


import services.RenterService;

@Controller
@RequestMapping("/renter/owner")
public class RenterOwnerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private RenterService renterService;

	// Constructors -----------------------------------------------------------

	public RenterOwnerController() {
		super();
	}

	// Listing ------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int propertyId) {
		ModelAndView result;
		Collection<Renter> renters;

		renters = renterService.findAllRentersByProperty(propertyId);

		result = new ModelAndView("renter/owner/list");
		result.addObject("renters", renters);
		result.addObject("requestURI", "renter/owner/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------

	// Edition ----------------------------------------------------

	// Ancillary methods ---------------------------------------------------------------

}
