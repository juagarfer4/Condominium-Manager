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

import services.BudgetHistoryService;

import controllers.AbstractController;
import domain.BudgetHistory;

@Controller
@RequestMapping("/budgethistory/manager")
public class BudgetHistoryManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private BudgetHistoryService budgetHistoryService;

	// Constructors ----------------------------------------------------------

	public BudgetHistoryManagerController() {
		super();
	}
	
	@RequestMapping(value="/PDF", method=RequestMethod.GET)
    public void PDF(@RequestParam int budgetHistoryId, HttpServletResponse response) throws Exception{
		budgetHistoryService.PDF(budgetHistoryId, response);
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int communityId) {
		ModelAndView result;
		Collection<BudgetHistory> budgetHistories;

		budgetHistories = budgetHistoryService.findAllBudgetHistoryByCommunity(communityId);

		result = new ModelAndView("budgethistory/manager/list");
		result.addObject("budgetHistories", budgetHistories);
		result.addObject("requestURI", "budgethistory/manager/list.do");

		return result;
	}
	
	// Displaying -----------------------------------------------

	// Creation ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int communityId){
		ModelAndView result;
		BudgetHistory budgetHistory;
			
		budgetHistory = budgetHistoryService.create(communityId);
			
		result = new ModelAndView("budgethistory/manager/edit");
		result.addObject("budgetHistory", budgetHistory);
		result.addObject("actionURI", "budgethistory/manager/edit.do");
		
		return result;
	}

	// Edition ---------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid BudgetHistory budgetHistory, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			System.out.println(binding.getAllErrors().get(0));
			result=createEditModelAndView(budgetHistory);
		}else{
			try{
				budgetHistoryService.save(budgetHistory);
				result=new ModelAndView("redirect:list.do?communityId="+budgetHistory.getCommunity().getId());
			}catch(Throwable oops){
//				result=createEditModelAndView(budgetHistory, "budgetHistory.commit.error");
				result=createEditModelAndView(budgetHistory, oops.getMessage());
				System.out.println(oops.getStackTrace());
			}
		}
			
		result.addObject("actionURI", "budgethistory/manager/edit.do");
		return result;
	}

	// Ancillary methods -----------------------------------------------------
	
	public ModelAndView createEditModelAndView(BudgetHistory budgetHistory){
		ModelAndView result;
			
		result=createEditModelAndView(budgetHistory, null);
			
		return result;
	}
		
	public ModelAndView createEditModelAndView(BudgetHistory budgetHistory, String message){
		ModelAndView result;
			
		result=new ModelAndView("budgethistory/manager/edit");
		result.addObject("budgetHistory", budgetHistory);
		result.addObject("message", message);
		
		return result;
	}

}
