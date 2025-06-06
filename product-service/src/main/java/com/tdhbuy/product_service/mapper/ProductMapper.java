package com.tdhbuy.product_service.mapper;

import com.tdhbuy.product_service.dto.ProductRequest;
import com.tdhbuy.product_service.dto.ProductResponse;
import com.tdhbuy.product_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "price", target = "price")
    })
    ProductResponse toProductResponse(Product product);
    Product toProduct(ProductRequest productRequest);
}
