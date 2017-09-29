package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ChargeHistory;

@Component
@Transactional
public class ChargeHistoryToStringConverter implements Converter<ChargeHistory, String> {

	@Override
	public String convert(ChargeHistory chargeHistory) {
		String result;

		if (chargeHistory == null)
			result = null;

		else
			result = String.valueOf(chargeHistory.getId());

		return result;
	}

}
