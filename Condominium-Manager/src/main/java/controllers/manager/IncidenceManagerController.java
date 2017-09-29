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

import services.IncidenceService;

import controllers.AbstractController;
import domain.Incidence;

@Controller
@RequestMapping("/incidence/manager")
public class IncidenceManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private IncidenceService incidenceService;

	// Constructors ----------------------------------------------------------

	public IncidenceManagerController() {
		super();
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/listbyblock", method = RequestMethod.GET)
	public ModelAndView listByBlock(@RequestParam int blockId) {
		ModelAndView result;
		Collection<Incidence> incidences;

		incidences = incidenceService.findAllIncidenceByBlock(blockId);

		result = new ModelAndView("incidence/manager/listbyblock");
		result.addObject("incidences", incidences);
		result.addObject("requestURI", "incidence/manager/listbyblock.do");

		return result;
	}

	@RequestMapping(value = "/listbycommunity", method = RequestMethod.GET)
	public ModelAndView listByCommunity(@RequestParam int communityId) {
		ModelAndView result;
		Collection<Incidence> incidences;

		incidences = incidenceService.findAllIncidenceByCommunity(communityId);

		result = new ModelAndView("incidence/manager/listbycommunity");
		result.addObject("incidences", incidences);
		result.addObject("requestURI", "incidence/manager/listbycommunity.do");

		return result;
	}
	
	// Displaying -----------------------------------------------------------
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int incidenceId) {
		ModelAndView result;
		Incidence incidence;
		String requestURI;

		incidence = incidenceService.findOne(incidenceId);
		requestURI = "incidence/manager/display.do?incidenceId="+incidenceId;
	
		result = new ModelAndView("incidence/manager/display");

		result.addObject("incidence", incidence);
		result.addObject("requestURI", requestURI);

		return result;
	}

	// Creation ---------------------------------------------------------------
	
	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int incidenceId){
		ModelAndView result;
		Incidence incidence;				

		incidence = incidenceService.findOne(incidenceId);
		
		Assert.notNull(incidence);

		result = createEditModelAndView(incidence);
		
		result = new ModelAndView("incidence/manager/edit");
		result.addObject("incidence", incidence);
		result.addObject("actionURI", "incidence/manager/edit.do");

		return result;
	}
				
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Incidence incidence, BindingResult binding){
		ModelAndView result;
	
		if(binding.hasErrors()){
			System.out.println(binding.getAllErrors().get(0));
			result=createEditModelAndView(incidence);
		}else{
			try{
				incidenceService.save(incidence);
				if(incidence.getCommunity()!=null){
					result=new ModelAndView("redirect:listbycommunity.do?communityId="+incidence.getCommunity().getId());
				}else if(incidence.getBlock()!=null){
					result=new ModelAndView("redirect:listbyblock.do?blockId="+incidence.getBlock().getId());
				}else{
					result=new ModelAndView("redirect:/community/manager/list.do");
				}
			}catch (Throwable oops){
//				result=createEditModelAndView(incidence, "incidence.commit.error");
				result=createEditModelAndView(incidence, oops.getMessage());
				System.out.println(oops.getLocalizedMessage());
			}
		}
					
		result.addObject("actionURI", "incidence/manager/edit.do");
					
		return result;
	}
				
	// Ancillary methods ----------------------------------
				
	protected ModelAndView createEditModelAndView(Incidence incidence){
		ModelAndView result;
					
		result=createEditModelAndView(incidence, null);
					
		return result;
	}
				
	protected ModelAndView createEditModelAndView(Incidence incidence, String message){
		ModelAndView result;
					
		result=new ModelAndView("incidence/manager/edit");
		result.addObject("incidence", incidence);
		result.addObject("message", message);
					
		return result;
	}

}
