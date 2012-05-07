package com.nextmilestone.neo4j.importer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nextmilestone.model.Cart;
import com.nextmilestone.model.Product;
import com.nextmilestone.neo4j.repository.CartRepository;
import com.nextmilestone.neo4j.repository.ProductRepository;
import com.nextmilestone.neo4j.repository.RelationshipRepository;

@Component
public class ImportService {
	private static final String CONTAINS = "CONTAINS";

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
		Cart cart = new Cart("");
		List<Product> products = new ArrayList<Product>(100);
		int i = 0;

		while ((values = fileReader.attemptToReadNextValues()) != null) {
			System.out.println("imported: " + i++);
			String cartId = values[0];
			String productId = values[1];

			if (!cartId.equals(cart.getId())) {
				if (StringUtils.isNotBlank(cart.getId())) {
					persist(cart, products);
					relate(cart, products);
				}
				cart = new Cart(cartId);
				products.clear();
			}
			Product product = new Product(productId);
			products.add(product);
		}
	}

	private void persist(Cart cart, List<Product> products) {
		cartRepository.save(cart);
		for (Product product : products) {
			productRepository.save(product);
		}
	}

	private void relate(Cart cart, List<Product> products) {
		Node cartNode = cartRepository.getPersistentState(cart);
		for (Product product : products) {
			Node productNode = productRepository.getPersistentState(product);
			relationshipRepository.relateNodes(cartNode, productNode, CONTAINS);
		}
	}
}
