package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Block;
import domain.ChargeHistory;
import domain.Manager;
import domain.Owner;

import repositories.ChargeHistoryRepository;
@Service
@Transactional
public class ChargeHistoryService {

	// Managed repository ---------------------------------

	@Autowired
	private ChargeHistoryRepository chargeHistoryRepository;

	// Supporting services ---------------------------------

	@Autowired
	private BlockService blockService;
	
	@Autowired
	private ManagerService managerService;
	
	// Constructors ----------------------------------------

	public ChargeHistoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------
	
	public ChargeHistory create(int blockId ) {
		ChargeHistory result;
		Block block;
		Date mandateBeginning;

		result = new ChargeHistory();
		mandateBeginning=new Date(System.currentTimeMillis()-1);

		block = blockService.findOne(blockId);

		result.setBlock(block);
		result.setMandateBeginning(mandateBeginning);

		return result;
	}

	public ChargeHistory findOne(Integer chargeHistory) {
		ChargeHistory result;

		result = chargeHistoryRepository.findOne(chargeHistory);

		Assert.notNull(result);

		return result;
	}

	public Collection<ChargeHistory> findAll() {
		Collection<ChargeHistory> result;

		result = chargeHistoryRepository.findAll();

		return result;
	}
	
	public void save(ChargeHistory chargeHistory) {
		this.checkPrincipal(chargeHistory);
		
		
		
		Integer chargeHistoryId;
		Integer blockId;
		
		chargeHistoryId=chargeHistory.getId();
		blockId= chargeHistory.getBlock().getId();
		
		if(chargeHistoryId==0){
			
			this.checkBothCharges(chargeHistory);
			Date date;
			
			date=new Date(System.currentTimeMillis()-1);
			
			chargeHistory.setMandateBeginning(date);
			
			// Comprobar que no se está designando un presidente en un bloque donde ya lo hay
			Assert.isTrue(!(this.findAllActualChargesInBlock(blockId).size()==1 && this.checkThereIsPresident(blockId) && chargeHistory.getIsPresident()), "chargehistory.error.president");
			// Comprobar que no se está designando un secretario en un bloque donde ya lo hay
			Assert.isTrue(!(this.findAllActualChargesInBlock(blockId).size()==1 && this.checkThereIsPresident(blockId)==false && chargeHistory.getIsPresident()==false), "chargehistory.error.secretary");
		}
		
		Date mandateBeginning;
		Date mandateEnding;
		
		mandateBeginning=chargeHistory.getMandateBeginning();
		mandateEnding=chargeHistory.getMandateEnding();
		
		if(mandateEnding!=null){
			Assert.isTrue(mandateBeginning.before(mandateEnding), "chargehistory.error.date");
		}
		
		chargeHistoryRepository.save(chargeHistory);
	}
	
	public void saveEdit(ChargeHistory chargeHistory) {
		this.checkPrincipal(chargeHistory);
		
		Date mandateBeginning;
		Date mandateEnding;
		
		mandateBeginning=chargeHistory.getMandateBeginning();
		mandateEnding=chargeHistory.getMandateEnding();
		
		if(mandateEnding!=null){
			Assert.isTrue(mandateBeginning.before(mandateEnding), "chargehistory.error.date");
		}
		
		chargeHistoryRepository.save(chargeHistory);
	}

	// Other business methods -------------------------------
	
	public void checkPrincipal(ChargeHistory chargeHistory) {
		Assert.notNull(chargeHistory, "error.null");

		Manager manager;

		manager = managerService.findByPrincipal();

		Assert.notNull(manager, "manager.error.null");
	}

	
	public void checkBothCharges(ChargeHistory chargeHistory){
		Block block;
		Owner owner;
		Collection<ChargeHistory> actualChargesInBlock;
		Collection<Owner> actualOwnersInChargesInBlock;
		
		owner=chargeHistory.getOwner();		
		block= chargeHistory.getBlock();
		actualChargesInBlock=chargeHistoryRepository.findAllActualChargesInBlock(block.getId());
		actualOwnersInChargesInBlock=chargeHistoryRepository.findAllActualOwnersInChargesInBlock(block.getId());
		
		Assert.isTrue(actualChargesInBlock.size()< 2, "chargehistory.error.both"); //Si ya estan los puestos cubiertos
		Assert.isTrue(!(actualOwnersInChargesInBlock.contains(owner)), "chargehistory.error.already");//Si ya esta en alguno de los puestos actuales este owner
		
	}
	
	public Collection<ChargeHistory> findAllChargeHistoryByBlock(int blockId) {
		Collection<ChargeHistory> result;

		result = chargeHistoryRepository.findAllChargeHistoryByBlock(blockId);

		return result;
	}
	
	public Collection<ChargeHistory> findAllActualChargesInBlock(int blockId) {
		Collection<ChargeHistory> result;

		result = chargeHistoryRepository.findAllActualChargesInBlock(blockId);

		return result;
	}

		
	public Boolean checkThereIsPresident(int blockId) {
		List<ChargeHistory> charge;
		Boolean result;
		
		result=false;
		charge =new ArrayList<ChargeHistory>( this.findAllActualChargesInBlock(blockId));
		
		if(charge.get(0).getIsPresident()){
			result=true;
		}

		return result;
	}

	



}
