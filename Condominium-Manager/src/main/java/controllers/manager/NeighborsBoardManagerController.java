package controllers.manager;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NeighborsBoardService;

import controllers.AbstractController;
import domain.Block;
import domain.NeighborsBoard;
import domain.Owner;
import forms.NeighborsBoardForm;

@Controller
@RequestMapping("/neighborsboard/manager")
public class NeighborsBoardManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private NeighborsBoardService neighborsBoardService;

	// Constructors ----------------------------------------------------------

	public NeighborsBoardManagerController() {
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

		result = new ModelAndView("neighborsboard/manager/list");
		result.addObject("neighborsBoards", neighborsBoards);
		result.addObject("requestURI", "neighborsboard/manager/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------
	
	// Creation --------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int blockId) {
		ModelAndView result;
		NeighborsBoard neighborsBoard;

		neighborsBoard = neighborsBoardService.create(blockId);

		result = new ModelAndView("neighborsboard/manager/edit");
		result.addObject("neighborsBoard", neighborsBoard);
		result.addObject("actionURI", "neighborsboard/manager/edit.do");

		return result;
	}

	// Edition ---------------------------------------------------------------
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam int neighborsBoardId) {
		ModelAndView result;
		NeighborsBoardForm neighborsBoardForm;
		Collection<Owner> attendants;

		neighborsBoardForm = new NeighborsBoardForm();
		attendants=neighborsBoardService.findAllOwnersNotInThisNeighborsBoard(neighborsBoardId);
		
		neighborsBoardForm.setNeighborsBoardId(neighborsBoardId);

		result = new ModelAndView("neighborsboard/manager/add");
		result.addObject("neighborsBoardForm", neighborsBoardForm);
		result.addObject("attendants", attendants);
		result.addObject("actionURI", "neighborsboard/manager/add.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid NeighborsBoard neighborsBoard, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(neighborsBoard);
		} else {
			try {
				neighborsBoardService.save(neighborsBoard);
				result = new ModelAndView("redirect:list.do?blockId="+neighborsBoard.getBlock().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(neighborsBoard, "neighborsboard.commit.error");
				System.out.println(oops.getStackTrace());
			}
		}

		result.addObject("actionURI", "neighborsboard/manager/edit.do");
		return result;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, params = "save2")
	public ModelAndView save(@Valid NeighborsBoardForm neighborsBoardForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView2(neighborsBoardForm);
		} else {
			try {
				neighborsBoardService.reconstruct(neighborsBoardForm);
				
				Integer neighborsBoardId;
				NeighborsBoard neighborsBoard;
				Block block;
				Integer blockId;
				
				neighborsBoardId = neighborsBoardForm.getNeighborsBoardId();
				neighborsBoard = neighborsBoardService.findOne(neighborsBoardId);
				block = neighborsBoard.getBlock();
				blockId = block.getId();
				
				result = new ModelAndView("redirect:list.do?blockId="+blockId);
			} catch (Throwable oops) {
				result = createEditModelAndView2(neighborsBoardForm, "neighborsboard.commit.error");
				System.out.println(oops.getStackTrace());
			}
		}

		result.addObject("actionURI", "neighborsboard/manager/add.do");
		return result;
	}

	// Ancillary methods -----------------------------------------------

	public ModelAndView createEditModelAndView(NeighborsBoard neighborsBoard) {
		ModelAndView result;

		result = createEditModelAndView(neighborsBoard, null);

		return result;
	}

	public ModelAndView createEditModelAndView(NeighborsBoard neighborsBoard, String message) {
		ModelAndView result;

		result = new ModelAndView("neighborsboard/manager/edit");
		result.addObject("neighborsBoard", neighborsBoard);
		result.addObject("message", message);

		return result;
	}
	
	public ModelAndView createEditModelAndView2(NeighborsBoardForm neighborsBoardForm) {
		ModelAndView result;

		result = createEditModelAndView2(neighborsBoardForm, null);

		return result;
	}
	
	public ModelAndView createEditModelAndView2(NeighborsBoardForm neighborsBoardForm, String message) {
		ModelAndView result;
		Integer neighborsBoardId;
		Collection<Owner> attendants;
		
		neighborsBoardId=neighborsBoardForm.getNeighborsBoardId();
		attendants=neighborsBoardService.findAllOwnersNotInThisNeighborsBoard(neighborsBoardId);

		result = new ModelAndView("neighborsboard/manager/add");
		result.addObject("neighborsBoardForm", neighborsBoardForm);
		result.addObject("attendants", attendants);
		result.addObject("message", message);

		return result;
	}

}
