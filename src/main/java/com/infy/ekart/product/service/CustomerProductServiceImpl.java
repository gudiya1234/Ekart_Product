package com.infy.ekart.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.ekart.product.dto.ProductDTO;
import com.infy.ekart.product.entity.Product;
import com.infy.ekart.product.exception.EKartProductException;
import com.infy.ekart.product.repository.ProductRepository;

@Service(value = "custmorProductService")
public class CustomerProductServiceImpl implements CustomerProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<ProductDTO> getAllProducts() throws EKartProductException {

		List<Product> products = productRepository.findAll();

		if (products.isEmpty()) {
			throw new EKartProductException("ProductService.PRODUCT_NOT_AVAILABLE");
		}

		List<ProductDTO> productDTOs = new ArrayList<>();
		products.forEach(product -> {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(product.getName());
			productDTO.setCategory(product.getCategory());
			productDTO.setBrand(product.getBrand());
			productDTO.setDescription(product.getDescription());
			productDTO.setPrice(product.getPrice());
			productDTO.setProductId(product.getProductId());
			productDTO.setAvailableQuantity(product.getAvailableQuantity());

			productDTOs.add(productDTO);
		});

		return productDTOs;
	}

	@Override
	public ProductDTO getProductById(Integer productId) throws EKartProductException {

		Optional<Product> productOp = productRepository.findById(productId);
		Product product = productOp
				.orElseThrow(() -> new EKartProductException("ProductService.PRODUCT_NOT_AVAILABLE"));

		ProductDTO productDTO = new ProductDTO();
		productDTO.setName(product.getName());
		productDTO.setCategory(product.getCategory());
		productDTO.setBrand(product.getBrand());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
		productDTO.setProductId(product.getProductId());
		productDTO.setAvailableQuantity(product.getAvailableQuantity());

		return productDTO;
	}

	@Override
	public Integer addProduct(ProductDTO productDTO) throws EKartProductException {

		if (productDTO == null) {
			throw new EKartProductException("ProductService.PRODUCT_DETAILS_NOT_FOUND");
		}

		Product product = new Product();
		product.setName(productDTO.getName());
		product.setCategory(productDTO.getCategory());
		product.setBrand(productDTO.getBrand());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setAvailableQuantity(productDTO.getAvailableQuantity());

		product = productRepository.save(product);

		return product.getProductId();
	}

	@Override
	public void reduceAvailableQuantity(Integer productId, Integer quantity) throws EKartProductException {
		Optional<Product> productOp = productRepository.findById(productId);
		Product product = productOp
				.orElseThrow(() -> new EKartProductException("ProductService.PRODUCT_NOT_AVAILABLE"));
		product.setAvailableQuantity(product.getAvailableQuantity() - quantity);
		productRepository.save(product);
	}

	public void deleteProductById(Integer productId) throws EKartProductException {

		Optional<Product> productOp = productRepository.findById(productId);
		Product product = productOp
				.orElseThrow(() -> new EKartProductException("ProductService.PRODUCT_NOT_AVAILABLE"));

		productRepository.delete(product);
	}

}