package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Contract;

@Component
@Transactional
public class ContractToStringConverter implements Converter<Contract, String> {

	@Override
	public String convert(Contract contract) {
		String result;

		if (contract == null)
			result = null;

		else
			result = String.valueOf(contract.getId());

		return result;
	}

}
