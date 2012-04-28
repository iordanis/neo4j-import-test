package com.nextmilestone.importer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SimpleImportingService {
	private static final Log log = LogFactory.getLog(SimpleImportingService.class);
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("applicationContext.xml");
		importABunch();
	}

	private static void importABunch() {
		for (int i = 0; i < 1000; i++) {
			Order order = new Order(String.valueOf(i));
			order.persist();
			log.info("Order imported: " + i);
		}
	}
}
