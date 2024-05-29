package com.example.evaluation.util;

import com.example.evaluation.dto.OrderItemsRequest;
import com.example.evaluation.entity.Order;
import com.example.evaluation.entity.OrderItems;
import com.example.evaluation.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class TestData {

    public static Product product1;
    public static Product product2;
    public static OrderItems orderItem1;
    public static OrderItems orderItem2;
    public static List<OrderItems> orderItemsList;
    public static Order order;
    public static OrderItemsRequest orderItemsRequest1;
    public static OrderItemsRequest orderItemsRequest2;
    public static  List<OrderItemsRequest> orderItemsRequestList;

    public void setUpProducts() {
        product1 = Product.build(10, "soap", 30.0);
        product2 = Product.build(20, "pen", 10.0);
    }

    public void setUpOrderItems() {
        orderItem1 = OrderItems.build(1, 10, 2, null);
        orderItem2 = OrderItems.build(2, 20, 4, null);
        orderItemsList = List.of(orderItem1, orderItem2);
    }

    public void setUpOrder() {
        order = Order.build(0, 1, orderItemsList);
    }

    public void setUpOrderItemsRequests() {
        orderItemsRequest1 = OrderItemsRequest.build(3, 24, 5, null);
        orderItemsRequest2 = OrderItemsRequest.build(4, 25, 6, null);
        orderItemsRequestList = List.of(orderItemsRequest1, orderItemsRequest2);
    }

}
