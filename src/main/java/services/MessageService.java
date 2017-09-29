package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Folder;
import domain.Message;
import domain.Owner;

import repositories.MessageRepository;
import security.UserAccount;

@Service
@Transactional
public class MessageService {

	// Managed repository ---------------------------------

	@Autowired
	private MessageRepository messageRepository;

	// Supporting services ---------------------------------
	
	@Autowired
	private OwnerService ownerService;

	@Autowired
	private FolderService folderService;
	
	// Constructors ----------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods -----------------------------------
	
	public Message create(){
		Message result;
		Owner sender;
		Date date;
		Collection<Folder> collectionFolders;
		List<Folder> listFolders;
		Folder folder;
		
		result=new Message();
		date=new Date(System.currentTimeMillis()-1);
		listFolders=new ArrayList<Folder>();
		
		sender=ownerService.findByPrincipal();
		collectionFolders=sender.getFolders();
		
		listFolders.addAll(collectionFolders);
		
		folder=listFolders.get(1);
		
		Assert.notNull(sender, "error.null");
		
		result.setSender(sender);
		result.setMoment(date);
		result.setFolder(folder);

		return result;
	}
	
	public Message findOne(Integer messageId){
		Message result;
		
		result=messageRepository.findOne(messageId);
		
		checkPrincipal(result);
		
		return result;
	}
	
	public void save(Message message){
		Integer messageId;
		
		messageId=message.getId();
		
		if(messageId==0){
			Date moment;
			
			moment=new Date(System.currentTimeMillis()-1);
			
			message.setMoment(moment);
		}
		
		messageRepository.save(message);
	}
	
	public void delete(Message message){
		checkPrincipal(message);
		
		messageRepository.delete(message);
	}

	// Other business methods --------------------------------
	
	public void checkPrincipal(Message message){
		Assert.notNull(message, "error.null");
		
		Folder folder;
		Owner owner;
		UserAccount ownerUserAccount;
		Integer ownerUserAccountId;
		Owner principal;
		UserAccount principalUserAccount;
		Integer principalUserAccountId;
		
		folder=message.getFolder();
		owner=folder.getOwner();
		ownerUserAccount=owner.getUserAccount();
		ownerUserAccountId=ownerUserAccount.getId();
		principal=ownerService.findByPrincipal();
		principalUserAccount=principal.getUserAccount();
		principalUserAccountId=principalUserAccount.getId();
		
		Assert.isTrue(ownerUserAccountId.equals(principalUserAccountId));
	}
	
	public Collection<Message> findAllMessagesByFolder(Integer folderId){
		Collection<Message> result;
		Folder folder;
		
		folder = folderService.findOne(folderId);
		
		Assert.notNull(folder, "error.null");
		
		result=messageRepository.findAllMessagesByFolder(folderId);
		
		return result;
	}
	
	public void send(Message message){
		Message message2;
		Owner owner;
		Collection<Folder> collectionFolders;
		List<Folder> listFolders;
		Folder folder;
		
		message2=this.create();
		owner=message.getRecipient();
		collectionFolders=owner.getFolders();
		listFolders=new ArrayList<Folder>();
		
		listFolders.addAll(collectionFolders);
		
		folder=listFolders.get(0);
		
		message2.setBody(message.getBody());
		message2.setMoment(message.getMoment());
		message2.setSender(message.getSender());
		message2.setRecipient(owner);
		message2.setSubject(message.getSubject());
		message2.setFolder(folder);
		
		this.save(message);
		this.save(message2);
	}
	
	public void deleteMessage(Integer messageId){
		Message message;
		
		message=findOne(messageId);
		
		checkPrincipal(message);
		
		Folder folder;
		String name;
		
		folder=message.getFolder();
		name=folder.getName();
		
		if(name.equals("TRASH")){
			delete(message);
		}else{
			Owner owner;
			Collection<Folder> collectionFolders;
			List<Folder> listFolders;
			Folder trash;
			
			owner=ownerService.findByPrincipal();
			collectionFolders=owner.getFolders();
			listFolders=new ArrayList<Folder>();
			
			listFolders.addAll(collectionFolders);
			
			trash=listFolders.get(2);
			
			message.setFolder(trash);
		}
	}

}
