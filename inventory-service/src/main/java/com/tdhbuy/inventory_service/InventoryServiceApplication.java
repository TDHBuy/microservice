package com.tdhbuy.inventory_service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaNamespace;
import com.tdhbuy.inventory_service.dto.InventoryRequest;
import com.tdhbuy.inventory_service.dto.InventoryResponse;
import com.tdhbuy.inventory_service.mapper.InventoryMapper;
import com.tdhbuy.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

// Init data
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
		return args -> {
			ObjectMapper objectMapper = new ObjectMapper();
			// Load Json file from resources
			ClassPathResource classPathResource = new ClassPathResource("inventory_init.json");

			// Read Json as List InventoryRequest
			List<InventoryRequest> list_request = objectMapper.readValue(
					classPathResource.getInputStream(),
					new TypeReference<List<InventoryRequest>>() {}
			);

			// Save to DB
			try{
				inventoryRepository.saveAll(inventoryMapper.mapInventory(list_request));
			}catch (Exception e){
				System.err.println("Failed to initialize inventory data: " + e.getMessage());
			}
		};
	}
}
