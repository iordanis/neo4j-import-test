package com.nextmilestone.neo4j.configuration;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.data.neo4j.support.DelegatingGraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;

@Configuration
public class ImporterConfiguration {

	@Bean
	@Scope(value="singleton")
	public SpringRestGraphDatabase remoteGraphDatabase() {
		return new SpringRestGraphDatabase("http://localhost:7474/db/data/");
	}

	@Bean
	public Neo4jTemplate neo4jTemplate(GraphDatabaseService graphDatabaseService) {
    	GraphDatabase graphDatabase = new DelegatingGraphDatabase(graphDatabaseService);
    	Neo4jTemplate neo4jTemplate = new Neo4jTemplate(graphDatabase);
    	return neo4jTemplate;
	}

	@Bean
	public int operationsThreshold() {
		return 30000;
	}

	@Bean
	public String cartsfile() {
		return "csv/cart-to-products-100k.csv";
	}

}
