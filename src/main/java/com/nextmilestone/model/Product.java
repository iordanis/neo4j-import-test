package com.nextmilestone.model;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Product {

	@Indexed
	private final String id;

	public Product(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
