package com.example.evaluation.service;

import com.example.evaluation.dto.OrderItemsRequest;
import com.example.evaluation.entity.Order;
import com.example.evaluation.exception.CommonException;

import java.util.List;

public interface OrderService {

    public Order createOrder(Order order) throws CommonException;
    public Order getOrder(int id) throws CommonException;
    public Order updateOrder(int id, String updateType,List<OrderItemsRequest> orderItems) throws CommonException;
    public String deleteOrder(int id) throws CommonException;
}
