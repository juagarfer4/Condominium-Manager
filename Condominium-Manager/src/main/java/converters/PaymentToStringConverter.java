package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Payment;

@Component
@Transactional
public class PaymentToStringConverter implements Converter<Payment, String>{
	
	@Override
	public String convert(Payment payment){
		String result;
		
		if(payment == null)
			result = null;
		
		else
			result = String.valueOf(payment.getId());
		
		return result;
	}

}
