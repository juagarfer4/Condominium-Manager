package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EmployeeRepository;

import domain.Employee;

@Component
@Transactional
public class StringToEmployeeConverter implements Converter<String, Employee>{
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public Employee convert(String text){
		Employee result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text))
				result = null;
			else{
				id = Integer.valueOf(text);
				result = employeeRepository.findOne(id);
			}
		}catch (Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
