package com.example.evaluation.service;

import com.example.evaluation.entity.Order;
import com.example.evaluation.entity.OrderItems;
import com.example.evaluation.repository.OrderRepository;
import com.example.evaluation.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderServiceImplTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;

    private Order order;
    private OrderItems orderItem1;
    private OrderItems orderItem2;
    private List<OrderItems> orderItemsList;

    @BeforeEach
    void setUp(){
        orderItem1=OrderItems.build(1,10,2,null);
        orderItem2=OrderItems.build(2,20,4,null);
        orderItemsList=List.of(orderItem1,orderItem2);
        order=Order.build(0,1,orderItemsList);
    }

}
