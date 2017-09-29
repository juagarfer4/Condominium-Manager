package controllers.administrator;

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

import domain.Owner;
import domain.Property;

import services.OwnerService;
import services.PropertyService;

@Controller
@RequestMapping("/property/administrator")
public class PropertyAdministratorController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private OwnerService ownerService;
	
	// Constructors -----------------------------------------------------------

	public PropertyAdministratorController() {
		super();
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int blockId) {
		ModelAndView result;
		Collection<Property> properties;

		properties = propertyService.findAllPropertyByBlock(blockId);

		result = new ModelAndView("property/administrator/list");
		result.addObject("properties", properties);
		result.addObject("requestURI", "property/administrator/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int blockId ) {
		ModelAndView result;
		Property property;
		Collection<Owner> owners;

		property  = propertyService.create(blockId);
		owners = ownerService.findAll();

		result = new ModelAndView("property/administrator/edit");
		result.addObject("property", property);
		result.addObject("owners", owners);
		result.addObject("actionURI", "property/administrator/edit.do");

		return result;
	}

	// Edition ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int propertyId) {
		ModelAndView result;
		Property property;
		Collection<Owner> owners;

		property = propertyService.findOne(propertyId);
		result = createEditModelAndView(property);
		owners = ownerService.findAll();
		
		result = new ModelAndView("property/administrator/edit");
		result.addObject("property", property);
		result.addObject("owners", owners);
		result.addObject("actionURI", "property/administrator/edit.do");

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Property property, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(property);
		} else {
			try {
				propertyService.save(property);
				result = new ModelAndView("redirect:list.do?blockId="+property.getBlock().getId());
			} catch (Throwable oops) {
//				result = createEditModelAndView(property, "property.commit.error");
				result = createEditModelAndView(property, oops.getMessage());
				System.out.println(oops.getStackTrace());
			}
		}

		return result;
	}

	// Ancillary methods
	// ---------------------------------------------------------------

	public ModelAndView createEditModelAndView(Property property) {
		ModelAndView result;

		result = createEditModelAndView(property, null);

		return result;

	}

	public ModelAndView createEditModelAndView(Property property,
			String message) {
		ModelAndView result;
		Collection<Owner> owners;

		owners = ownerService.findAll();

		result = new ModelAndView("property/administrator/edit");
		result.addObject("property", property);
		result.addObject("owners", owners);
		result.addObject("message", message);

		return result;
	}

}
