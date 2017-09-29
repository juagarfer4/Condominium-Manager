package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Renter;
@Repository
public interface RenterRepository extends JpaRepository<Renter, Integer>{
	
	@Query("select r from Renter r where r.property.id=?1")
	Collection<Renter> findAllRentersByProperty(int propertyId);

}
