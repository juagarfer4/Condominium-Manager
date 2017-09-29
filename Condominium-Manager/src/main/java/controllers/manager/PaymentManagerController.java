package controllers.manager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import services.PaymentService;

import controllers.AbstractController;
import domain.Payment;
import forms.CommunityForm;
import forms.GoogleSheetForm;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/payment/manager")
public class PaymentManagerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private PaymentService paymentService;

	// Constructors ----------------------------------------------------------

	public PaymentManagerController() {
		super();
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	
	@RequestMapping(value="/downloadcsv", method = RequestMethod.GET)
	public void downloadFileCSV(HttpServletResponse response) throws IOException, URISyntaxException {
		paymentService.downloadFileCSV(response);
	}
	
	@RequestMapping(value="/downloadgoogledrive", method = RequestMethod.GET)
	public void downloadFileGoogleDrive(HttpServletResponse response) throws IOException, URISyntaxException {
		paymentService.downloadFileGoogleDrive(response);
	}
	
	@RequestMapping(value="/tutorial", method = RequestMethod.GET)
	public ModelAndView tutorial() {
		ModelAndView result;

		result = new ModelAndView("payment/manager/tutorial");
		result.addObject("requestURI", "payment/manager/tutorial.do");

		return result;
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int communityId) {
		ModelAndView result;
		Collection<Payment> payments;

		payments = paymentService.findAllPaymentByCommunity(communityId);

		result = new ModelAndView("payment/manager/list");
		result.addObject("payments", payments);
		result.addObject("requestURI", "payment/manager/list.do");

		return result;
	}
	
	
	// Displaying -----------------------------------------------
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int paymentId) {
		ModelAndView result;
		Payment payment;
		String requestURI;

		payment = paymentService.findOne(paymentId);
		requestURI = "payment/manager/display.do?paymentId="+paymentId;
	
		result = new ModelAndView("payment/manager/display");

		result.addObject("payment", payment);
		result.addObject("requestURI", requestURI);

		return result;
	}

	// Creation ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int communityId){
		ModelAndView result;
		Payment payment;
			
		payment = paymentService.create(communityId);
			
		result = new ModelAndView("payment/manager/edit");
		result.addObject("payment", payment);
		result.addObject("actionURI", "payment/manager/edit.do");
		
		return result;
	}

	// Edition ---------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Payment payment, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			System.out.println(binding.getAllErrors().get(0));
			result=createEditModelAndView(payment);
		}else{
			try{
				paymentService.save(payment);
				result=new ModelAndView("redirect:list.do?communityId="+payment.getCommunity().getId());
			}catch(Throwable oops){
				result=createEditModelAndView(payment, "payment.commit.error");
				System.out.println(oops.getStackTrace());
			}
		}
			
		result.addObject("actionURI", "payment/manager/edit.do");
		return result;
	}
	
	@RequestMapping(value="/importer", method=RequestMethod.GET)
	public ModelAndView importer(@RequestParam int communityId){
		ModelAndView result;
		CommunityForm communityForm;
		
		communityForm = new CommunityForm();
		
		communityForm.setCommunityId(communityId);
		
		result = new ModelAndView("payment/manager/importer");
		result.addObject("communityForm", communityForm);
		result.addObject("actionURI", "payment/manager/saveimporter.do");
		
		return result;
	}
	
	@RequestMapping(value="/saveimporter", method=RequestMethod.POST, params="saveimporter")
	public ModelAndView saveimporter(@Valid CommunityForm communityForm){
		ModelAndView result;
		byte[] csv;
		String csvCad;
		
		csv = communityForm.getCsv();
		paymentService.arrayBytesToCsv(csv,communityForm.getCommunityId());
//		paymentService.importPayment(csvCad, communityForm.getCommunityId());
		result=new ModelAndView("redirect:list.do?communityId="+ communityForm.getCommunityId());
		
		return result;
	}
	
	@RequestMapping(value="/sheetdrive", method=RequestMethod.GET)
	public ModelAndView sheetdrive(@RequestParam int communityId){
		ModelAndView result;
		GoogleSheetForm googleSheetForm;
		
		googleSheetForm = new GoogleSheetForm();
		
		googleSheetForm.setCommunityId(communityId);
		
		result = new ModelAndView("payment/manager/sheetdrive");
		result.addObject("googleSheetForm", googleSheetForm);
		result.addObject("actionURI", "payment/manager/savesheetdrive.do");
		
		return result;
	}
	
	@RequestMapping(value="/savesheetdrive", method=RequestMethod.POST, params="savesheetdrive")
	public ModelAndView savesheeetdrive(GoogleSheetForm googleSheetForm){
		ModelAndView result;
		String url;
		
		url=googleSheetForm.getUrlSheet();
		try {
			paymentService.googleSheetsApiDownload(googleSheetForm);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		paymentService.importPayment(csvCad, communityForm.getCommunityId());
		result=new ModelAndView("redirect:list.do?communityId="+ googleSheetForm.getCommunityId());
		
		return result;
	}

	// Ancillary methods -----------------------------------------------------
	
	public ModelAndView createEditModelAndView(Payment payment){
		ModelAndView result;
			
		result=createEditModelAndView(payment, null);
			
		return result;
	}
		
	public ModelAndView createEditModelAndView(Payment payment, String message){
		ModelAndView result;
			
		result=new ModelAndView("payment/manager/edit");
		result.addObject("payment", payment);
		result.addObject("message", message);
		
		return result;
	}
	
	

}
