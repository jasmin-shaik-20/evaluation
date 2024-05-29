package com.example.evaluation.service;

import com.example.evaluation.dto.OrderItemsRequest;
import com.example.evaluation.entity.Order;
import com.example.evaluation.entity.OrderItems;
import com.example.evaluation.exception.OrderItemNotFoundException;
import com.example.evaluation.exception.OrderNotFoundException;
import com.example.evaluation.exception.ProductNotFoundException;

import java.util.List;

public interface OrderService {

    public Order createOrder(Order order) throws ProductNotFoundException;
    public Order getOrder(int id) throws OrderNotFoundException;
    public Order updateOrder(int id, String updateType,List<OrderItemsRequest> orderItems) throws OrderNotFoundException, OrderItemNotFoundException;
    public String deleteOrder(int id) throws OrderNotFoundException;
}
