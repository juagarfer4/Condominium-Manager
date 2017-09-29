package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Manager;
import forms.ManagerForm;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
@Service
@Transactional
public class ManagerService {

	// Managed repository ---------------------------------

	@Autowired
	private ManagerRepository managerRepository;

	// Supporting services ---------------------------------

	// Constructors ----------------------------------------

	public ManagerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------
	
	public Manager create(){
		Manager result;
		UserAccount userAccount;
		Collection<Authority> authorities;
		Authority authority;
		
		result=new Manager();
		userAccount=new UserAccount();
		authorities=new ArrayList<Authority>();
		authority=new Authority();
		
		authority.setAuthority("MANAGER");
		
		authorities.add(authority);
		
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		
		return result;
	}
	
	public void save(Manager manager){
		Assert.notNull(manager, "error.null");
		
		this.encodePassword(manager);
		
		managerRepository.save(manager);
	}

	// Other business methods -------------------------------
	
	public Manager findByPrincipal() {
		Manager result;
		UserAccount userAccount;
		Integer userAccountId;

		userAccount = LoginService.getPrincipal();

		Assert.notNull(userAccount);

		userAccountId = userAccount.getId();

		result = managerRepository.findByUserAccountId(userAccountId);

		Assert.notNull(result);

		return result;
	}
	
	public void encodePassword(Manager Manager){
		UserAccount userAccount;
		String password;
		Md5PasswordEncoder encoder;
		
		encoder= new Md5PasswordEncoder();
		userAccount=Manager.getUserAccount();
		password=userAccount.getPassword();
		password=encoder.encodePassword(password, null);
		userAccount.setPassword(password);
	}

	public Manager reconstruct(ManagerForm managerForm) {
		Manager manager = this.create();
		
		Assert.isTrue(managerForm.isCondition(), "manager.error.condition");
		Assert.isTrue(managerForm.getPassword().equals(managerForm.getPasswordVerificada()), "manager.error.password");
		
		manager.setName(managerForm.getName());
		manager.setSurname(managerForm.getSurname());
		manager.setEmail(managerForm.getEmail());
		manager.setPhone(managerForm.getPhone());
		manager.getUserAccount().setPassword(managerForm.getPassword());
		manager.getUserAccount().setUsername(managerForm.getUsername());
		
		return manager;
	}

}
