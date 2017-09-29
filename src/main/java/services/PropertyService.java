package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Block;
import domain.Invoice;
import domain.Owner;
import domain.Property;
import domain.Renter;

import repositories.PropertyRepository;
import security.UserAccount;

@Service
@Transactional
public class PropertyService {

	// Managed repository ---------------------------------

	@Autowired
	private PropertyRepository propertyRepository;

	// Supporting services ---------------------------------


	
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private BlockService blockService;
	
	
	// Constructors ----------------------------------------

	public PropertyService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public Property create(int blockId) {
		Property result;
		Block block;
		Collection<Invoice> invoices;
		Collection<Renter> renters;

		result = new Property();
		invoices = new ArrayList<Invoice>();
		renters=new ArrayList<Renter>();

		block = blockService.findOne(blockId);

		result.setBlock(block);
		result.setInvoices(invoices);
		result.setRenters(renters);

		return result;
	}


	public Property findOne(Integer propertyId) {
		Property result;

		result = propertyRepository.findOne(propertyId);

		Assert.notNull(result);

		return result;
	}

	public Collection<Property> findAll() {
		Collection<Property> result;

		result = propertyRepository.findAll();

		return result;
	}

	public void save(Property property) {
		Assert.notNull(property, "error.null");
		
		
		this.checkPrincipal(property);
		
		Integer propertyId;
		
		propertyId=property.getId();
		
		if(propertyId==0){
			Integer floor;
			Block block;
			Integer numberOfFloors;
			Integer blockId;
			Integer numberOfDoors;
			Integer limit;
			
			floor=property.getFloor();
			block=property.getBlock();
			numberOfFloors=block.getNumberOfFloors();
			blockId=block.getId();
			numberOfDoors=block.getNumberOfDoors();
			limit=blockService.findAllPropetiesByFloor(blockId, floor);
			
			Assert.isTrue(floor<=numberOfFloors, "property.error.floor");
			Assert.isTrue(limit<numberOfDoors, "property.error.door");
			
			//La propiedad door la paso a minusculas
			property.setDoor(property.getDoor().toLowerCase());
			
			//compruebo que no hay una propiedad en ese bloque con los mismos valores 
			Assert.isTrue(this.findAllPropertyByBlockWithSameParameters(property)==0.0, "property.error.repeated");
		}
		
		propertyRepository.save(property);
	}
	
	// Other business methods ------------------------------

	public void checkPrincipal(Property property) {
		Assert.notNull(property, "error.null");

		Administrator administrator;
		administrator = administratorService.findByPrincipal();

		Assert.notNull(administrator, "administrator.error.null");
	}

	public Collection<Property> findAllPropertyByBlock(
			int blockId) {
		Collection<Property> result;

		result = propertyRepository.findAllPropertyByBlock(blockId);

		return result;
	}
	
	public Double findAllPropertyByBlockWithSameParameters(
			Property property) {
		Double result;

		result = propertyRepository.findAllPropertyByBlockWithSameParameters(property.getBlock().getId() , property.getFloor() , property.getDoor() );

		return result;
	}
	
	

	public Collection<Property> findAllPropertiesByOwner(){
		Owner owner;
		
		owner=ownerService.findByPrincipal();
		
		Assert.notNull(owner);
		
		Collection<Property> result;
		UserAccount userAccount;
		Integer userAccountId;
		
		userAccount=owner.getUserAccount();
		userAccountId=userAccount.getId();
		result=propertyRepository.findAllPropertiesByOwner(userAccountId);
		
		return result;
	}

}
