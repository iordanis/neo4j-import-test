package com.nextmilestone.neo4j.repository;

import com.nextmilestone.model.Cart;

public interface CartRepository {

	void save(Cart cart);
}
