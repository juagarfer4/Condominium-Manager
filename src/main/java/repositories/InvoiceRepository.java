package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Invoice;
import domain.Owner;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{
	
	@Query("select i from Invoice i where i.property.id=?1")
	Collection<Invoice> findAllInvoicesByProperty(int userAccountId);

	@Query("select i from Invoice i where i.property.block.community.id=?1 and YEAR(i.paymentMoment)=?2")
	Collection<Invoice> findAllInvoicesByCommunityAndYear(int communityId , int year);

	@Query("select SUM(i.amount) from Invoice i where i.property.block.community.id=?1 and YEAR(i.paymentMoment)=?2")
	Double findAllTotalAmountInvoicesByCommunityAndYear(int communityId , int year);
	
	@Query("select i from Invoice i where i.property.block.community.id=?1 and i.paid=true and year(i.paymentMoment)=year(current_date)")
	Collection<Invoice> findAllInvoicesByCommunityCurrentYear(int communityId);
	
	@Query("select sum(i.amount) from Invoice i where i.property.block.community.id=?1 and i.paid=true and year(i.paymentMoment)=year(current_date)")
	Double findAllInvoicesAmountByCommunityCurrentYear(int communityId);
	
	@Query("select i from Invoice i where i.property.block.community.id=?1 and i.paid=false order by i.creationMoment asc")
	Collection<Invoice> findAllUnpaidInvoicesBetweenDates(int communityId);
	
	@Query("select count(i) from Invoice i where i.property.block.community.id=?1 and i.paid=false order by i.creationMoment asc")
	Integer findNumberOfUnpaidInvoicesBetweenDates(int communityId);
	
	@Query("select distinct(i.property.owner) from Invoice i where i.property.block.community.id=?1 and i.paid=false order by i.creationMoment asc")
	Collection<Owner> findAllOwnersOfUnpaidInvoicesBetweenDates(int communityId);
	
	@Query("select sum(i.amount) from Invoice i where i.property.block.community.id=?1 and i.paid=false")
	Double findUnpaidAmountBetweenDates(int communityId);
	
	@Query("select i from Invoice i where i.property.block.community.id=?1 and i.paid=true and i.paymentMoment between ?2 and ?3 order by i.paymentMoment asc")
	Collection<Invoice> findAllPaidInvoicesBetweenDates(int communityId, Date initialDate, Date endDate);
	
	@Query("select count(i) from Invoice i where i.property.block.community.id=?1 and i.paid=true and i.paymentMoment between ?2 and ?3 order by i.paymentMoment asc")
	Integer findNumberOfPaidInvoicesBetweenDates(int communityId, Date initialDate, Date endDate);
	
	@Query("select distinct(i.property.owner) from Invoice i where i.property.block.community.id=?1 and i.paid=true and i.paymentMoment between ?2 and ?3 order by i.paymentMoment asc")
	Collection<Owner> findAllOwnersOfPaidInvoicesBetweenDates(int communityId, Date initialDate, Date endDate);
	
	@Query("select sum(i.amount) from Invoice i where i.property.block.community.id=?1 and i.paid=true and i.paymentMoment between ?2 and ?3")
	Double findPaidAmountBetweenDates(int communityId, Date initialDate, Date endDate);
	
}
