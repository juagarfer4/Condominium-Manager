package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Block;

@Component
@Transactional
public class BlockToStringConverter implements Converter<Block, String>{
	
	@Override
	public String convert(Block block){
		String result;
		
		if(block == null)
			result = null;
		
		else
			result = String.valueOf(block.getId());
		
		return result;
	}

}
