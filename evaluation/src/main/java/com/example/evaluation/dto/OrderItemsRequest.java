package com.example.evaluation.dto;

import com.example.evaluation.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class OrderItemsRequest {
    private int id;
    private int productId;
    private int quantity;
    private Order order;
}
