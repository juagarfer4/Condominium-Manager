package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Access(AccessType.PROPERTY)
@Table( indexes = {@Index (columnList = "mandateEnding")})
public class ChargeHistory extends DomainEntity{
	
	// Constructors ------------------------
	
	public ChargeHistory(){
		super();
	}
	
	// Attributes --------------------------
	
	private Date mandateBeginning;
	private Date mandateEnding;
	private Boolean isPresident;
	
	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMandateBeginning() {
		return mandateBeginning;
	}
	public void setMandateBeginning(Date mandateBeginning) {
		this.mandateBeginning = mandateBeginning;
	}
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMandateEnding() {
		return mandateEnding;
	}
	public void setMandateEnding(Date mandateEnding) {
		this.mandateEnding = mandateEnding;
	}
	
	@NotNull
	public Boolean getIsPresident() {
		return isPresident;
	}
	public void setIsPresident(Boolean isPresident) {
		this.isPresident = isPresident;
	}
	
	// Relationships ----------------------------
	
	private Owner owner;
	private Block block;

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
	@ManyToOne(optional=false)
	public Block getBlock() {
		return block;
	}
	public void setBlock(Block block) {
		this.block = block;
	}

}
