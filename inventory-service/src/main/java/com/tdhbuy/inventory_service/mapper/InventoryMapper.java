package com.tdhbuy.inventory_service.mapper;

import com.tdhbuy.inventory_service.dto.InventoryRequest;
import com.tdhbuy.inventory_service.dto.InventoryResponse;
import com.tdhbuy.inventory_service.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.WARN)
public interface InventoryMapper {
    List<Inventory> mapInventory(List<InventoryRequest> inventoryRequests);
}
