package com.nextmilestone.importer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nextmilestone.importer.repository.OrderRepository;


public class SimpleImportingService {
	private static final Log log = LogFactory.getLog(SimpleImportingService.class);
	private static OrderRepository orderRepository;

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		orderRepository = context.getBean(OrderRepository.class);
		importABunch();
	}

	private static void importABunch() {
		List<Order> orders = new ArrayList<Order>();
		for (int i = 0; i < 1000; i++) {
			Order order = new Order(String.valueOf(i));
			log.info("Order created: " + i);
			orders.add(order);
		}
		long startOfSave = now();
		orderRepository.save(orders);
		log.info("Orders saved after: " + (now() - startOfSave));
	}

	private static long now() {
		return System.currentTimeMillis();
	}
}
