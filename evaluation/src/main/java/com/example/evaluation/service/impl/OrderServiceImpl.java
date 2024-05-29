package com.example.evaluation.service.impl;

import com.example.evaluation.constants.InfoId;
import com.example.evaluation.constants.InfoMessage;
import com.example.evaluation.constants.StringConstants;
import com.example.evaluation.dto.OrderItemsRequest;
import com.example.evaluation.entity.Order;
import com.example.evaluation.entity.OrderItems;
import com.example.evaluation.entity.Product;
import com.example.evaluation.exception.CommonException;
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
    public Order createOrder(Order order) throws CommonException {
        List<OrderItems> orderItemsList = order.getOrderItems();
        for (OrderItems data : orderItemsList) {
            Product product = productRepository.findById(data.getProductId()).orElse(null);
            if (product == null) {
                throw new CommonException(InfoId.infoId, InfoMessage.productNotFound);
            }
        }
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(int id) throws CommonException{
        Order order=orderRepository.findById(id).orElse(null);
        if(order==null){
            throw new CommonException(InfoId.infoId,InfoMessage.OrderNotFound);
        }
        return order;
    }

    @Override
    public Order updateOrder(int id, String updateType, List<OrderItemsRequest> orderItems) throws CommonException {
        Order getOrder=orderRepository.findById(id).orElse(null);
        if(getOrder==null){
            throw new CommonException(InfoId.infoId,InfoMessage.OrderNotFound);
        }
        if(Objects.equals(updateType, StringConstants.add)){
            for (OrderItemsRequest data : orderItems) {
                OrderItems orderItems1=OrderItems.build(0,data.getProductId(), data.getQuantity(),data.getOrder());
                orderItemsRepository.save(orderItems1);
            }
        } else if (Objects.equals(updateType, StringConstants.remove)) {
            for (OrderItemsRequest data : orderItems) {
                orderItemsRepository.deleteById(data.getId());
            }
        }
        else if (Objects.equals(updateType, StringConstants.update)){
            for(OrderItemsRequest data : orderItems){
                OrderItems orderItems2 = orderItemsRepository.findById(data.getId()).orElse(null);
                if(orderItems2==null){
                    throw new CommonException(InfoId.infoId,InfoMessage.OrderItemNotFound);
                }
                orderItems2.setProductId(data.getProductId());
                orderItems2.setQuantity(data.getQuantity());
                orderItemsRepository.save(orderItems2);
            }
        }
        return orderRepository.save(getOrder);
    }

    @Override
    public String deleteOrder(int id) throws CommonException {
        Order order=orderRepository.findById(id).orElse(null);
        if(order==null){
            throw new CommonException(InfoId.infoId,InfoMessage.OrderNotFound);
        }
        else {
            orderRepository.deleteById(id);
            return StringConstants.orderDeleted;
        }
    }
}
