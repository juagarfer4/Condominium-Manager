package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BudgetHistory;

@Repository
public interface BudgetHistoryRepository extends JpaRepository<BudgetHistory, Integer>{

	
	@Query("select bh from BudgetHistory bh where bh.community.id=?1")
	Collection<BudgetHistory> findAllBudgetHistoryByCommunity(int communityId);
	
	@Query("select bh from BudgetHistory bh where bh.community.id=?1 AND bh.position=(select MAX(bh2.position) from BudgetHistory bh2 where bh2.community.id=?1)")
	BudgetHistory findLastBudgetHistoryByCommunity(int communityId);
	
	@Query("select bh from BudgetHistory bh where bh.community.id=?1 AND bh.position=(select MAX(bh2.position)-1 from BudgetHistory bh2 where bh2.community.id=?1)")
	BudgetHistory findLastBudgetHistoryByCommunity2(int communityId);
	
	@Query("select COUNT(bh.position) from BudgetHistory bh where bh.community.id=?1 AND bh.position=?2")
	Integer anyBudgetHistoryByCommunityAndPosition(int communityId, int position);

}
