package controllers.owner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PaymentService;

import controllers.AbstractController;
import domain.Payment;

@Controller
@RequestMapping("/payment/owner")
public class PaymentOwnerController extends AbstractController {

	// Services --------------------------------------------------------------

	@Autowired
	private PaymentService paymentService;

	// Constructors ----------------------------------------------------------

	public PaymentOwnerController() {
		super();
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int communityId) {
		ModelAndView result;
		Collection<Payment> payments;

		payments = paymentService.findAllPaymentByCommunity(communityId);

		result = new ModelAndView("payment/owner/list");
		result.addObject("payments", payments);
		result.addObject("requestURI", "payment/owner/list.do");

		return result;
	}

	// Displaying ------------------------------------------------
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int paymentId) {
		ModelAndView result;
		Payment payment;
		String requestURI;

		payment = paymentService.findOne(paymentId);
		requestURI = "payment/owner/display.do?paymentId="+paymentId;
	
		result = new ModelAndView("payment/owner/display");

		result.addObject("payment", payment);
		result.addObject("requestURI", requestURI);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ---------------------------------------------------------------

	// Ancillary methods -----------------------------------------------------

}
