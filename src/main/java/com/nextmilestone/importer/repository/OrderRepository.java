package com.nextmilestone.importer.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.nextmilestone.importer.Order;

public interface OrderRepository extends GraphRepository<Order> {
}
