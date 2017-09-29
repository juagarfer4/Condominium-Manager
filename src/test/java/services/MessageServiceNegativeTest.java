package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import domain.Message;
import domain.Owner;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class MessageServiceNegativeTest extends AbstractTest {
	
	// Service under test -------------------------------
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private OwnerService ownerService;
	
	// Tests ---------------------------------------
	
	// Send a message with null recipient. -----------------------
	
	@Test(expected=java.lang.NullPointerException.class)
	public void testNegativeSendMessageNullRecipient(){
		authenticate("owner1");
		
		Message message;
		Owner recipient;
		
		message=messageService.create();
		recipient=ownerService.findOne(870);
		
		message.setSubject("subject");
		message.setBody("body");
		message.setRecipient(recipient);
		
		messageService.send(message);
		
		unauthenticate();
	}
	
	// Delete a null message. ---------------------------------
	
	@Test(expected=java.lang.IllegalArgumentException.class)
	public void testNegativeDeleteMessageNullMessage(){
		authenticate("owner1");
		
		messageService.deleteMessage(930);
		
		unauthenticate();
	}

}
