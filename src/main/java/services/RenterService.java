package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Property;
import domain.Renter;

import repositories.RenterRepository;
@Service
@Transactional
public class RenterService {

	// Managed repository ---------------------------------

	@Autowired
	private RenterRepository renterRepository;

	// Supporting services ---------------------------------
	
	@Autowired
	private PropertyService propertyService;
	
	// Constructors ----------------------------------------

	public RenterService() {
		super();
	}

	// Simple CRUD methods ----------------------------------
	
	public Renter create(Integer propertyId){
		Renter result;
		Property property;
		
		result=new Renter();
		property=propertyService.findOne(propertyId);
		
		result.setProperty(property);
		
		return result;
	}
	
	public Renter findOne(Integer renterId) {
		Renter result;

		result = renterRepository.findOne(renterId);

		Assert.notNull(result);

		return result;
	}
	
	public void save(Renter renter){
		Assert.notNull(renter, "error.null");
		
		Date arrivalDate;
		Date departureDate;
		
		arrivalDate=renter.getArrivalDate();
		departureDate=renter.getDepartureDate();
		
		if(departureDate!=null){
			Assert.isTrue(arrivalDate.before(departureDate), "renter.error.date");
		}
		
		renterRepository.save(renter);
	}

	// Other business methods -------------------------------
	
	public Collection<Renter> findAllRentersByProperty(Integer propertyId){
		Collection<Renter> result;
		
		result=renterRepository.findAllRentersByProperty(propertyId);
		
		return result;
	}

}
