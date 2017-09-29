package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Payment;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

	@Query("select p from Payment p where p.community.id=?1")
	Collection<Payment> findAllPaymentByCommunity(int communityId);
	
	@Query("select p from Payment p where p.community.id=?1 and month(p.paymentMoment)=?2")
	Collection<Payment> findAllPaymentByCommunityAndMonth(int communityId , int month);
	
	@Query("select p from Payment p where p.community.id=?1 and YEAR(p.paymentMoment)=?2")
	Collection<Payment> findAllPaymentsByCommunityAndYear(int communityId , int year);
	
	@Query("select SUM(p.amount) from Payment p where p.community.id=?1 and YEAR(p.paymentMoment)=?2")
	Double findAllTotalAmountPaymentsByCommunityAndYear(int communityId , int year);
	
	@Query("select p from Payment p where p.community.id=?1 and year(p.paymentMoment)=year(current_date)")
	Collection<Payment> findAllPaymentsByCommunityCurrentYear(int communityId);
	
	@Query("select sum(p.amount) from Payment p where p.community.id=?1 and year(p.paymentMoment)=year(current_date)")
	Double findAllPaymentsAmountByCommunityCurrentYear(int communityId);
	
	@Query("select p from Payment p where p.community.id=?1 and p.paymentMoment between ?2 and ?3 order by p.paymentMoment asc")
	Collection<Payment> findAllPaymentsBetweenDates(int communityId, Date initialDate, Date endDate);
	
	@Query("select sum(p.amount) from Payment p where p.community.id=?1 and p.paymentMoment between ?2 and ?3")
	Double findPaymentAmountBetweenDates(int communityId, Date initialDate, Date endDate);
	
}

