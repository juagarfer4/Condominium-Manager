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

import services.AnnouncementService;

import controllers.AbstractController;
import domain.Announcement;

@Controller
@RequestMapping("/announcement/manager")
public class AnnouncementManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private AnnouncementService announcementService;

	// Constructors ----------------------------------------------------------

	public AnnouncementManagerController() {
		super();
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/listbyblock", method = RequestMethod.GET)
	public ModelAndView listByBlock(@RequestParam int blockId) {
		ModelAndView result;
		Collection<Announcement> announcements;

		announcements = announcementService.findAllAnnouncementByBlock(blockId);

		result = new ModelAndView("announcement/manager/listbyblock");
		result.addObject("announcements", announcements);
		result.addObject("requestURI", "announcement/manager/listbyblock.do");

		return result;
	}

	@RequestMapping(value = "/listbycommunity", method = RequestMethod.GET)
	public ModelAndView listByCommunity(@RequestParam int communityId) {
		ModelAndView result;
		Collection<Announcement> announcements;

		announcements = announcementService.findAllAnnouncementByCommunity(communityId);

		result = new ModelAndView("announcement/manager/listbycommunity");
		result.addObject("announcements", announcements);
		result.addObject("requestURI", "announcement/manager/listbycommunity.do");

		return result;
	}
	
	// Displaying -----------------------------------------------
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int announcementId) {
		ModelAndView result;
		Announcement announcement;
		String requestURI;

		announcement = announcementService.findOne(announcementId);
		requestURI = "announcement/manager/display.do?announcementId="+announcementId;
	
		result = new ModelAndView("announcement/manager/display");

		result.addObject("announcement", announcement);
		result.addObject("requestURI", requestURI);

		return result;
	}

	// Creation ---------------------------------------------------------------
	
	@RequestMapping(value = "/createbycommunity", method = RequestMethod.GET)
	public ModelAndView createByCommunity(@RequestParam Integer communityId) {
		ModelAndView result;
		Announcement announcement;

		announcement = announcementService.createCommunity(communityId);

		result = new ModelAndView("announcement/manager/edit");
		result.addObject("announcement", announcement);
		result.addObject("actionURI", "announcement/manager/edit.do");

		return result;
	}
	
	@RequestMapping(value = "/createbyblock", method = RequestMethod.GET)
	public ModelAndView createByBlock(@RequestParam Integer blockId) {
		ModelAndView result;
		Announcement announcement;

		announcement = announcementService.createBlock(blockId);

		result = new ModelAndView("announcement/manager/edit");
		result.addObject("announcement", announcement);
		result.addObject("actionURI", "announcement/manager/edit.do");

		return result;
	}

	// Edition ---------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int announcementId){
		ModelAndView result;
		Announcement announcement;				

		announcement = announcementService.findOne(announcementId);
		
		Assert.notNull(announcement);

		result = createEditModelAndView(announcement);
		
		result = new ModelAndView("announcement/manager/edit");
		result.addObject("announcement", announcement);
		result.addObject("actionURI", "announcement/manager/edit.do");

		return result;
	}
				
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Announcement announcement, BindingResult binding){
		ModelAndView result;
	
		if(binding.hasErrors()){
			System.out.println(binding.getAllErrors().get(0));
			result=createEditModelAndView(announcement);
		}else{
			try{
				announcementService.save(announcement);
				if(announcement.getCommunity()!=null){
					result=new ModelAndView("redirect:listbycommunity.do?communityId="+announcement.getCommunity().getId());
				}else if(announcement.getBlock()!=null){
					result=new ModelAndView("redirect:listbyblock.do?blockId="+announcement.getBlock().getId());
				}else{
					result=new ModelAndView("redirect:/community/manager/list.do");
				}
			}catch (Throwable oops){
				result=createEditModelAndView(announcement, "announcement.commit.error");
				System.out.println(oops.getLocalizedMessage());
			}
		}
					
		result.addObject("actionURI", "announcement/manager/edit.do");
					
		return result;
	}

	// Ancillary methods -----------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Announcement announcement){
		ModelAndView result;
					
		result=createEditModelAndView(announcement, null);
					
		return result;
	}
				
	protected ModelAndView createEditModelAndView(Announcement announcement, String message){
		ModelAndView result;
					
		result=new ModelAndView("announcement/manager/edit");
		result.addObject("announcement", announcement);
		result.addObject("message", message);
					
		return result;
	}

}
