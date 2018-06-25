
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tremite;

@Component
@Transactional
public class TremiteToStringConverter implements Converter<Tremite, String> {

	@Override
	public String convert(final Tremite tremite) {
		String result;

		if (tremite == null)
			result = null;
		else
			result = String.valueOf(tremite.getId());

		return result;
	}

}
