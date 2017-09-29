package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.IncidenceRepository;

import domain.Incidence;

@Component
@Transactional
public class StringToIncidenceConverter implements Converter<String, Incidence>{
	
	@Autowired
	IncidenceRepository incidenceRepository;
	
	@Override
	public Incidence convert(String text){
		Incidence result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text))
				result = null;
			else{
				id = Integer.valueOf(text);
				result = incidenceRepository.findOne(id);
			}
		}catch (Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
