package com.tdhbuy.order_service.mapper;

import com.tdhbuy.order_service.dto.OrderLineItemsDTO;
import com.tdhbuy.order_service.model.OrderLineItems;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.WARN)
public interface OrderMapper {
    OrderLineItems toOrderLineItems(OrderLineItemsDTO orderLineItemsDTO);
}
