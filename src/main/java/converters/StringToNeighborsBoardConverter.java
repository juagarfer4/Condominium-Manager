package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.NeighborsBoardRepository;

import domain.NeighborsBoard;

@Component
@Transactional
public class StringToNeighborsBoardConverter implements Converter<String, NeighborsBoard>{
	
	@Autowired
	NeighborsBoardRepository neighborsBoardRepository;
	
	@Override
	public NeighborsBoard convert(String text){
		NeighborsBoard result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text))
				result = null;
			else{
				id = Integer.valueOf(text);
				result = neighborsBoardRepository.findOne(id);
			}
		}catch (Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
