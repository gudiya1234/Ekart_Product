package com.infy.ekart.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.ekart.product.dto.ProductDTO;
import com.infy.ekart.product.entity.Product;
import com.infy.ekart.product.exception.EKartProductException;
import com.infy.ekart.product.repository.ProductRepository;
import com.infy.ekart.product.service.CustomerProductService;
import com.infy.ekart.product.service.CustomerProductServiceImpl;

@SpringBootTest(classes = CustomerProductService.class)
public class CustomerProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private CustomerProductService customerProductService = new CustomerProductServiceImpl();

	@Test
	public void addProductValidTest() throws EKartProductException {

		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("Dzire");
		productDTO.setCategory("Electronic-Moblie");
		productDTO.setBrand("samgsung");
		productDTO.setDescription("samgsung mobile");
		productDTO.setPrice(12000.00);
		productDTO.setAvailableQuantity(250);

		Product product = new Product();
		product.setProductId(1);

		Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

		Assertions.assertDoesNotThrow(() -> customerProductService.addProduct(productDTO));
	}

	@Test
	public void addProductInValidTest() throws EKartProductException {
		Assertions.assertThrows(EKartProductException.class, () -> customerProductService.addProduct(null));
	}

	@Test
	public void getAllProductsValidTest() throws EKartProductException {
		List<Product> products = new ArrayList<Product>();
		Product product1 = new Product();
		product1.setProductId(1000);
		product1.setAvailableQuantity(150);
		product1.setBrand("motobot");
		product1.setCategory("Electronic-Moblie");
		product1.setPrice(16000.00);
		product1.setName("Bot E5s Plus");

		Product product2 = new Product();
		product2.setProductId(1001);
		product2.setAvailableQuantity(250);
		product2.setBrand("sangsung");
		product2.setName("Dzire");
		product2.setPrice(12000.00);
		product2.setCategory("Electronic-Moblie");

		products.add(product1);
		products.add(product2);

		Mockito.when(productRepository.findAll()).thenReturn(products);

		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		ProductDTO productDTO1 = new ProductDTO();
		productDTO1.setProductId(1000);

		ProductDTO productDTO2 = new ProductDTO();
		productDTO2.setProductId(1001);

		productDTOs.add(productDTO1);
		productDTOs.add(productDTO2);

		List<ProductDTO> list = customerProductService.getAllProducts();
		Assertions.assertEquals(productDTOs.get(0).getProductId(), list.get(0).getProductId());
		Assertions.assertEquals(productDTOs.get(1).getProductId(), list.get(1).getProductId());
	}

	@Test
	public void getAllProductsInValidTest() throws EKartProductException {
		List<Product> products = new ArrayList<Product>();

		Mockito.when(productRepository.findAll()).thenReturn(products);
		EKartProductException e = Assertions.assertThrows(EKartProductException.class,
				() -> customerProductService.getAllProducts());
		Assertions.assertEquals("ProductService.PRODUCT_NOT_AVAILABLE", e.getMessage());
	}

	@Test
	public void getProductByIdValidTest() throws EKartProductException {
		Product product = new Product();
		product.setProductId(1000);
		product.setName("Bot E5s Plus");
		product.setBrand("motobot");
		product.setCategory("Electronic-Moblie");
		product.setPrice(16000.00);
		product.setAvailableQuantity(150);

		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		Assertions.assertDoesNotThrow(() -> customerProductService.getProductById(product.getProductId()));
	}

	@Test
	public void getProductByIdInValidTest() throws EKartProductException {
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		EKartProductException e = Assertions.assertThrows(EKartProductException.class,
				() -> customerProductService.getProductById(1));
		Assertions.assertEquals("ProductService.PRODUCT_NOT_AVAILABLE", e.getMessage());
	}

	@Test
	public void reduceAvailableQuantityValidTest() throws EKartProductException {
		Product product = new Product();
		product.setProductId(1000);
		product.setAvailableQuantity(150);
		product.setBrand("motobot");
		product.setCategory("Electronic-Moblie");
		product.setPrice(16000.00);
		product.setName("Bot E5s Plus");

		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		Assertions.assertDoesNotThrow(() -> customerProductService.reduceAvailableQuantity(product.getProductId(), 50));
	}

	@Test
	public void reduceAvailableQuantityInvalidTest() throws EKartProductException {
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		EKartProductException e = Assertions.assertThrows(EKartProductException.class,
				() -> customerProductService.reduceAvailableQuantity(1, 12));
		Assertions.assertEquals("ProductService.PRODUCT_NOT_AVAILABLE", e.getMessage());
	}

	@Test
	public void deleteProductIdValidTest() throws EKartProductException {
		Product product = new Product();
		product.setProductId(1000);
		product.setName("Bot E5s Plus");
		product.setBrand("motobot");
		product.setCategory("Electronic-Moblie");
		product.setPrice(16000.00);
		product.setAvailableQuantity(150);

		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		Assertions.assertDoesNotThrow(() -> customerProductService.deleteProductById(product.getProductId()));
	}

	@Test
	public void deleteProductIdInValidTest() throws EKartProductException {
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		EKartProductException e = Assertions.assertThrows(EKartProductException.class,
				() -> customerProductService.deleteProductById(1));
		Assertions.assertEquals("ProductService.PRODUCT_NOT_AVAILABLE", e.getMessage());
	}
}