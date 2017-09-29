package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Entity
@Access(AccessType.PROPERTY)
public class Property extends DomainEntity{
	
	// Constructors --------------------------------
	
	public Property(){
		super();
	}
	
	// Attributes --------------------------------
	
	private int floor;
	private String door;
	
	@NotNull
	@Min(0)
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	
	@NotNull
	public String getDoor() {
		return door;
	}
	public void setDoor(String door) {
		this.door = door;
	}
	
	// Relationships ---------------------------------
	
	private Owner owner;
	private Collection<Renter> renters;
	private Block block;
	private Collection<Invoice> invoices;

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy="property")
	public Collection<Renter> getRenters() {
		return renters;
	}
	public void setRenters(Collection<Renter> renters) {
		this.renters = renters;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Block getBlock() {
		return block;
	}
	public void setBlock(Block block) {
		this.block = block;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="property")
	public Collection<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(Collection<Invoice> invoices) {
		this.invoices = invoices;
	}

}
