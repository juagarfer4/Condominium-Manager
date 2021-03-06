package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PaymentRepository;

import domain.Payment;

@Component
@Transactional
public class StringToPaymentConverter implements Converter<String, Payment>{
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Override
	public Payment convert(String text){
		Payment result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text))
				result = null;
			else{
				id = Integer.valueOf(text);
				result = paymentRepository.findOne(id);
			}
		}catch (Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
