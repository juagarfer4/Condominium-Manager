package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer>{
	
	@Query("select b from Block b where b.community.id=?1")
	Collection<Block> findAllBlocksByCommunity(int communityId);
	
	@Query("select count(b) from Block b where b.community.id=?1 and b.number=?2")
	Double findNumberOfBlockSameNumberInCommunity(int communityId , int numberOfBlock);
	
	@Query("select count(p) from Property p where p.block.id=?1 and p.floor=?2")
	Integer findAllPropetiesByFloor(int blockId, int floor);

}
