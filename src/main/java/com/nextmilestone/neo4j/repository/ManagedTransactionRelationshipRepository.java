package com.nextmilestone.neo4j.repository;

import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

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
	public void relateNodes(Node startNode, Node endNode, String relationship) {
		transaction.prepareTransaction();
		neo4jTemplate.createRelationshipBetween(startNode, endNode, relationship, null);
	}
}
