package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.NeighborsBoard;
@Repository
public interface NeighborsBoardRepository extends JpaRepository<NeighborsBoard, Integer>{

	@Query("select nb from NeighborsBoard nb where nb.block.id=?1")
	Collection<NeighborsBoard> findAllNeighborsBoardByBlock(int blockId);
	
}
