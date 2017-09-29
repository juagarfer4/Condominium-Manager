package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PropertyService;

import controllers.AbstractController;
import domain.Property;

@Controller
@RequestMapping("/property/manager")
public class PropertyManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private PropertyService propertyService;

	// Constructors ----------------------------------------------------------

	public PropertyManagerController() {
		super();
	}

	// Listing ---------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int blockId) {
		ModelAndView result;
		Collection<Property> properties;

		properties = propertyService.findAllPropertyByBlock(blockId);

		result = new ModelAndView("property/manager/list");
		result.addObject("properties", properties);
		result.addObject("requestURI", "property/manager/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------------------------

	// Edition ---------------------------------------------------------------

	// Ancillary methods -----------------------------------------------

}
