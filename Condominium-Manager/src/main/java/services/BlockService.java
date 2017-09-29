package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Announcement;
import domain.Block;
import domain.ChargeHistory;
import domain.Community;
import domain.Incidence;
import domain.NeighborsBoard;
import domain.Property;


import repositories.BlockRepository;
@Service
@Transactional
public class BlockService {

	// Managed repository ---------------------------------

	@Autowired
	private BlockRepository blockRepository;

	// Supporting services ---------------------------------

	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private AdministratorService administratorService;
	
	// Constructors ----------------------------------------

	public BlockService() {
		super();
	}

	// Simple CRUD methods ----------------------------------

	public Block create(Integer communityId){
		Community community;
		
		community=communityService.findOne(communityId);
		
		
		Block result;
		String code;
		Collection<Incidence> incidences;
		Collection<Announcement> announcements;
		Collection<ChargeHistory> chargeHistories;
		Collection<Property> properties;
		Collection<NeighborsBoard> neighborsBoards;
		
		result=new Block();
		code="noCode";
		incidences = new ArrayList<Incidence>();
		announcements = new ArrayList<Announcement>();
		chargeHistories = new ArrayList<ChargeHistory>();
		properties = new ArrayList<Property>();
		neighborsBoards = new ArrayList<NeighborsBoard>();
		
		result.setCode(code);
		result.setIncidences(incidences);
		result.setAnnouncements(announcements);
		result.setChargeHistories(chargeHistories);
		result.setProperties(properties);
		result.setNeighborsBoards(neighborsBoards);
		result.setCommunity(community);
		
		return result;
	}
	
	public Block findOne(Integer blockId){
		Block result;
		
		result=blockRepository.findOne(blockId);
		
		return result;
	}
	
	public Collection<Block> findAll(Integer blockId){
		Collection<Block> result;
		
		result=blockRepository.findAll();
		
		return result;
	}
	
	public void save(Block block){
		this.checkPrincipal(block);
		
		Integer blockId;
		
		blockId=block.getId();
		
		if(blockId==0){
			Integer communityId;
			String code;
			Community community;
			
			communityId= block.getCommunity().getId();
			community=communityService.findOne(communityId);
			
			//Comprobacion de que no existe ese bloque en la comunidad
			Assert.isTrue(this.findNumberOfBlockSameNumberInCommunity(communityId, block.getNumber())== 0.0, "block.error.repeated");
			
			code= community.getName()+"-"+block.getNumber();
			
			block.setCode(code);
		}else{
			this.check(block);
		}
		
		blockRepository.save(block);
	}
	
	// Other business methods -------------------------------------------
	
	public void checkPrincipal(Block block) {
		Assert.notNull(block, "error.null");

		Administrator administrator;
		administrator = administratorService.findByPrincipal();

		Assert.notNull(administrator, "administrator.error.null");
	}
	
	public Collection<Block> findAllBlocksByCommunity(int communityId){
		Collection<Block> result;
		
		result=blockRepository.findAllBlocksByCommunity(communityId);
		
		return result;
	}
	
	public Double findNumberOfBlockSameNumberInCommunity(int communityId ,int numberOfBlock){
		Double result;
		
		result=blockRepository.findNumberOfBlockSameNumberInCommunity(communityId,numberOfBlock);
		
		return result;
	}
	
	public Integer findAllPropetiesByFloor(Integer blockId, Integer floor){
		Integer result;
		
		result=blockRepository.findAllPropetiesByFloor(blockId, floor);
		
		return result;
	}
	
	public void check(Block newBlock) {
		Assert.notNull(newBlock);

		this.checkPrincipal(newBlock);

		Block oldBlock;
		Integer id;

		id = newBlock.getId();
		oldBlock = this.findOne(id);

		Integer oldNumberOfFloors;
		Integer oldNumberOfDoors;
		Integer newNumberOfFloors;
		Integer newNumberOfDoors;

		oldNumberOfFloors = oldBlock.getNumberOfFloors();
		oldNumberOfDoors = oldBlock.getNumberOfDoors();
		newNumberOfFloors = newBlock.getNumberOfFloors();
		newNumberOfDoors = newBlock.getNumberOfDoors();

		if (!(oldNumberOfFloors.compareTo(newNumberOfFloors) == 0)) {
			Assert.isTrue(oldNumberOfFloors<=newNumberOfFloors, "block.error.floors");
		}

		if (!(oldNumberOfDoors.compareTo(newNumberOfDoors) == 0)) {
			Assert.isTrue(oldNumberOfDoors<=newNumberOfDoors, "block.error.doors");
		}
	}
	
}