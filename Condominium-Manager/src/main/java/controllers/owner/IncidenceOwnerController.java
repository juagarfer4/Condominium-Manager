package controllers.owner;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.IncidenceService;

import controllers.AbstractController;
import domain.Incidence;

@Controller
@RequestMapping("/incidence/owner")
public class IncidenceOwnerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private IncidenceService incidenceService;

	// Constructors ----------------------------------------------------------

	public IncidenceOwnerController() {
		super();
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/listbyblock", method = RequestMethod.GET)
	public ModelAndView listByBlock(@RequestParam int blockId) {
		ModelAndView result;
		Collection<Incidence> incidences;

		incidences = incidenceService.findAllIncidenceByBlock(blockId);

		result = new ModelAndView("incidence/owner/listbyblock");
		result.addObject("incidences", incidences);
		result.addObject("requestURI", "incidence/owner/listbyblock.do");

		return result;
	}

	@RequestMapping(value = "/listbycommunity", method = RequestMethod.GET)
	public ModelAndView listByCommunity(@RequestParam int communityId) {
		ModelAndView result;
		Collection<Incidence> incidences;

		incidences = incidenceService.findAllIncidenceByCommunity(communityId);

		result = new ModelAndView("incidence/owner/listbycommunity");
		result.addObject("incidences", incidences);
		result.addObject("requestURI", "incidence/owner/listbycommunity.do");

		return result;
	}
	
	// Displaying -----------------------------------------------
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int incidenceId) {
		ModelAndView result;
		Incidence incidence;
		String requestURI;

		incidence = incidenceService.findOne(incidenceId);
		requestURI = "incidence/owner/display.do?incidenceId="+incidenceId;
	
		result = new ModelAndView("incidence/owner/display");

		result.addObject("incidence", incidence);
		result.addObject("requestURI", requestURI);

		return result;
	}

	// Creation -------------------------------------------

	@RequestMapping(value = "/createbycommunity", method = RequestMethod.GET)
	public ModelAndView createByCommunity(@RequestParam Integer communityId) {
		ModelAndView result;
		Incidence incidence;

		incidence = incidenceService.createCommunity(communityId);

		result = new ModelAndView("incidence/owner/edit");
		result.addObject("incidence", incidence);
		result.addObject("actionURI", "incidence/owner/edit.do");

		return result;
	}
	
	@RequestMapping(value = "/createbyblock", method = RequestMethod.GET)
	public ModelAndView createByBlock(@RequestParam Integer blockId) {
		ModelAndView result;
		Incidence incidence;

		incidence = incidenceService.createBlock(blockId);

		result = new ModelAndView("incidence/owner/edit");
		result.addObject("incidence", incidence);
		result.addObject("actionURI", "incidence/owner/edit.do");

		return result;
	}

	// Edition -------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Incidence incidence, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(incidence);
		} else {
			try {
				incidenceService.save(incidence);
				result = new ModelAndView("redirect:/property/owner/list.do");
			} catch (Throwable oops) {
//				result = createEditModelAndView(incidence, "incidence.commit.error");
				result = createEditModelAndView(incidence, oops.getMessage());
				System.out.println(oops.getStackTrace());
			}
		}

		result.addObject("actionURI", "incidence/owner/edit.do");
		return result;
	}

	// Ancillary methods ----------------------------------

	public ModelAndView createEditModelAndView(Incidence incidence) {
		ModelAndView result;

		result = createEditModelAndView(incidence, null);

		return result;
	}

	public ModelAndView createEditModelAndView(Incidence incidence, String message) {
		ModelAndView result;

		result = new ModelAndView("incidence/owner/edit");
		result.addObject("incidence", incidence);
		result.addObject("message", message);

		return result;
	}

}
