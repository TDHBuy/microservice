package com.tdhbuy.inventory_service.service;

import com.tdhbuy.inventory_service.dto.InventoryResponse;
import com.tdhbuy.inventory_service.mapper.InventoryMapper;
import com.tdhbuy.inventory_service.model.Inventory;
import com.tdhbuy.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes){
        // Find inventory based on skuCode
        List<Inventory> inventories = inventoryRepository.findBySkuCodeIn(skuCodes);
        // Map found inventory to map by skuCode
        Map<String, Inventory> foundMap = inventories.stream()
                .collect(Collectors.toMap(Inventory::getSkuCode, inventory -> inventory));
        // Loop through all skuCodes to mapping inventory
        return skuCodes.stream().map(
                skuCode ->
                {
                    Inventory inventory = foundMap.get(skuCode);
                    return InventoryResponse.builder()
                            .skuCode(skuCode)
                            .isInStock(inventory != null && inventory.getQuantity() > 0)
                            .build();
                }
        ).toList();
    }

}
