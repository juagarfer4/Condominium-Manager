package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.mchange.util.AssertException;

import domain.Administrator;
import domain.Announcement;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AnnouncementServiceNegativeTest extends AbstractTest {

	// Service under test ------------------------------------------------

	@Autowired
	private AnnouncementService announcementService;

	// Tests --------------------------------------------------------------

	// List of announcements of a block: wrong size. ------------

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void listAnnouncementsBlockNegativeTest() {
		this.authenticate("owner1");

		Collection<Announcement> announcements;
		Integer size;
		announcements = announcementService.findAllAnnouncementByBlock(16);
		size = announcements.size();

		Assert.isTrue(size == 10);

		this.unauthenticate();
	}

	// List of announcements of a community: wrong size. ------------

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void listAnnouncementsCommunityNegativeTest() {
		this.authenticate("owner2");

		Collection<Announcement> announcements;
		Integer size;
		announcements = announcementService.findAllAnnouncementByCommunity(18);
		size = announcements.size();

		Assert.isTrue(size == 21);

		this.unauthenticate();
	}

	// Create an announcement for a community: no community. ------------

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void createAnnouncementCommunityNegativeTest() {
		this.authenticate("manager1");

		Announcement announcement;

		announcement = announcementService.createCommunity(57);
		announcement.setName("name");
		announcement.setDescription("description");
		
		announcementService.save(announcement);

		this.unauthenticate();
	}

	// Edit an announcement without being a manager. ------------

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void editAnnouncementCommunityNegativeTest() {
		this.authenticate("owner1");

		Announcement announcement;

		announcement = announcementService.findOne(25);
		announcement.setDescription("description");
		
		announcementService.save(announcement);

		this.unauthenticate();
	}

}
