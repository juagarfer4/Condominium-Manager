package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.OwnerService;

import controllers.AbstractController;
import domain.Owner;

@Controller
@RequestMapping("/owner/manager")
public class OwnerManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private OwnerService ownerService;

	// Constructors ----------------------------------------------------------

	public OwnerManagerController() {
		super();
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int neighborsBoardId) {
		ModelAndView result;
		Collection<Owner> attendants;

		attendants = ownerService.findAllAttendantsByNeighborsBoard(neighborsBoardId);

		result = new ModelAndView("owner/manager/list");
		result.addObject("owners", attendants);
		result.addObject("requestURI", "owner/manager/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------------------------

	// Edition ---------------------------------------------------------------

	// Ancillary methods -----------------------------------------------

}
