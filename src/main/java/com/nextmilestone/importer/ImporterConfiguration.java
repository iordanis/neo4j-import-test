package com.nextmilestone.importer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

@Configuration
public class ImporterConfiguration {

	@Bean
	@Scope(value="singleton")
	public SpringRestGraphDatabase remoteGraphDatabase() {
		return new SpringRestGraphDatabase("http://localhost:7474/db/data/");
	}
}
