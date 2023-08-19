package com.sagor.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sagor.exception.ProductException;
import com.sagor.model.Product;
import com.sagor.request.CreateProductRequest;

public interface ProductService {

	public Product createProduct(CreateProductRequest req);

	public String deleteProduct(Long productId) throws ProductException;

	public Product updateProduct(Long productId, Product req) throws ProductException;

	public Product findProductById(Long id) throws ProductException;

	public List<Product> findProductByCategory(String category) throws ProductException;

	public Page<Product> getAllProduct(String category, List<String> color, List<String> size, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

}
