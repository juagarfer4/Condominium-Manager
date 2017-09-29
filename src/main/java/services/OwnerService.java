package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Block;
import domain.ChargeHistory;
import domain.Community;
import domain.Folder;
import domain.NeighborsBoard;
import domain.Owner;
import domain.Property;
import forms.OwnerForm;

import repositories.OwnerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class OwnerService {

	// Managed repository ---------------------------------

	@Autowired
	private OwnerRepository ownerRepository;

	// Supporting services ---------------------------------

	@Autowired
	private ChargeHistoryService chargeHistoryService;
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private FolderService folderService;
	
	// Constructors ----------------------------------------

	public OwnerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Owner create() {
		Owner result;
		UserAccount userAccount;
		Collection<Authority> authorities;
		Authority authority;
		Collection<Property> properties;
		Collection<ChargeHistory> chargeHistories;
		Collection<NeighborsBoard> neighborsBoards;
		Collection<Folder> folders;
		Folder inbox;
		Folder outbox;
		Folder trash;
		

		result = new Owner();
		userAccount = new UserAccount();
		authorities = new ArrayList<Authority>();
		authority = new Authority();
		properties = new ArrayList<Property>();
		chargeHistories = new ArrayList<ChargeHistory>();
		neighborsBoards=new ArrayList<NeighborsBoard>();
		folders=new ArrayList<Folder>();
		inbox=folderService.create(result);
		outbox=folderService.create(result);
		trash=folderService.create(result);

		authority.setAuthority("OWNER");

		authorities.add(authority);
		
		inbox.setName("INBOX");
		outbox.setName("OUTBOX");
		trash.setName("TRASH");
		
		folders.add(inbox);
		folders.add(outbox);
		folders.add(trash);

		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		result.setProperties(properties);
		result.setChargeHistories(chargeHistories);
		result.setNeighborsBoards(neighborsBoards);
		result.setFolders(folders);

		return result;
	}

	public Owner findOne(Integer ownerId) {
		Owner result;

		result = ownerRepository.findOne(ownerId);

		return result;
	}

	public Collection<Owner> findAll() {
		Collection<Owner> result;

		result = ownerRepository.findAll();

		return result;
	}

	public void save(Owner owner) {
		Assert.notNull(owner, "error.null");

		this.encodePassword(owner);

		ownerRepository.save(owner);
	}

	// Other business methods -------------------------------

	public Owner findByPrincipal() {
		Owner result;
		UserAccount userAccount;
		Integer userAccountId;

		userAccount = LoginService.getPrincipal();
		
		Assert.notNull(userAccount);

		userAccountId = userAccount.getId();

		result = ownerRepository.findByUserAccountId(userAccountId);

		Assert.notNull(result);

		return result;
	}

	public void encodePassword(Owner owner) {
		UserAccount userAccount;
		String password;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		userAccount = owner.getUserAccount();
		password = userAccount.getPassword();
		password = encoder.encodePassword(password, null);
		userAccount.setPassword(password);
	}

	public Owner reconstruct(OwnerForm ownerForm) {
		Owner owner = this.create();

		Assert.isTrue(ownerForm.isCondition(), "owner.error.condition");
		Assert.isTrue(ownerForm.getPassword().equals(ownerForm.getPasswordVerificada()), "owner.error.password");

		owner.setName(ownerForm.getName());
		owner.setSurname(ownerForm.getSurname());
		owner.setEmail(ownerForm.getEmail());
		owner.setPhone(ownerForm.getPhone());
		owner.getUserAccount().setPassword(ownerForm.getPassword());
		owner.getUserAccount().setUsername(ownerForm.getUsername());

		return owner;
	}
	
	public Collection<Owner> findAllOwnersByBlock(Integer blockId){
		Collection<Owner> result;
		
		result=ownerRepository.findAllOwnersByBlock(blockId);
		
		return result;
	}
	
	public Collection<Owner> findAllOwnersByBlockNotInCharge(Integer blockId){
		Collection<Owner> result;
		List<ChargeHistory> charges;
		Owner owner1;
		Owner owner2;
		
		charges =new ArrayList<ChargeHistory> (chargeHistoryService.findAllActualChargesInBlock(blockId) );
		result=ownerRepository.findAllOwnersByBlock(blockId);
		
		if(charges.size()!=0){
			if(charges.size()==1){
				owner1=  charges.get(0).getOwner();
				
				result.remove(owner1);
			}else{
				owner1=  charges.get(0).getOwner();
				owner2=  charges.get(1).getOwner();
				
				result.remove(owner1);
				result.remove(owner2);
			}
		}
		
		return result;
	}
	
	public Collection<Owner> findAllAttendantsByNeighborsBoard(Integer neighborsBoardId){
		Collection<Owner> result;
		
		result=ownerRepository.findAllAttendantsByNeighborsBoard(neighborsBoardId);
		
		return result;
	}
	
	public Collection<Owner> findAllRecipientsByOwner(){
		Collection<Owner> result;
		Owner owner;
		
		result=findAll();
		owner=findByPrincipal();
		
		result.remove(owner);
		
		return result;
	}
	
	public Collection<Owner> findAllRecipientsByOwner(Integer propertyId){
		Property property;
		
		property=propertyService.findOne(propertyId);
		
		Assert.notNull(property);
		
		Collection<Owner> result;
		Block block;
		Community community;
		Integer communityId;
		Owner owner;
		
		block=property.getBlock();
		community=block.getCommunity();
		communityId=community.getId();
		result=ownerRepository.findAllRecipientesByOwner(communityId);
		owner=findByPrincipal();
		
		result.remove(owner);
		
		return result;
	}

}
