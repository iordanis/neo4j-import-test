package com.nextmilestone.importer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.graphdb.Node;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;


public class SimpleImportingService {
	private static final Log log = LogFactory.getLog(SimpleImportingService.class);
	private static Neo4jTemplate neo4jTemplate;

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		GraphDatabase graphDatabase = context.getBean("remoteGraphDatabase", GraphDatabase.class);
		neo4jTemplate = new Neo4jTemplate(graphDatabase);
		neo4jTemplate.postConstruct();

		importABunch();
	}

	@Transactional
	private static void importABunch() {
		long startOfSave = now();
		for (int i = 0; i < 1000; i++) {
			createUniqueNode(i);
//			createNodeAndIndexEntry(i);
			log.info("Order & index created: " + i);
		}
		log.info("Orders saved after: " + (now() - startOfSave));
	}

	private static void createNodeAndIndexEntry(int i) {
		Node node = neo4jTemplate.createNode(org.neo4j.helpers.collection.MapUtil.map("id", String.valueOf(i)));
		neo4jTemplate.index("Order", node, "id", String.valueOf(i));
	}

	private static void createUniqueNode(int i) {
		neo4jTemplate.createUniqueNode(new Order(String.valueOf(i)));
	}

	private static long now() {
		return System.currentTimeMillis();
	}
}
