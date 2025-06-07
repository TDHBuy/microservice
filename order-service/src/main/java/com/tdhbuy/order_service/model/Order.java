package com.tdhbuy.order_service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "t_order")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    List<OrderLineItems> orderLineItems;
}
