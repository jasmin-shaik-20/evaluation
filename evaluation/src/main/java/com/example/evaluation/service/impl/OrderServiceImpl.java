package com.example.evaluation.service.impl;

import com.example.evaluation.dto.OrderItemsRequest;
import com.example.evaluation.entity.Order;
import com.example.evaluation.entity.OrderItems;
import com.example.evaluation.entity.Product;
import com.example.evaluation.exception.OrderItemNotFoundException;
import com.example.evaluation.exception.OrderNotFoundException;
import com.example.evaluation.exception.ProductNotFoundException;
import com.example.evaluation.repository.OrderItemsRepository;
import com.example.evaluation.repository.OrderRepository;
import com.example.evaluation.repository.ProductRepository;
import com.example.evaluation.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemsRepository orderItemsRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public Order createOrder(Order order) throws ProductNotFoundException {
        List<OrderItems> orderItemsList = order.getOrderItems();
        for (OrderItems data : orderItemsList) {
            Product product = productRepository.findById(data.getProductId()).orElse(null);
            if (product == null) {
                throw new ProductNotFoundException("Product not available");
            }
        }
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(int id) throws OrderNotFoundException{
        Order order=orderRepository.findById(id).orElse(null);
        if(order==null){
            throw new OrderNotFoundException("Order not found");
        }
        return order;
    }

    @Override
    public Order updateOrder(int id, String updateType, List<OrderItemsRequest> orderItems) throws OrderNotFoundException, OrderItemNotFoundException {
        Order getOrder=orderRepository.findById(id).orElse(null);
        if(getOrder==null){
            throw new OrderNotFoundException("Order not found");
        }
        if(Objects.equals(updateType, "add")){
            for (OrderItemsRequest data : orderItems) {
                OrderItems orderItems1=OrderItems.build(0,data.getProductId(), data.getQuantity(),data.getOrder());
                orderItemsRepository.save(orderItems1);
            }
        } else if (Objects.equals(updateType, "remove")) {
            for (OrderItemsRequest data : orderItems) {
                orderItemsRepository.deleteById(data.getId());
            }
        }
        else if (Objects.equals(updateType, "update")){
            for(OrderItemsRequest data : orderItems){
                OrderItems orderItems2 = orderItemsRepository.findById(data.getId()).orElse(null);
                if(orderItems2==null){
                    throw new OrderItemNotFoundException("Order Item not found");
                }
                orderItems2.setProductId(data.getProductId());
                orderItems2.setQuantity(data.getQuantity());
                orderItemsRepository.save(orderItems2);
            }
        }
        return orderRepository.save(getOrder);
    }

    @Override
    public String deleteOrder(int id) throws OrderNotFoundException {
        Order order=orderRepository.findById(id).orElse(null);
        if(order==null){
            throw new OrderNotFoundException("Order not found");
        }
        else {
            orderRepository.deleteById(id);
            return "Order deleted Successfully";
        }
    }
}
