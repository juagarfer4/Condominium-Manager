package controllers.owner;

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
@RequestMapping("/owner/owner")
public class OwnerOwnerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private OwnerService ownerService;

	// Constructors ----------------------------------------------------------

	public OwnerOwnerController() {
		super();
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int neighborsBoardId) {
		ModelAndView result;
		Collection<Owner> owners;

		owners = ownerService.findAllAttendantsByNeighborsBoard(neighborsBoardId);

		result = new ModelAndView("owner/owner/list");
		result.addObject("owners", owners);
		result.addObject("requestURI", "owner/owner/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------------------------

	// Edition ---------------------------------------------------------------

	// Ancillary methods -----------------------------------------------

}
