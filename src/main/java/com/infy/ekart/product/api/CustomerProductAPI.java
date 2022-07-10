package com.infy.ekart.product.api;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.ekart.product.dto.ProductDTO;
import com.infy.ekart.product.exception.EKartProductException;
import com.infy.ekart.product.service.CustomerProductService;

@RestController
@CrossOrigin
@Validated
@RequestMapping(value = "/product-api")
public class CustomerProductAPI {

	@Autowired
	private CustomerProductService customerProductService;

	@Autowired
	private Environment environment;

	Log logger = LogFactory.getLog(CustomerProductAPI.class);

	@GetMapping(value = "/viewProducts")
	public ResponseEntity<List<ProductDTO>> getAllProducts() throws EKartProductException {
		List<ProductDTO> products = customerProductService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping(value = "/getProduct/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer productId) throws EKartProductException {
		ProductDTO product = customerProductService.getProductById(productId);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PostMapping(value = "/product")
	public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) throws EKartProductException {
		Integer id = customerProductService.addProduct(productDTO);
		String message = environment.getProperty("ProductAPI.PRODUCT_ADDED");
		return new ResponseEntity<>(message + id, HttpStatus.CREATED);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<String> reduceAvailableQuantity(@RequestBody ProductDTO productDTO)
			throws EKartProductException {
		customerProductService.reduceAvailableQuantity(productDTO.getProductId(), productDTO.getQuantity());
		String message = environment.getProperty("ProductAPI.REDUCE_QUANTITY_SUCCESSFULL");
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/delProduct/{productId}")
	public ResponseEntity<String> deleteProductById(@PathVariable Integer productId) throws EKartProductException {
		customerProductService.deleteProductById(productId);
		String message = environment.getProperty("ProductAPI.PRODUCT_DELETED");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

}
