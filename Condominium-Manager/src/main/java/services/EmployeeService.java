package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Employee;
import domain.Contract;
import forms.EmployeeForm;

import repositories.EmployeeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
@Service
@Transactional
public class EmployeeService {

	// Managed repository ---------------------------------

	@Autowired
	private EmployeeRepository employeeRepository;

	// Supporting services ---------------------------------

	// Constructors ----------------------------------------

	public EmployeeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------
	
	public Employee create(){
		Employee result;
		UserAccount userAccount;
		Collection<Authority> authorities;
		Authority authority;
		Collection<Contract> Contracts;
		
		result=new Employee();
		userAccount=new UserAccount();
		authorities=new ArrayList<Authority>();
		authority=new Authority();
		Contracts=new ArrayList<Contract>();
		
		authority.setAuthority("EMPLOYEE");
		
		authorities.add(authority);
		
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		result.setContracts(Contracts);
		
		return result;
	}
	
	public Employee findOne(Integer employeeId){
		Employee result;
		
		result = employeeRepository.findOne(employeeId);
		
		return result;
	}
	
	public Collection<Employee> findAll(){
		Collection<Employee> result;
		
		result=employeeRepository.findAll();
		
		return result;
	}
	
	public void save(Employee employee){
		Assert.notNull(employee, "error.null");
		
		this.encodePassword(employee);
		
		employeeRepository.save(employee);
	}

	// Other business methods -------------------------------
	
	public Employee findByPrincipal() {
		Employee result;
		UserAccount userAccount;
		Integer userAccountId;

		userAccount = LoginService.getPrincipal();

		Assert.notNull(userAccount);

		userAccountId = userAccount.getId();

		result = employeeRepository.findByUserAccountId(userAccountId);

		Assert.notNull(result);

		return result;
	}
	
	public void encodePassword(Employee employee){
		UserAccount userAccount;
		String password;
		Md5PasswordEncoder encoder;
		
		encoder= new Md5PasswordEncoder();
		userAccount=employee.getUserAccount();
		password=userAccount.getPassword();
		password=encoder.encodePassword(password, null);
		userAccount.setPassword(password);
	}

	public Employee reconstruct(EmployeeForm employeeForm) {
		Employee employee = this.create();
		
		Assert.isTrue(employeeForm.isCondition(), "employee.condition.error");
		Assert.isTrue(employeeForm.getPassword().equals(employeeForm.getPasswordVerificada()), "employee.password.error");
		
		employee.setName(employeeForm.getName());
		employee.setSurname(employeeForm.getSurname());
		employee.setEmail(employeeForm.getEmail());
		employee.setPhone(employeeForm.getPhone());
		employee.getUserAccount().setPassword(employeeForm.getPassword());
		employee.getUserAccount().setUsername(employeeForm.getUsername());
		
		return employee;
	}

}
