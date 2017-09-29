package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Access(AccessType.PROPERTY)
@Table( indexes = {@Index (columnList = "number")})
public class Block extends DomainEntity{
	
	// Constructors --------------------------
	
	public Block(){
		super();
	}
	
	// Attributes ---------------------------
	
	private int number;
	private String address;
	private int numberOfFloors;
	private int numberOfDoors;
	private String code;
	
	@NotNull
	@Min(1)
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address = address;
	}
	
	@NotNull
	@Min(0)
	public int getNumberOfFloors() {
		return numberOfFloors;
	}
	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}
	
	@NotNull
	@Min(1)
	public int getNumberOfDoors() {
		return numberOfDoors;
	}
	public void setNumberOfDoors(int numberOfDoors) {
		this.numberOfDoors = numberOfDoors;
	}
	
	@Column(unique=true)
	@NotBlank
	public String getCode() {
		//code=community.getName()+"-"+this.getNumber();
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	// Relationships -----------------------------

	private Community community;
	private Collection<Property> properties;
	private Collection<Incidence> incidences;
	private Collection<Announcement> announcements;
	private Collection<NeighborsBoard> neighborsBoards;
	private Collection<ChargeHistory> chargeHistories;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Community getCommunity() {
		return community;
	}
	public void setCommunity(Community community) {
		this.community = community;
	}
	
	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy="block")
	public Collection<Property> getProperties() {
		return properties;
	}
	public void setProperties(Collection<Property> properties) {
		this.properties = properties;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="block")
	public Collection<Incidence> getIncidences() {
		return incidences;
	}
	public void setIncidences(Collection<Incidence> incidences) {
		this.incidences = incidences;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="block")
	public Collection<Announcement> getAnnouncements() {
		return announcements;
	}
	public void setAnnouncements(Collection<Announcement> announcements) {
		this.announcements = announcements;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="block")
	public Collection<NeighborsBoard> getNeighborsBoards() {
		return neighborsBoards;
	}
	public void setNeighborsBoards(Collection<NeighborsBoard> neighborsBoards) {
		this.neighborsBoards = neighborsBoards;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="block")
	public Collection<ChargeHistory> getChargeHistories() {
		return chargeHistories;
	}
	public void setChargeHistories(Collection<ChargeHistory> chargeHistories) {
		this.chargeHistories = chargeHistories;
	}
	
}
