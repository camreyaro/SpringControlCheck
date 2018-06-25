
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.TremiteRepository;
import domain.Tremite;

@Component
@Transactional
public class StringToTremiteConverter implements Converter<String, Tremite> {

	@Autowired
	TremiteRepository	tremiteRepository;


	@Override
	public Tremite convert(final String text) {
		Tremite result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.tremiteRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
