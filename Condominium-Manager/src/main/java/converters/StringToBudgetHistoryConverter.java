package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.BudgetHistoryRepository;

import domain.BudgetHistory;

@Component
@Transactional
public class StringToBudgetHistoryConverter implements Converter<String, BudgetHistory>{
	
	@Autowired
	BudgetHistoryRepository budgetHistoryRepository;
	
	@Override
	public BudgetHistory convert(String text){
		BudgetHistory result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text))
				result = null;
			else{
				id = Integer.valueOf(text);
				result = budgetHistoryRepository.findOne(id);
			}
		}catch (Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
