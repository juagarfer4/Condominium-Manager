package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CommunityRepository;

import domain.Community;

@Component
@Transactional
public class StringToCommunityConverter implements Converter<String, Community>{
	
	@Autowired
	CommunityRepository communityRepository;
	
	@Override
	public Community convert(String text){
		Community result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text))
				result = null;
			else{
				id = Integer.valueOf(text);
				result = communityRepository.findOne(id);
			}
		}catch (Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
