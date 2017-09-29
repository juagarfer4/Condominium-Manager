package controllers.owner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;

import controllers.AbstractController;
import domain.Announcement;

@Controller
@RequestMapping("/announcement/owner")
public class AnnouncementOwnerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private AnnouncementService announcementService;

	// Constructors ----------------------------------------------------------

	public AnnouncementOwnerController() {
		super();
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/listbyblock", method = RequestMethod.GET)
	public ModelAndView listByBlock(@RequestParam int blockId) {
		ModelAndView result;
		Collection<Announcement> announcements;

		announcements = announcementService.findAllAnnouncementByBlock(blockId);

		result = new ModelAndView("announcement/owner/listbyblock");
		result.addObject("announcements", announcements);
		result.addObject("requestURI", "announcement/owner/listbyblock.do");

		return result;
	}
	
	@RequestMapping(value = "/listbycommunity", method = RequestMethod.GET)
	public ModelAndView listByCommunity(@RequestParam int communityId) {
		ModelAndView result;
		Collection<Announcement> announcements;

		announcements = announcementService.findAllAnnouncementByCommunity(communityId);

		result = new ModelAndView("announcement/owner/listbycommunity");
		result.addObject("announcements", announcements);
		result.addObject("requestURI", "announcement/owner/listbycommunity.do");

		return result;
	}
	
	// Displaying -----------------------------------------------
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int announcementId) {
		ModelAndView result;
		Announcement announcement;
		String requestURI;

		announcement = announcementService.findOne(announcementId);
		requestURI = "announcement/owner/display.do?announcementId="+announcementId;
	
		result = new ModelAndView("announcement/owner/display");

		result.addObject("announcement", announcement);
		result.addObject("requestURI", requestURI);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ---------------------------------------------------------------

	// Ancillary methods ---------------------------------------------------------------

}
