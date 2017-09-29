package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Folder;
import domain.Message;
import domain.Owner;

import repositories.FolderRepository;
import security.UserAccount;

@Service
@Transactional
public class FolderService {

	// Managed repository ---------------------------------

	@Autowired
	private FolderRepository folderRepository;
	
	// Supporting services ---------------------------------
	
	@Autowired
	private OwnerService ownerService;
	
	// Constructors ----------------------------------------
	
	public FolderService(){
		super();
	}
	
	// Simple CRUD methods -----------------------------------
	
	public Folder create(Owner owner){
		Assert.notNull(owner, "error.null");
		
		Folder result;
		Collection<Message> messages;
		
		result=new Folder();
		messages=new ArrayList<Message>();
		
		result.setMessages(messages);
		result.setOwner(owner);
		
		return result;
	}
	
	public Folder findOne (Integer folderId){
		Folder result;
		Owner owner;
		Owner principal;
		
		result=folderRepository.findOne(folderId);
		owner=result.getOwner();
		principal=ownerService.findByPrincipal();
		
		Assert.isTrue(owner==principal);
		
		return result;
	}
	
	public void save(Folder folder){
		String name;
		
		name=folder.getName();
		
		Assert.isTrue(name=="INBOX"||name=="OUTBOX"||name=="TRASH", "folder.error.name");
		
		folderRepository.save(folder);
	}
	
	// Other business methods --------------------------------
	
	public Collection<Folder> findAllFoldersByOwner(){
		Owner principal;
		
		principal=ownerService.findByPrincipal();
		
		Collection<Folder> result;
		UserAccount userAccount;
		Integer userAccountId;
		
		userAccount=principal.getUserAccount();
		userAccountId=userAccount.getId();
		
		result=folderRepository.findAllFoldersByOwner(userAccountId);
		
		return result;
	}

}
