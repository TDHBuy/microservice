package com.tdhbuy.order_service.service;

import com.tdhbuy.order_service.dto.OrderRequest;
import com.tdhbuy.order_service.mapper.OrderMapper;
import com.tdhbuy.order_service.model.Order;
import com.tdhbuy.order_service.model.OrderLineItems;
import com.tdhbuy.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList =
                orderRequest.getOrderLineItemsDTOList().stream()
                .map(orderMapper::toOrderLineItems)
                .toList();

        order.setOrderLineItems(orderLineItemsList);
        orderRepository.save(order);
    }
}
