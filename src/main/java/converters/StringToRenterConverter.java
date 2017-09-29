package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RenterRepository;

import domain.Renter;

@Component
@Transactional
public class StringToRenterConverter implements Converter<String, Renter>{
	
	@Autowired
	RenterRepository renterRepository;
	
	@Override
	public Renter convert(String text){
		Renter result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text))
				result = null;
			else{
				id = Integer.valueOf(text);
				result = renterRepository.findOne(id);
			}
		}catch (Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
