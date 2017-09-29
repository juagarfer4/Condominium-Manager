package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.BudgetHistory;

@Component
@Transactional
public class BudgetHistoryToStringConverter implements Converter<BudgetHistory, String>{
	
	@Override
	public String convert(BudgetHistory budgetHistory){
		String result;
		
		if(budgetHistory == null)
			result = null;
		
		else
			result = String.valueOf(budgetHistory.getId());
		
		return result;
	}

}
