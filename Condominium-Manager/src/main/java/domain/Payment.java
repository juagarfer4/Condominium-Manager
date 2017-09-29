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
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Access(AccessType.PROPERTY)
@Table( indexes = {@Index (columnList = "paymentMoment")})
public class Payment extends DomainEntity{
	
	// Constructors -----------------------
	
	public Payment(){
		super();
	}
	
	// Attributes ------------------------
	
	private Date paymentMoment;
	private double amount;
	private String description;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Past
	public Date getPaymentMoment() {
		return paymentMoment;
	}
	public void setPaymentMoment(Date paymentMoment) {
		this.paymentMoment = paymentMoment;
	}
	
	@Min(1)
	@Digits(integer = 9, fraction = 2)
	@NotNull
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	// Relationships -----------------------------
	
	private Community community;

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Community getCommunity() {
		return community;
	}
	public void setCommunity(Community community) {
		this.community = community;
	}

}
