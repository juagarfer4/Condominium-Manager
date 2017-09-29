package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer>{

	@Query("select a from Announcement a where a.community.id=?1")
	Collection<Announcement> findAllAnnouncementByCommunity(int communityId);
	
	@Query("select a from Announcement a where a.block.id=?1")
	Collection<Announcement> findAllAnnouncementByBlock(int blockId);
	
}
