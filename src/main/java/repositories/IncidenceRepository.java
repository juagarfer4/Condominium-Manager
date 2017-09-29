package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Incidence;

@Repository
public interface IncidenceRepository extends JpaRepository<Incidence, Integer>{

	@Query("select i from Incidence i where i.community.id=?1")
	Collection<Incidence> findAllIncidenceByCommunity(int communityId);
	
	@Query("select i from Incidence i where i.block.id=?1")
	Collection<Incidence> findAllIncidenceByBlock(int blockId);
}
