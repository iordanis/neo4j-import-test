package com.nextmilestone.neo4j.repository;

import org.neo4j.graphdb.Node;

import com.nextmilestone.model.Product;

public interface ProductRepository {

	void save(Product product);

	Node getPersistentState(Product product);
}
