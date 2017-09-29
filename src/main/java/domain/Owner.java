package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Access(AccessType.PROPERTY)
public class Owner extends Actor {
	
	// Constructors --------------------------------
	
	public Owner(){
		super();
	}
	
	// Attributes --------------------------------
	
	// Relationships -----------------------------
	
	Collection<Property> properties;
	Collection<ChargeHistory> chargeHistories;
	Collection<NeighborsBoard> neighborsBoards;
	Collection<Folder> folders;

	@Valid
	@NotNull
	@OneToMany(mappedBy="owner")
	public Collection<Property> getProperties() {
		return properties;
	}
	public void setProperties(Collection<Property> properties) {
		this.properties = properties;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="owner")
	public Collection<ChargeHistory> getChargeHistories() {
		return chargeHistories;
	}
	public void setChargeHistories(Collection<ChargeHistory> chargeHistories) {
		this.chargeHistories = chargeHistories;
	}
	
	@Valid
	@NotNull
	@ManyToMany(mappedBy="attendants")
	public Collection<NeighborsBoard> getNeighborsBoards() {
		return neighborsBoards;
	}
	public void setNeighborsBoards(Collection<NeighborsBoard> neighborsBoards) {
		this.neighborsBoards = neighborsBoards;
	}

	@Valid
	@NotNull
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	public Collection<Folder> getFolders() {
		return folders;
	}
	public void setFolders(Collection<Folder> folders) {
		this.folders = folders;
	}


}
