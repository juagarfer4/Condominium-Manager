package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Incidence;
import domain.Block;
import domain.Community;
import domain.Manager;
import domain.Property;

import repositories.IncidenceRepository;

@Service
@Transactional
public class IncidenceService {

	// Managed repository ---------------------------------

	@Autowired
	private IncidenceRepository incidenceRepository;

	// Supporting services ---------------------------------
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private BlockService blockService;
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private PropertyService propertyService;
	
	// Constructors ----------------------------------------

	public IncidenceService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public Incidence createBlock(int blockId) {
		Incidence result;
		Block block;
		Date creationMoment;

		creationMoment = new Date(System.currentTimeMillis() - 1);
		result = new Incidence();

		block = blockService.findOne(blockId);

		result.setBlock(block);
		result.setCreationMoment(creationMoment);
		result.setStatus("UNREAD");

		return result;
	}

	public Incidence createCommunity(int communityId) {
		Incidence result;
		Community community;
		Date creationMoment;

		creationMoment = new Date(System.currentTimeMillis() - 1);
		result = new Incidence();

		community = communityService.findOne(communityId);

		result.setCommunity(community);
		result.setCreationMoment(creationMoment);
		result.setStatus("UNREAD");

		return result;
	}

	public Incidence findOne(Integer incidenceId) {
		Incidence result;

		result = incidenceRepository.findOne(incidenceId);

		Assert.notNull(result);

		return result;
	}

	public Collection<Incidence> findAll() {
		Collection<Incidence> result;

		result = incidenceRepository.findAll();

		return result;
	}

	public void save(Incidence incidence) {
		Assert.notNull(incidence, "error.null");
		
		int id;

		id=incidence.getId();
		
		if(id == 0){
			Date creationMoment;

			creationMoment = new Date(System.currentTimeMillis() - 1);
			
			incidence.setCreationMoment(creationMoment);
			
			Community community;
			Block block;
			
			community=incidence.getCommunity();
			block=incidence.getBlock();
			
			if(block!=null){
				Collection<Property> properties;
				Integer i;
				
				properties=propertyService.findAllPropertiesByOwner();
				i=null;
				
				for(Property p:properties){
					Block b;
					
					b=p.getBlock();
					
					if(b.equals(block)){
						i=0;
					}
				}
				
				Assert.notNull(i, "incidence.error.block");
			}else if(community!=null){
				Collection<Property> properties;
				Integer i;
				
				properties=propertyService.findAllPropertiesByOwner();
				i=null;
				
				for(Property p:properties){
					Block b;
					Community c;
					
					b=p.getBlock();
					c=b.getCommunity();
					
					if(c.equals(community)){
						i=0;
					}
				}
				
				Assert.notNull(i, "incidence.error.community");
			}
		}else {
			this.checkPrincipalManager(incidence);
			
			Integer oldId;
			Incidence oldIncidence;
			String oldStatus;
			
			oldId = incidence.getId();
			oldIncidence = this.findOne(oldId);
			oldStatus = oldIncidence.getStatus();
			
			Assert.isTrue(!(oldStatus.equals("SOLVED")), "incidence.error.solved");
			
			Assert.isTrue(incidence.getStatus().equals("PENDING")||incidence.getStatus().equals("SOLVED"), "incidence.error.status");
		}
		incidenceRepository.save(incidence);
	}
	
	
	// Other business methods ------------------------------

	public void checkPrincipalManager(Incidence incidence) {
		Assert.notNull(incidence, "error.null");

		Manager manager;
		manager = managerService.findByPrincipal();

		Assert.notNull(manager, "manager.error.null");
	}

	public Collection<Incidence> findAllIncidenceByCommunity(
			int communityId) {
		Collection<Incidence> result;

		result = incidenceRepository.findAllIncidenceByCommunity(communityId);

		return result;
	}

	public Collection<Incidence> findAllIncidenceByBlock(
			int blockId) {
		Collection<Incidence> result;

		result = incidenceRepository.findAllIncidenceByBlock(blockId);

		return result;
	}

}
