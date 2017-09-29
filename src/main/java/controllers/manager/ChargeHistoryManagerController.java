package controllers.manager;

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

import domain.Owner;
import domain.ChargeHistory;

import services.ChargeHistoryService;
import services.OwnerService;

@Controller
@RequestMapping("/chargehistory/manager")
public class ChargeHistoryManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private ChargeHistoryService chargeHistoryService;

	@Autowired
	private OwnerService ownerService;

	// Constructors -----------------------------------------------------------

	public ChargeHistoryManagerController() {
		super();
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int blockId) {
		ModelAndView result;
		Collection<ChargeHistory> chargeHistories;

		chargeHistories = chargeHistoryService
				.findAllChargeHistoryByBlock(blockId);

		result = new ModelAndView("chargehistory/manager/list");
		result.addObject("chargeHistories", chargeHistories);
		result.addObject("requestURI", "chargehistory/manager/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int blockId) {
		ModelAndView result;
		ChargeHistory chargeHistory;
		Collection<Owner> owners;

		chargeHistory = chargeHistoryService.create(blockId);
		owners = ownerService.findAllOwnersByBlockNotInCharge(blockId);

		result = new ModelAndView("chargehistory/manager/edit");
		result.addObject("chargeHistory", chargeHistory);
		result.addObject("owners", owners);
		result.addObject("actionURI", "chargehistory/manager/edit.do");

		return result;
	}

	// Edition ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int chargeHistoryId) {
		ModelAndView result;
		ChargeHistory chargeHistory;

		chargeHistory = chargeHistoryService
				.findOne(chargeHistoryId);
		result = createEditModelAndView(chargeHistory);
		
		result = new ModelAndView("chargehistory/manager/edit");
		result.addObject("chargeHistory", chargeHistory);
		result.addObject("actionURI", "chargehistory/manager/edit.do");

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ChargeHistory chargeHistory,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(chargeHistory);
		} else {
			try {

				chargeHistoryService.save(chargeHistory);
				result = new ModelAndView("redirect:list.do?blockId="+chargeHistory.getBlock().getId());
			} catch (Throwable oops) {
//				result = createEditModelAndView(chargeHistory, "chargehistory.commit.error");
				result = createEditModelAndView(chargeHistory, oops.getMessage());
				System.out.println(oops.getStackTrace());
			}
		}

		return result;
	}

	// Ancillary methods
	// ---------------------------------------------------------------

	public ModelAndView createEditModelAndView(
			ChargeHistory chargeHistory) {
		ModelAndView result;

		result = createEditModelAndView(chargeHistory, null);

		return result;

	}

	public ModelAndView createEditModelAndView(
			ChargeHistory chargeHistory, String message) {
		ModelAndView result;
		Collection<Owner> owners;

		owners = ownerService.findAllOwnersByBlockNotInCharge(chargeHistory.getBlock().getId());

		result = new ModelAndView("chargehistory/manager/edit");
		result.addObject("chargeHistory", chargeHistory);
		result.addObject("owners", owners);
		result.addObject("message", message);

		return result;
	}

}
