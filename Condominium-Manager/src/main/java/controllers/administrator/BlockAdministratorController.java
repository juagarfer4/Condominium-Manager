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

import domain.Block;

import services.BlockService;

@Controller
@RequestMapping("/block/administrator")
public class BlockAdministratorController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private BlockService blockService;

	// Constructors -----------------------------------------------------------

	public BlockAdministratorController() {
		super();
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int communityId) {
		ModelAndView result;
		Collection<Block> blocks;

		blocks = blockService.findAllBlocksByCommunity(communityId);

		result = new ModelAndView("block/administrator/list");
		result.addObject("blocks", blocks);
		result.addObject("requestURI", "block/administrator/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int communityId) {
		ModelAndView result;
		Block block;

		block  = blockService.create(communityId);

		result = new ModelAndView("block/administrator/edit");
		result.addObject("block", block);
		result.addObject("actionURI", "block/administrator/edit.do");

		return result;
	}

	// Edition ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int blockId) {
		ModelAndView result;
		Block block;

		block = blockService.findOne(blockId);
		result = createEditModelAndView(block);
		
		result = new ModelAndView("block/administrator/edit");
		result.addObject("block", block);
		result.addObject("actionURI", "block/administrator/edit.do");

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Block block, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(block);
		} else {
			try {
				blockService.save(block);
				result = new ModelAndView("redirect:list.do?communityId="+block.getCommunity().getId());
			} catch (Throwable oops) {
//				result = createEditModelAndView(block, "block.commit.error");
				result = createEditModelAndView(block, oops.getMessage());
				System.out.println(oops.getStackTrace());
			}
		}

		return result;
	}

	// Ancillary methods ---------------------------------------------------------------

	public ModelAndView createEditModelAndView(Block block) {
		ModelAndView result;

		result = createEditModelAndView(block, null);

		return result;

	}

	public ModelAndView createEditModelAndView(Block block, String message) {
		ModelAndView result;

		result = new ModelAndView("block/administrator/edit");
		result.addObject("block", block);
		result.addObject("message", message);

		return result;
	}

}
