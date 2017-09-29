package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer>{

	@Query("select o from Owner o where o.userAccount.id=?1")
	Owner findByUserAccountId(int userAccountId);
	
	@Query("select p.owner from Property p where p.block.id=?1")
	Collection<Owner> findAllOwnersByBlock(int blockId);
	
	@Query("select nb.attendants from NeighborsBoard nb where nb.id=?1")
	Collection<Owner> findAllAttendantsByNeighborsBoard(int neighborsBoardId);
	
	@Query("select distinct(p.owner) from Property p where p.block.community.id=?1")
	Collection<Owner> findAllRecipientesByOwner(int communityId);

}
