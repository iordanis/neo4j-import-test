package com.nextmilestone.importer;

import static org.neo4j.helpers.collection.MapUtil.map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;


public class SimpleImportingService {
	private static final Log log = LogFactory.getLog(SimpleImportingService.class);
	private static Neo4jTemplate neo4jTemplate;

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		GraphDatabaseService graphDatabaseService = context.getBean("graphDatabaseService", GraphDatabaseService.class);
//		DelegatingGraphDatabase graphDatabase = new DelegatingGraphDatabase(graphDatabaseService);
		GraphDatabase graphDatabase = context.getBean("graphDatabase", GraphDatabase.class);
		neo4jTemplate = new Neo4jTemplate(graphDatabase);
		neo4jTemplate.postConstruct();

		importABunch();
	}

	@Transactional
	private static void importABunch() {
		long startOfSave = now();
		for (int i = 0; i < 1000; i++) {
			neo4jTemplate.createNode(map("id", String.valueOf(i)));
//			neo4jTemplate.save(new Order(String.valueOf(i)));
			log.info("Order created: " + i);
		}
		log.info("Orders saved after: " + (now() - startOfSave));
	}

	private static long now() {
		return System.currentTimeMillis();
	}
}
