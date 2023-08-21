package com.sagor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.exception.ProductException;
import com.sagor.model.Product;
import com.sagor.request.CreateProductRequest;
import com.sagor.response.ApiResponse;
import com.sagor.service.ProductService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

	private final ProductService productService;

	public AdminProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) {
		Product product = productService.createProduct(null);
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}

	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException {
		productService.deleteProduct(productId);
		ApiResponse res = new ApiResponse();
		res.setMessage("Product deleted successfully");
		res.setStatus(true);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}

//	@GetMapping("/all")
//	public ResponseEntity<List<Product>> findAllProduct() {
//		List<Product> products = productService.findAllProduct();
//		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
//	}

	@PutMapping("/{productId}/update")
	public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product req)
			throws ProductException {
		Product product = productService.updateProduct(productId, req);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@PostMapping("/creates")
	public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req) {
		for (CreateProductRequest product : req) {
			productService.createProduct(product);
		}
		ApiResponse res = new ApiResponse();
		res.setMessage("Product created successfully");
		res.setStatus(true);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}
}
