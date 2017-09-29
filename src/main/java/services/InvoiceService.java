package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Invoice;
import domain.Manager;
import domain.Owner;
import domain.Property;

import repositories.InvoiceRepository;

@Service
@Transactional
public class InvoiceService {
	
	// Managed repository ---------------------------------

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	// Supporting services -------------------------------
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private CommunityService communityService;
	
	// Constructors -------------------------------------
	
	public InvoiceService(){
		super();
	}
	
	// Simple CRUD methods -------------------------------
	
	public Invoice create(Integer propertyId){
		Property property;
		
		property=propertyService.findOne(propertyId);
		
		Assert.notNull(property, "error.null");
		
		Invoice result;
		Date creationMoment;
		Boolean paid;
		
		result=new Invoice();
		creationMoment=new Date(System.currentTimeMillis()-1);
		paid=new Boolean(false);
		
		result.setCreationMoment(creationMoment);
		result.setPaid(paid);
		result.setProperty(property);
		
		return result;
	}
	
	public Invoice findOne(Integer invoiceId){
		Invoice result;
		
		result=invoiceRepository.findOne(invoiceId);
				
		return result;
	}
	
	public void saveManager(Invoice invoice){
		this.checkPrincipalManager(invoice);
		
		Date creationMoment;
		
		creationMoment=new Date(System.currentTimeMillis()-1);
		
		invoice.setCreationMoment(creationMoment);
		
		Date paymentMoment;
		
		paymentMoment=invoice.getPaymentMoment();
		
		if(paymentMoment!=null){
			Assert.isTrue(creationMoment.before(paymentMoment), "invoice.error.date");
		}
		
		invoiceRepository.save(invoice);
	}
	
	public void saveOwner(Invoice invoice){
		this.checkPrincipalOwner(invoice);
		
		Date creationMoment;
		Date paymentMoment;
		
		creationMoment=invoice.getCreationMoment();
		paymentMoment=invoice.getPaymentMoment();
		
		if(paymentMoment!=null){
			Assert.isTrue(creationMoment.before(paymentMoment), "invoice.error.date");
		}
		
		invoiceRepository.save(invoice);
		
		communityService.calculateBudget(invoice);
	}
	
	public void save(Invoice invoice){
		Assert.notNull(invoice);
		
		invoiceRepository.save(invoice);
	}
	
	// Other business methods ----------------------------
	
	public void checkPrincipalManager(Invoice invoice){
		Assert.notNull(invoice);
		
		Manager manager;
		
		manager=managerService.findByPrincipal();
		
		Assert.notNull(manager);
	}
	
	public void checkPrincipalOwner(Invoice invoice){
		Assert.notNull(invoice);
		
		Property property;
		Owner owner;
		Owner principal;
		
		property=invoice.getProperty();
		owner=property.getOwner();
		principal=ownerService.findByPrincipal();
		
		Assert.isTrue(owner==principal, "invoice.error.owner");
	}
	
	public Collection<Invoice> findAllInvoicesByProperty(Integer propertyId){
		Owner principal;
		
		principal=ownerService.findByPrincipal();
		
		Assert.notNull(principal);
		
		Property property;
		Owner owner;
		
		property=propertyService.findOne(propertyId);
		owner=property.getOwner();
		
		Assert.isTrue(owner==principal);
		
		Collection<Invoice> result;
		
		result=invoiceRepository.findAllInvoicesByProperty(propertyId);
		
		return result;
	}
	
	public void payInvoice(Invoice invoice){
		this.checkPrincipalOwner(invoice);
		
		Boolean paid;
		
		paid=invoice.getPaid();
		
		Assert.isTrue(paid.equals(false), "invoice.error.paid");
		
		Date paymentMoment;
		
		paymentMoment=new Date(System.currentTimeMillis()-1);
		
		invoice.setPaymentMoment(paymentMoment);
		invoice.setPaid(true);
		
		this.saveOwner(invoice);
	}

	public Collection<Invoice> findAllInvoiceByCommunityAndYear(int communityId , int year) {
		Collection<Invoice> result;

		result = invoiceRepository.findAllInvoicesByCommunityAndYear(communityId ,year);

		return result;
	}
	
	public Double findAllTotalAmountInvoiceByCommunityAndYear(int communityId , int year) {
		Double result;

		result = invoiceRepository.findAllTotalAmountInvoicesByCommunityAndYear(communityId , year);
		
		if(result == null){
			result = 0.0;
		}

		return result;
	}
	
	public Collection<Invoice> findAllInvoicestByCommunityCurrentYear(int communityId){
		Collection<Invoice> result;
		
		result=invoiceRepository.findAllInvoicesByCommunityCurrentYear(communityId);
		
		return result;
	}
	
	public Double findAllInvoicesAmountByCommunityCurrentYear(int communityId){
		Double result;
		
		result=invoiceRepository.findAllInvoicesAmountByCommunityCurrentYear(communityId);
		
		return result;
	}
	
	public Collection<Invoice> findAllUnpaidInvoicesBetweenDates(Integer communityId){
		Collection<Invoice> result;
		
		result=invoiceRepository.findAllUnpaidInvoicesBetweenDates(communityId);
		
		return result;
	}
	
	public Integer findNumberOfUnpaidInvoicesBetweenDates(Integer communityId){
		Integer result;
		
		result=invoiceRepository.findNumberOfUnpaidInvoicesBetweenDates(communityId);
		
		return result;
	}
	
	public Collection<Owner> findAllOwnersOfUnpaidInvoicesBetweenDates(Integer communityId){
		Collection<Owner> result;
		
		result=invoiceRepository.findAllOwnersOfUnpaidInvoicesBetweenDates(communityId);
		
		return result;
	}
	
	public Double findUnpaidAmountBetweenDates(Integer communityId){
		Double result;
		
		result=invoiceRepository.findUnpaidAmountBetweenDates(communityId);
		
		return result;
	}
	
	public Collection<Invoice> findAllPaidInvoicesBetweenDates(Integer communityId, Date initialDate, Date endDate){
		Collection<Invoice> result;
		
		result=invoiceRepository.findAllPaidInvoicesBetweenDates(communityId, initialDate, endDate);
		
		return result;
	}
	
	public Integer findNumberOfPaidInvoicesBetweenDates(Integer communityId, Date initialDate, Date endDate){
		Integer result;
		
		result=invoiceRepository.findNumberOfPaidInvoicesBetweenDates(communityId, initialDate, endDate);
		
		return result;
	}
	
	public Collection<Owner> findAllOwnersOfPaidInvoicesBetweenDates(Integer communityId, Date initialDate, Date endDate){
		Collection<Owner> result;
		
		result=invoiceRepository.findAllOwnersOfPaidInvoicesBetweenDates(communityId, initialDate, endDate);
		
		return result;
	}
	
	public Double findPaidAmountBetweenDates(Integer communityId, Date initialDate, Date endDate){
		Double result;
		
		result=invoiceRepository.findPaidAmountBetweenDates(communityId, initialDate, endDate);
		
		return result;
	}
	
}
