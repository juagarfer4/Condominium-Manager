package controllers.owner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PropertyService;

import controllers.AbstractController;
import domain.Property;

@Controller
@RequestMapping("/property/owner")
public class PropertyOwnerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private PropertyService propertyService;

	// Constructors ----------------------------------------------------------

	public PropertyOwnerController() {
		super();
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Property> properties;

		properties = propertyService.findAllPropertiesByOwner();

		result = new ModelAndView("property/owner/list");
		result.addObject("properties", properties);
		result.addObject("requestURI", "property/owner/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------------------------

	// Edition ---------------------------------------------------------------

	// Ancillary methods ---------------------------------------------------------------

}
