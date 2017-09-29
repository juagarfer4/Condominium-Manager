package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ChargeHistory;
import domain.Owner;

@Repository
public interface ChargeHistoryRepository extends JpaRepository<ChargeHistory, Integer>{
	
	@Query("select ch from ChargeHistory ch where ch.block.id=?1 and ch.mandateEnding=null")
	Collection<ChargeHistory> findAllActualChargesInBlock(int blockId);
	
	@Query("select ch.owner from ChargeHistory ch where ch.block.id=?1 and ch.mandateEnding=null")
	Collection<Owner> findAllActualOwnersInChargesInBlock(int blockId);
	
	@Query("select ch from ChargeHistory ch where ch.block.id=?1")
	Collection<ChargeHistory> findAllChargeHistoryByBlock(int blockId);
	
}
