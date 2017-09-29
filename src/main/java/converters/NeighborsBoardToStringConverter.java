package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.NeighborsBoard;

@Component
@Transactional
public class NeighborsBoardToStringConverter implements Converter<NeighborsBoard, String>{
	
	@Override
	public String convert(NeighborsBoard neighborsBoard){
		String result;
		
		if(neighborsBoard == null)
			result = null;
		
		else
			result = String.valueOf(neighborsBoard.getId());
		
		return result;
	}

}
