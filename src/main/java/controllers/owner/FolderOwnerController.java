package controllers.owner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;

import controllers.AbstractController;
import domain.Folder;

@Controller
@RequestMapping("/folder/owner")
public class FolderOwnerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private FolderService folderService;

	// Constructors ----------------------------------------------------------

	public FolderOwnerController() {
		super();
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Folder> folders;

		folders = folderService.findAllFoldersByOwner();

		result = new ModelAndView("folder/owner/list");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/owner/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------
	
	// Creation -----------------------------------------------
	
	// Edition -----------------------------------------------
	
	// Ancillary methods --------------------------------------

}
