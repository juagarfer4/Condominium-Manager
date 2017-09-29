package controllers.owner;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BudgetHistoryService;

import controllers.AbstractController;
import domain.BudgetHistory;

@Controller
@RequestMapping("/budgethistory/owner")
public class BudgetHistoryOwnerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private BudgetHistoryService budgetHistoryService;

	// Constructors ----------------------------------------------------------

	public BudgetHistoryOwnerController() {
		super();
	}

	@RequestMapping(value = "/PDF", method = RequestMethod.GET)
	public void PDF(@RequestParam int budgetHistoryId,
			HttpServletResponse response) throws Exception {
		budgetHistoryService.PDF(budgetHistoryId, response);
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int communityId) {
		ModelAndView result;
		Collection<BudgetHistory> budgetHistories;

		budgetHistories = budgetHistoryService
				.findAllBudgetHistoryByCommunity(communityId);

		result = new ModelAndView("budgethistory/owner/list");
		result.addObject("budgetHistories", budgetHistories);
		result.addObject("requestURI", "budgethistory/owner/list.do");

		return result;
	}

	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------------------------
	
	// Edition ---------------------------------------------------------------
	
	// Ancillary methods -----------------------------------------------------

}
