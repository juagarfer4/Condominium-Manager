package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BlockService;

import controllers.AbstractController;
import domain.Block;

@Controller
@RequestMapping("/block/manager")
public class BlockManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private BlockService blockService;

	// Constructors ----------------------------------------------------------

	public BlockManagerController() {
		super();
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int communityId) {
		ModelAndView result;
		Collection<Block> blocks;

		blocks = blockService.findAllBlocksByCommunity(communityId);

		result = new ModelAndView("block/manager/list");
		result.addObject("blocks", blocks);
		result.addObject("requestURI", "block/manager/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------------------------

	// Edition ---------------------------------------------------------------

	// Ancillary methods -----------------------------------------------

}
