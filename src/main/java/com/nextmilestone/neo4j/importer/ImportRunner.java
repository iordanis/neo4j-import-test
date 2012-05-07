package com.nextmilestone.neo4j.importer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ImportRunner {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ImportService importService = context.getBean(ImportService.class);
		importService.importData();
    	context.close();
	}
}
