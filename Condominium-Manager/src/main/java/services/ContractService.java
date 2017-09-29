package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Community;
import domain.Employee;
import domain.Contract;
import domain.Manager;

import repositories.ContractRepository;
import security.UserAccount;
@Service
@Transactional
public class ContractService {

	// Managed repository ---------------------------------

	@Autowired
	private ContractRepository contractRepository;

	// Supporting services ---------------------------------

	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private EmployeeService employeeService;
	
	// Constructors ----------------------------------------

	public ContractService() {
		super();
	}

	// Simple CRUD methods ----------------------------------
	
	public Contract create(int communityId) {
		Contract result;
		Community community;

		result = new Contract();

		community = communityService.findOne(communityId);

		result.setCommunity(community);

		return result;
	}

	public Contract findOne(Integer contract) {
		Contract result;

		result = contractRepository.findOne(contract);

		Assert.notNull(result);

		return result;
	}

	public Collection<Contract> findAll() {
		Collection<Contract> result;

		result = contractRepository.findAll();

		return result;
	}
	
	public void save(Contract contract) {
		this.checkPrincipal(contract);
		
		Date arrivalDate;
		Date departureDate;
		
		arrivalDate=contract.getArrivalDate();
		departureDate=contract.getDepartureDate();
		
		if(departureDate!=null){
			Assert.isTrue(arrivalDate.before(departureDate), "contract.error.date");
		}
		
		contractRepository.save(contract);
	}

	// Other business methods -------------------------------
	
	public void checkPrincipal(Contract contract) {
		Assert.notNull(contract, "error.null");

		Manager manager;

		manager = managerService.findByPrincipal();

		Assert.notNull(manager, "manager.error.null");
	}

	public Collection<Contract> findAllContractByCommunity(
			int communityId) {
		Collection<Contract> result;

		result = contractRepository.findAllContractsByCommunity(communityId);

		return result;
	}

	public Collection<Contract> findAllContractsByEmployee() {
		Employee employee;
		
		employee=employeeService.findByPrincipal();
		
		Assert.notNull(employee);
		
		UserAccount userAccount;
		Integer userAccountId;
		Collection<Contract> result;
		
		userAccount=employee.getUserAccount();
		userAccountId=userAccount.getId();

		result = contractRepository.findAllContractsByEmployee(userAccountId);

		return result;
	}

}
