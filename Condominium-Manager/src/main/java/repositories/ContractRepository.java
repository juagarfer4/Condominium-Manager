package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer>{

	@Query("select c from Contract c where c.community.id=?1")
	Collection<Contract> findAllContractsByCommunity (int communityId);
	
	@Query("select c from Contract c where c.employee.userAccount.id=?1")
	Collection<Contract> findAllContractsByEmployee (int userAccountId);
	
}
