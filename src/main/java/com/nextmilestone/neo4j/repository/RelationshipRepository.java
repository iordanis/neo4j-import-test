package com.nextmilestone.neo4j.repository;

import com.nextmilestone.model.Cart;
import com.nextmilestone.model.Product;

public interface RelationshipRepository {

	void contains(Cart cart, Product product);
}
