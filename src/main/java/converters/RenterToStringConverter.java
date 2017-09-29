package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Renter;

@Component
@Transactional
public class RenterToStringConverter implements Converter<Renter, String>{
	
	@Override
	public String convert(Renter renter){
		String result;
		
		if(renter == null)
			result = null;
		
		else
			result = String.valueOf(renter.getId());
		
		return result;
	}

}
