package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Announcement;
import domain.Block;
import domain.Community;
import domain.Manager;

import repositories.AnnouncementRepository;

@Service
@Transactional
public class AnnouncementService {

	// Managed repository ---------------------------------

	@Autowired
	private AnnouncementRepository announcementRepository;

	// Supporting services ------------------------S---------

	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private BlockService blockService;
	
	@Autowired
	private CommunityService communityService;
	
	// Constructors ----------------------------------------

	public AnnouncementService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public Announcement createBlock(int blockId) {
		Announcement result;
		Block block;
		Date creationMoment;

		creationMoment = new Date(System.currentTimeMillis() - 1);
		result = new Announcement();

		block = blockService.findOne(blockId);

		result.setBlock(block);
		result.setCreationMoment(creationMoment);

		return result;
	}

	public Announcement createCommunity(int communityId) {
		Announcement result;
		Community community;
		Date creationMoment;

		creationMoment = new Date(System.currentTimeMillis() - 1);
		result = new Announcement();

		community = communityService.findOne(communityId);

		result.setCommunity(community);
		result.setCreationMoment(creationMoment);

		return result;
	}

	public Announcement findOne(Integer announcementId) {
		Announcement result;

		result = announcementRepository.findOne(announcementId);

		Assert.notNull(result, "error.null");

		return result;
	}

	public Collection<Announcement> findAll() {
		Collection<Announcement> result;

		result = announcementRepository.findAll();

		return result;
	}

	public void save(Announcement announcement) {
		if(announcement.getCommunity()==null){
			Assert.notNull(announcement.getBlock(), "announcement.error.null");
		}
		if(announcement.getBlock()==null){
			Assert.notNull(announcement.getCommunity(), "announcement.error.null");
		}
		
		this.checkPrincipal(announcement);
		
		Date creationMoment;

		creationMoment = new Date(System.currentTimeMillis() - 1);

		announcement.setCreationMoment(creationMoment);
		
		announcementRepository.save(announcement);
	}
	
	
	
	// Other business methods ------------------------------

	public void checkPrincipal(Announcement announcement) {
		Assert.notNull(announcement, "error.null");

		Manager manager;

		manager = managerService.findByPrincipal();

		Assert.notNull(manager, "manager.error.null");
	}

	public Collection<Announcement> findAllAnnouncementByCommunity(
			int communityId) {
		Collection<Announcement> result;

		result = announcementRepository.findAllAnnouncementByCommunity(communityId);

		return result;
	}

	public Collection<Announcement> findAllAnnouncementByBlock(
			int blockId) {
		Collection<Announcement> result;

		result = announcementRepository.findAllAnnouncementByBlock(blockId);

		return result;
	}

}
