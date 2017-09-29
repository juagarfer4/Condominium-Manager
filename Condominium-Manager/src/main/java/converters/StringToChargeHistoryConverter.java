package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ChargeHistoryRepository;

import domain.ChargeHistory;

@Component
@Transactional
public class StringToChargeHistoryConverter implements Converter<String, ChargeHistory> {

	@Autowired
	ChargeHistoryRepository chargeHistoryRepository;

	@Override
	public ChargeHistory convert(String text) {
		ChargeHistory result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = chargeHistoryRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
