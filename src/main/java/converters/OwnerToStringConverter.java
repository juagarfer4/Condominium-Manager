package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Owner;

@Component
@Transactional
public class OwnerToStringConverter implements Converter<Owner, String>{
	
	@Override
	public String convert(Owner owner){
		String result;
		
		if(owner == null)
			result = null;
		
		else
			result = String.valueOf(owner.getId());
		
		return result;
	}

}
