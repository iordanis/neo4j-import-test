package com.nextmilestone.importer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.DelegatingGraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;

public class SimpleImportingService {
	private static final Log log = LogFactory.getLog(SimpleImportingService.class);
	private static final int NUMBER_OF_ORDERS = 1000;
	private static final int NUMBER_OF_ITEMS_PER_ORDER = 10;
	private static final String CONTAINS = "CONTAINS";
	private static Neo4jTemplate neo4jTemplate;
	private static GraphDatabaseService graphDatabaseService;

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		graphDatabaseService = context.getBean("graphDatabaseService", GraphDatabaseService.class);
    	GraphDatabase graphDatabase = new DelegatingGraphDatabase(graphDatabaseService);
		neo4jTemplate = new Neo4jTemplate(graphDatabase);
		neo4jTemplate.postConstruct();

		importABunch();
	}

	private static void importABunch() {
		long startOfSave = now();
		importWithTransaction();
		log.info("Orders saved after: " + (now() - startOfSave));
	}

	private static void importWithTransaction() {
		Transaction transaction = graphDatabaseService.beginTx();
		try {
			importData();
			transaction.success();
		} finally {
			transaction.finish();
		}
	}

	private static void importData() {
		for (int i = 0; i < NUMBER_OF_ORDERS; i++) {
			Node orderNode = createOrderNode(new Order(String.valueOf(i)));
			List<Node> itemNodes = createItemNodes(generateItemsForOrder(NUMBER_OF_ITEMS_PER_ORDER));
			relateOrderToItems(orderNode, itemNodes);
		}
	}

	private static List<Item> generateItemsForOrder(int numberOfItemsPerOrder) {
		List<Item> items = new ArrayList<Item>(numberOfItemsPerOrder);
		for (int i = 0; i < numberOfItemsPerOrder; i++) {
			Item item = new Item(UUID.randomUUID().toString());
			items.add(item);
		}
		return items;
	}

	private static Node createOrderNode(Order order) {
		String orderId = order.getId();
		log.info("Importing order: " + orderId);
		return neo4jTemplate.getOrCreateNode("Order", "id", orderId, orderPropertyMap(order));
	}

	private static List<Node> createItemNodes(List<Item> items) {
		List<Node> itemNodes = new ArrayList<Node>();
		for (Item item : items) {
			Node itemNode = createItemNode(item);
			itemNodes.add(itemNode);
		}
		return itemNodes;
	}


	private static Node createItemNode(Item item) {
		String itemId = item.getId();
		log.info("Importing item");
		return neo4jTemplate.getOrCreateNode("Item", "id", itemId, itemPropertyMap(item));
	}

	private static void relateOrderToItems(Node orderNode, List<Node> itemNodes) {
		for (Node itemNode : itemNodes) {
			neo4jTemplate.createRelationshipBetween(orderNode, itemNode, CONTAINS, null);
		}
	}

	private static Map<String, Object> orderPropertyMap(Order order) {
		HashMap<String, Object> map = new HashMap<String, Object>(2);
		map.put("id", order.getId());
		map.put("__type__", "com.nextmilestone.importer.Order");
		return map;
	}

	private static Map<String, Object> itemPropertyMap(Item item) {
		HashMap<String, Object> map = new HashMap<String, Object>(2);
		map.put("id", item.getId());
		map.put("__type__", "com.nextmilestone.importer.Item");
		return map;
	}

	private static long now() {
		return System.currentTimeMillis();
	}
}
