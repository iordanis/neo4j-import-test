package com.nextmilestone.model;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Cart {

	@Indexed
	private final String id;

	public Cart(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
