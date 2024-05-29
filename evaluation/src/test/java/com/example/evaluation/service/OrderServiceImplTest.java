package com.example.evaluation.service;

import com.example.evaluation.exception.OrderItemNotFoundException;
import com.example.evaluation.exception.OrderNotFoundException;
import com.example.evaluation.exception.ProductNotFoundException;
import com.example.evaluation.repository.OrderItemsRepository;
import com.example.evaluation.repository.OrderRepository;
import com.example.evaluation.repository.ProductRepository;
import com.example.evaluation.service.impl.OrderServiceImpl;
import com.example.evaluation.util.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

import static com.example.evaluation.util.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderServiceImpl;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private TestData testData;

    @BeforeEach
    void setUp(){
        testData.setUpOrder();
        testData.setUpOrderItems();
        testData.setUpOrderItemsRequests();
        testData.setUpProducts();
    }

    @Test
    void testCreateOrder() throws ProductNotFoundException {
        when(productRepository.findById(10)).thenReturn(Optional.of(product1));
        when(productRepository.findById(20)).thenReturn(Optional.of(product2));
        productRepository.save(product1);
        productRepository.save(product2);
        when(orderRepository.save(order)).thenReturn(order);
        assertThat(orderServiceImpl.createOrder(order)).isEqualTo(order);
    }

    @Test
    void testGetOrderById() throws OrderNotFoundException{
        when(orderRepository.findById(0)).thenReturn(Optional.ofNullable(order));
        assertThat(orderServiceImpl.getOrder(0)).isEqualTo(order);
    }

    @Test
    void testDeleteById() throws OrderNotFoundException{
        when(orderRepository.findById(0)).thenReturn(Optional.ofNullable(order));
        doNothing().when(orderRepository).deleteById(0);
        assertThat(orderServiceImpl.deleteOrder(0)).isEqualTo("Order deleted Successfully");
    }

    @Test
    void testAddProductById() throws OrderNotFoundException, OrderItemNotFoundException {
        when(orderRepository.findById(0)).thenReturn(Optional.ofNullable(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderItemsRepository.save(orderItem1)).thenReturn(orderItem1);
        when(orderItemsRepository.save(orderItem2)).thenReturn(orderItem2);
        assertThat(orderServiceImpl.updateOrder(0,"add",orderItemsRequestList)).isEqualTo(order);
    }

    @Test
    void testRemoveProductById() throws OrderNotFoundException, OrderItemNotFoundException {
        when(orderRepository.findById(0)).thenReturn(Optional.ofNullable(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderItemsRepository.save(orderItem1)).thenReturn(orderItem1);
        when(orderItemsRepository.save(orderItem2)).thenReturn(orderItem2);
        assertThat(orderServiceImpl.updateOrder(0,"remove",orderItemsRequestList)).isEqualTo(order);
    }
    @Test
    void testUpdateProductById() throws OrderNotFoundException, OrderItemNotFoundException {
        when(orderRepository.findById(0)).thenReturn(Optional.ofNullable(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderItemsRepository.findById(3)).thenReturn(Optional.ofNullable(orderItem1));
        when(orderItemsRepository.findById(4)).thenReturn(Optional.ofNullable(orderItem2));
        assertThat(orderServiceImpl.updateOrder(0, "update", orderItemsRequestList)).isEqualTo(order);
    }


    //failure
    @Test
    void testDeleteOrderByIdException(){
        assertThrows(OrderNotFoundException.class,() ->{
            orderServiceImpl.deleteOrder(0);
        });
    }

    @Test
    void testGetOrderByIdException(){
        assertThrows(OrderNotFoundException.class,() ->{
            orderServiceImpl.getOrder(0);
        });
    }


}
