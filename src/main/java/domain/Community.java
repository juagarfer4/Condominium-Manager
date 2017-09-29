package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Transient;
@Entity
@Access(AccessType.PROPERTY)
public class Community extends DomainEntity{
	
	// Constructors ------------------------
	
	public Community(){
		super();
	}
	
	// Attributes -----------------------------
	
	private String name;
	private String address;
	private double budget;
	private String email;
	
	@NotBlank
	@Column(unique = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Transient
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
	
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	// Relationships --------------------------------
	
	private Collection<Payment> payments;
	private Collection<Incidence> incidences;
	private Collection<Announcement> announcements;
	private Collection<Block> blocks;
	private Collection<Contract> contracts;
	private Collection<BudgetHistory> budgetHistories;

	@Valid
	@NotNull
	@OneToMany(mappedBy="community")
	public Collection<Payment> getPayments() {
		return payments;
	}
	public void setPayments(Collection<Payment> payments) {
		this.payments = payments;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="community")
	public Collection<Incidence> getIncidences() {
		return incidences;
	}
	public void setIncidences(Collection<Incidence> incidences) {
		this.incidences = incidences;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="community")
	public Collection<Announcement> getAnnouncements() {
		return announcements;
	}
	public void setAnnouncements(Collection<Announcement> announcements) {
		this.announcements = announcements;
	}
	
	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy="community")
	public Collection<Block> getBlocks() {
		return blocks;
	}
	public void setBlocks(Collection<Block> blocks) {
		this.blocks = blocks;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="community")
	public Collection<Contract> getContracts() {
		return contracts;
	}
	public void setContracts(Collection<Contract> contracts) {
		this.contracts = contracts;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="community")
	public Collection<BudgetHistory> getBudgetHistories() {
		return budgetHistories;
	}
	public void setBudgetHistories(
			Collection<BudgetHistory> budgetHistories) {
		this.budgetHistories = budgetHistories;
	}

}
