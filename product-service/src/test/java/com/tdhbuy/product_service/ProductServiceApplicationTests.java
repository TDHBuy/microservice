package com.tdhbuy.product_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdhbuy.product_service.dto.ProductRequest;
import com.tdhbuy.product_service.dto.ProductResponse;
import com.tdhbuy.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProductServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");
	@Autowired
	private ProductRepository productRepository;
	@DynamicPropertySource
	static void mongoDBProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
		registry.add("server.port", mongoDBContainer::getFirstMappedPort);
	}
	@Test
	void createProductSuccess() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/created")
					.contentType(MediaType.APPLICATION_JSON)
					.content(productRequestString))
				.andExpect(status().isCreated())
				.andReturn();
	}

	ProductRequest getProductRequest(){
		return ProductRequest.builder()
				.name("Shin - cậu bé bút chì")
				.description("Câu chuyện về một cấu bé nhí nhảnh, vui nhộn sống trong gia đình Nohara")
				.price(BigDecimal.valueOf(30000))
				.build();
	}

	@Test
	void getAllProductsSuccess() throws Exception {
		// Create a product
		createProductSuccess();

		// Get all products
		MvcResult results = mockMvc.perform(MockMvcRequestBuilders.get("/api/product/get-all"))
				.andExpect(status().isOk())
				.andReturn();

		// Deserialize JSON response to list
		String json = results.getResponse().getContentAsString();
		List<ProductResponse> responses = objectMapper.readValue(json, new TypeReference<List<ProductResponse>>(){});

		// Assert response is valid
		Assertions.assertNotNull(responses);
		Assertions.assertEquals(1, responses.size());
		Assertions.assertEquals("Shin - cậu bé bút chì", responses.getFirst().getName());
	}
}
