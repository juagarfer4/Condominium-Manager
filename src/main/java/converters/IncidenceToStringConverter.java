package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Incidence;

@Component
@Transactional
public class IncidenceToStringConverter implements Converter<Incidence, String>{
	
	@Override
	public String convert(Incidence incidence){
		String result;
		
		if(incidence == null)
			result = null;
		
		else
			result = String.valueOf(incidence.getId());
		
		return result;
	}

}
