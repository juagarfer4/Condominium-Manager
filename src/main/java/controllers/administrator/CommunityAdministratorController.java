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

import domain.Community;

import services.CommunityService;

@Controller
@RequestMapping("/community/administrator")
public class CommunityAdministratorController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private CommunityService communityService;

	// Constructors -----------------------------------------------------------

	public CommunityAdministratorController() {
		super();
	}

	// Listing ------------------------------------------------
		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<Community> communities;
	
			communities = communityService.findAll();
	
			result = new ModelAndView("community/administrator/list");
			result.addObject("communities", communities);
			result.addObject("requestURI", "community/administrator/list.do");
	
			return result;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam String address){
		ModelAndView result;
		Collection<Community> communities;
					
		communities = communityService.findAllCommunitiesByAddress(address);
					
		result=new ModelAndView("community/administrator/search");
		result.addObject("communities", communities);
		result.addObject("requestURI", "community/administrator/search.do");
					
		return result;
	}
	
	// Displaying -----------------------------------------------
	
	// Creation ---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Community community;

		community = communityService.create();

		result = new ModelAndView("community/administrator/edit");
		result.addObject("community", community);
		result.addObject("actionURI", "community/administrator/edit.do");

		return result;
	}

	// Edition ----------------------------------------------------

	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int communityId) {
		ModelAndView result;
		Community community;

		community = communityService.findOne(communityId);
		result = createEditModelAndView(community);
		
		result = new ModelAndView("community/administrator/edit");
		result.addObject("community", community);
		result.addObject("actionURI", "community/administrator/edit.do");

		return result;

	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Community community, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(community);
		} else {
			try {
				communityService.save(community);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(community, "community.commit.error");
				System.out.println(oops.getStackTrace());
			}
		}

		return result;
	}

	// Ancillary methods
	// ---------------------------------------------------------------

	public ModelAndView createEditModelAndView(Community community) {
		ModelAndView result;

		result = createEditModelAndView(community, null);

		return result;

	}

	public ModelAndView createEditModelAndView(Community community,
			String message) {
		ModelAndView result;

		result = new ModelAndView("community/administrator/edit");
		result.addObject("community", community);
		result.addObject("message", message);

		return result;
	}

}
