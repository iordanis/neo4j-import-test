package com.nextmilestone.importer;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Item {

	@Indexed(unique = true)
	private String id;

	public Item() {
	}

	public Item(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
