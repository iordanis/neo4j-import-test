package com.nextmilestone.neo4j.repository;

import org.neo4j.graphdb.Node;

import com.nextmilestone.model.Cart;

public interface CartRepository {

	void save(Cart cart);

	Node getPersistentState(Cart cart);
}
