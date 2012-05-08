package com.nextmilestone.neo4j.importer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nextmilestone.model.Cart;
import com.nextmilestone.model.Product;
import com.nextmilestone.neo4j.repository.CartRepository;
import com.nextmilestone.neo4j.repository.ProductRepository;
import com.nextmilestone.neo4j.repository.RelationshipRepository;

@Component
public class ImportService {

	private final CsvFileReader fileReader;
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final RelationshipRepository relationshipRepository;

	@Autowired
	public ImportService(CsvFileReader fileReader, CartRepository cartRepository,
			ProductRepository productRepository, RelationshipRepository relationshipRepository) {
		this.fileReader = fileReader;
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.relationshipRepository = relationshipRepository;
	}

	public void importData() {
		String[] values;
		int i = 0;

		while ((values = fileReader.attemptToReadNextValues()) != null) {
			System.out.println("imported: " + i++);
			Cart cart = new Cart(values[0]);
			Product product = new Product(values[1]);
			persist(cart, product);
		}
	}

	private void persist(Cart cart, Product product) {
		cartRepository.save(cart);
		productRepository.save(product);
		relationshipRepository.contains(cart, product);
	}
}
