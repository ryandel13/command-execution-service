package net.mkengineering.studies.ces.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.mkengineering.studies.ces.persistence.CommandRepository;
import net.mkengineering.studies.ces.persistence.MockRepository;

@Configuration
public class CesConfig {

	@Bean
	@ConditionalOnMissingBean(CommandRepository.class)
	public CommandRepository myBeanForOthers() {
		System.out.println("LOADING FALLBACK BEAN");
		return new MockRepository();
	}
	
}
