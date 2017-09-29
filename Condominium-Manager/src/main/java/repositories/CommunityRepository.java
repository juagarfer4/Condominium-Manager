package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer>{
	
	@Query("select c.budget from Community c where c.id=?1")
	Double findBudgetByCommunity(int communityId);
	
	@Query("select c from Community c where c.address like concat('%', ?1, '%')")
	Collection<Community> findAllCommunitiesByAddress(String address);

}
