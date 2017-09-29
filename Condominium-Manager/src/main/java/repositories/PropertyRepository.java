package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer>{

	@Query("select p from Property p where p.block.id=?1")
	Collection<Property> findAllPropertyByBlock(int blockId);
	
	@Query("select p from Property p where p.owner.userAccount.id=?1")
	Collection<Property> findAllPropertiesByOwner(int userAccountId);
	
	@Query("select count(p) from Property p where p.block.id=?1 and p.floor=?2 and p.door=?3")
	Double findAllPropertyByBlockWithSameParameters (int blockId , int floor, String door);
	
}
