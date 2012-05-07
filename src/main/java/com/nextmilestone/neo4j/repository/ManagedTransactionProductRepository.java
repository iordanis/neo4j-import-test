package com.nextmilestone.neo4j.repository;

import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

import com.nextmilestone.model.Product;

@Component
public class ManagedTransactionProductRepository implements ProductRepository {

	private final ManagedTransaction transaction;
	private final Neo4jTemplate neo4jTemplate;

	@Autowired
	public ManagedTransactionProductRepository(ManagedTransaction transaction, Neo4jTemplate neo4jTemplate) {
		this.transaction = transaction;
		this.neo4jTemplate = neo4jTemplate;
	}

	@Override
	public void save(Product product) {
		transaction.prepareTransaction();
		neo4jTemplate.save(product);
	}

	@Override
	public Node getPersistentState(Product product) {
		return neo4jTemplate.getPersistentState(product);
	}

}