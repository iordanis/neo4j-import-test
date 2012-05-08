package com.nextmilestone.neo4j.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

import com.nextmilestone.model.Cart;

@Component
public class ManagedTransactionCartRepository implements CartRepository {

	private final ManagedTransaction transaction;
	private final Neo4jTemplate neo4jTemplate;

	@Autowired
	public ManagedTransactionCartRepository(ManagedTransaction transaction, Neo4jTemplate neo4jTemplate) {
		this.transaction = transaction;
		this.neo4jTemplate = neo4jTemplate;
	}

	@Override
	public void save(Cart cart) {
		transaction.prepareTransaction();
		neo4jTemplate.save(cart);
	}
}
