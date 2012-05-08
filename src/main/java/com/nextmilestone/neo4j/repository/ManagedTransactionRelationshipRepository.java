package com.nextmilestone.neo4j.repository;

import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

import com.nextmilestone.model.Cart;
import com.nextmilestone.model.Product;

@Component
public class ManagedTransactionRelationshipRepository implements RelationshipRepository {
	private final ManagedTransaction transaction;
	private final Neo4jTemplate neo4jTemplate;

	@Autowired
	public ManagedTransactionRelationshipRepository(ManagedTransaction transaction, Neo4jTemplate neo4jTemplate) {
		this.transaction = transaction;
		this.neo4jTemplate = neo4jTemplate;
	}

	@Override
	public void relate(Cart cart, Product product, String relationship) {
		Node cartNode = neo4jTemplate.getPersistentState(cart);
		Node productNode = neo4jTemplate.getPersistentState(product);

		transaction.prepareTransaction();
		neo4jTemplate.createRelationshipBetween(cartNode, productNode, relationship, null);
	}
}
