package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Access(AccessType.PROPERTY)
public class NeighborsBoard extends DomainEntity{
	
	// Constructors ------------------------
	
	public NeighborsBoard(){
		super();
	}
	
	// Attributes ---------------------------
	
	private Date date;
	private String record;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Past
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@NotBlank
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	
	// Relationships ---------------------------------
	
	private Block block;
	private Collection<Owner> attendants;
	
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
	@ManyToMany
	public Collection<Owner> getAttendants() {
		return attendants;
	}
	public void setAttendants(Collection<Owner> attendants) {
		this.attendants = attendants;
	}

}
