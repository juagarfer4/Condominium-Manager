package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.OwnerRepository;

import domain.Owner;

@Component
@Transactional
public class StringToOwnerConverter implements Converter<String, Owner>{
	
	@Autowired
	OwnerRepository ownerRepository;
	
	@Override
	public Owner convert(String text){
		Owner result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text))
				result = null;
			else{
				id = Integer.valueOf(text);
				result = ownerRepository.findOne(id);
			}
		}catch (Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
