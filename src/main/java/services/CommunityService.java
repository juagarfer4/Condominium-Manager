package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Announcement;
import domain.BudgetHistory;
import domain.Community;
import domain.Block;
import domain.Contract;
import domain.Incidence;
import domain.Invoice;
import domain.Payment;
import domain.Property;

import repositories.CommunityRepository;

@Service
@Transactional
public class CommunityService {

	// Managed repository ---------------------------------

	@Autowired
	private CommunityRepository communityRepository;

	// Supporting services ---------------------------------
	
	@Autowired
	private AdministratorService administratorService;
	
	// Constructors ----------------------------------------

	public CommunityService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public Community create() {
		Community result;
		Collection<Payment> payments;
		Collection<Incidence> incidences;
		Collection<Announcement> announcements;
		Collection<Block> blocks;
		Collection<Contract> contracts;
		Collection<BudgetHistory> budgetHistories;

		result = new Community();
		payments = new ArrayList<Payment>();
		incidences = new ArrayList<Incidence>();
		announcements = new ArrayList<Announcement>();
		blocks = new ArrayList<Block>();
		contracts = new ArrayList<Contract>();
		budgetHistories = new ArrayList<BudgetHistory>();

		result.setPayments(payments);
		result.setIncidences(incidences);
		result.setAnnouncements(announcements);
		result.setBlocks(blocks);
		result.setContracts(contracts);
		result.setBudgetHistories(budgetHistories);

		return result;
	}

	

	public Community findOne(Integer communityId) {
		Community result;

		result = communityRepository.findOne(communityId);

		Assert.notNull(result);

		return result;
	}

	public Collection<Community> findAll() {
		Collection<Community> result;

		result = communityRepository.findAll();

		return result;
	}

	public void save(Community community) {
		Assert.notNull(community, "error.null");
		
		communityRepository.save(community);
	}
	
	
	// Other business methods ------------------------------

	public void checkPrincipal(Community community) {
		Assert.notNull(community, "error.null");

		Administrator administrator;

		administrator = administratorService.findByPrincipal();

		Assert.notNull(administrator, "administrator.error.null");
	}
	
	public void calculateBudget(Invoice invoice){
		Assert.notNull(invoice);
		
		Double amount;
		Property property;
		Block block;
		Community community;
		Double budget;
		
		amount=invoice.getAmount();
		property=invoice.getProperty();
		block=property.getBlock();
		community=block.getCommunity();
		budget=community.getBudget();
		
		budget+=amount;
		
		budget = Math.round(budget*100)/100.0;
		
		community.setBudget(budget);
		
		this.save(community);
	}
	
	public void decreaseBudget(Payment payment){
		Assert.notNull(payment);
		
		Double amount;
		Community community;
		Double budget;
		
		amount=payment.getAmount();
		community=payment.getCommunity();
		budget=community.getBudget();
		
		budget-=amount;
		
		community.setBudget(budget);
		
		budget = Math.round(budget*100)/100.0;
		
		this.save(community);
	}
	
	public Double findBudgetByCommunity(Integer communityId){
		Double result;
		
		result=communityRepository.findBudgetByCommunity(communityId);
		
		return result;
	}
	
	public Collection<Community> findAllCommunitiesByAddress(String address){
		Collection<Community> result;
		
		result = communityRepository.findAllCommunitiesByAddress(address);
		
		return result;
	}

}
