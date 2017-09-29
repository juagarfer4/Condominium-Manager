package controllers.owner;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MessageService;
import services.OwnerService;

import controllers.AbstractController;
import domain.Message;
import domain.Owner;

@Controller
@RequestMapping("/message/owner")
public class MessageOwnerController extends AbstractController {
	
	private int global;

	// Services --------------------------------------------------------------

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private OwnerService ownerService;

	// Constructors ----------------------------------------------------------

	public MessageOwnerController() {
		super();
	}

	// Displaying ------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int messageId) {
		ModelAndView result;
		Collection<Message> messages;
		Message message;

		messages = new ArrayList<Message>();
		message = messageService.findOne(messageId);

		messages.add(message);

		result = new ModelAndView("message/owner/display");
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/owner/display.do");

		return result;
	}

	// Listing ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int folderId) {
		ModelAndView result;
		Collection<Message> messages;

		messages = messageService.findAllMessagesByFolder(folderId);

		result = new ModelAndView("message/owner/list");
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/owner/list.do");

		return result;
	}

	// Creation -------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int propertyId) {
		ModelAndView result;
		Message message;
		Collection<Owner> recipients;

		message = messageService.create();
		recipients=ownerService.findAllRecipientsByOwner(propertyId);
		global=propertyId;

		result = new ModelAndView("message/owner/edit");
		result.addObject("message", message);
		result.addObject("recipients", recipients);
		result.addObject("actionURI", "message/owner/edit.do");

		return result;
	}

	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Message message, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors().get(0));
			result = createEditModelAndView(message);
		} else {
			try {
				messageService.send(message);
				result = new ModelAndView("redirect:/folder/owner/list.do");
			} catch (Throwable oops) {
//				result = createEditModelAndView(message, "message.commit.error");
				result = createEditModelAndView(message, oops.getMessage());
				System.out.println(oops.getStackTrace());
			}
		}

		result.addObject("actionURI", "message/owner/edit.do");
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int messageId) {
		ModelAndView result;
		
		messageService.deleteMessage(messageId);

		result = new ModelAndView("redirect:/folder/owner/list.do");

		return result;
	}

	// Ancillary methods -----------------------------------------------

	public ModelAndView createEditModelAndView(Message message) {
		ModelAndView result;

		result = createEditModelAndView(message, null);

		return result;
	}

	public ModelAndView createEditModelAndView(Message message, String messsage) {
		ModelAndView result;
		Collection<Owner> recipients;
		
		recipients=ownerService.findAllRecipientsByOwner(global);

		result = new ModelAndView("message/owner/edit");
		result.addObject("message", message);
		result.addObject("recipients", recipients);
		result.addObject("messsage", messsage);

		return result;
	}

}
