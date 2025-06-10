package com.tdhbuy.order_service.service;

import com.tdhbuy.order_service.dto.InventoryResponse;
import com.tdhbuy.order_service.dto.OrderLineItemsDTO;
import com.tdhbuy.order_service.dto.OrderRequest;
import com.tdhbuy.order_service.mapper.OrderMapper;
import com.tdhbuy.order_service.model.Order;
import com.tdhbuy.order_service.model.OrderLineItems;
import com.tdhbuy.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList =
                orderRequest.getOrderLineItemsDTOList().stream()
                .map(orderMapper::toOrderLineItems)
                .toList();

        // Fetch inventory details for ensure that all requested products are available in stock
        Boolean inStock = isInStock(orderRequest);

        if(inStock) {
            order.setOrderLineItems(orderLineItemsList);
            orderRepository.save(order);
        }else
            throw new IllegalArgumentException("Product is not in stock");
    }

    // Ensure that all requested products are available in stock before proceeding
    private Boolean isInStock(OrderRequest request){
        List<String> skuCodes = request.getOrderLineItemsDTOList().stream()
                .map(OrderLineItemsDTO::getSkuCode).toList();
        InventoryResponse[] responses = inventoryStockResponse(skuCodes);
        return Arrays.stream(responses)
                .allMatch(InventoryResponse::getIsInStock);
    }

    // Fetch inventory details for the given list of SKU codes from the inventory service
    private InventoryResponse[] inventoryStockResponse(List<String> skuCodes) {
        return webClient.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                .block();
    }
}
