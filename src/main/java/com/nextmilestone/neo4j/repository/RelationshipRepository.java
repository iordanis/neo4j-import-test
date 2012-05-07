package com.nextmilestone.neo4j.repository;

import org.neo4j.graphdb.Node;

public interface RelationshipRepository {

	void relateNodes(Node startNode, Node endNode, String relationship);
}
