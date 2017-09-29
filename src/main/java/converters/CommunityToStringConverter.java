package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Community;

@Component
@Transactional
public class CommunityToStringConverter implements Converter<Community, String>{
	
	@Override
	public String convert(Community community){
		String result;
		
		if(community == null)
			result = null;
		
		else
			result = String.valueOf(community.getId());
		
		return result;
	}

}
