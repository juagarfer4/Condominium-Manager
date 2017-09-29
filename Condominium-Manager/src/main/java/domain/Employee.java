package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@Entity
@Access(AccessType.PROPERTY)
public class Employee extends Actor{
	
	// Constructors --------------------------------
	
	public Employee (){
		super();
	}
	
	// Attributes --------------------------------
	
	// Relationships -------------------------------
	
	private Collection<Contract> contracts;

	@Valid
	@NotNull
	@OneToMany(mappedBy="employee")
	public Collection<Contract> getContracts() {
		return contracts;
	}
	public void setContracts(Collection<Contract> contracts) {
		this.contracts = contracts;
	}

}
