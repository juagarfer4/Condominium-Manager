package controllers.owner;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NeighborsBoardService;

import controllers.AbstractController;
import domain.NeighborsBoard;

@Controller
@RequestMapping("/neighborsboard/owner")
public class NeighborsBoardOwnerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private NeighborsBoardService neighborsBoardService;

	// Constructors ----------------------------------------------------------

	public NeighborsBoardOwnerController() {
		super();
	}
	
	@RequestMapping(value="/PDF", method=RequestMethod.GET)
    public void PDF(@RequestParam int neighborsBoardId, HttpServletResponse response) throws Exception{
		neighborsBoardService.PDF(neighborsBoardId, response);
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int blockId) {
		ModelAndView result;
		Collection<NeighborsBoard> neighborsBoards;

		neighborsBoards = neighborsBoardService.findAllNeighborsBoardByBlock(blockId);

		result = new ModelAndView("neighborsboard/owner/list");
		result.addObject("neighborsBoards", neighborsBoards);
		result.addObject("requestURI", "neighborsboard/owner/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------------------------

	// Edition ---------------------------------------------------------------

	// Ancillary methods ----------------------------------------------------

}
