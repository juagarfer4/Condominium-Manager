package controllers.manager;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommunityService;

import controllers.AbstractController;
import domain.Community;

@Controller
@RequestMapping("/community/manager")
public class CommunityManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private CommunityService communityService;

	// Constructors ----------------------------------------------------------

	public CommunityManagerController() {
		super();
	}

	// Listing ---------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Community> communities;

		communities = communityService.findAll();

		result = new ModelAndView("community/manager/list");
		result.addObject("communities", communities);
		result.addObject("requestURI", "community/manager/list.do");

		return result;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam String address){
		ModelAndView result;
		Collection<Community> communities;
					
		communities = communityService.findAllCommunitiesByAddress(address);
					
		result=new ModelAndView("community/manager/search");
		result.addObject("communities", communities);
		result.addObject("requestURI", "community/manager/search.do");
					
		return result;
	}

	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------------------------

	// Edition ---------------------------------------------------------------

	// Ancillary methods -----------------------------------------------

}
