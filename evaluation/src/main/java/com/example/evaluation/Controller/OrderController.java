package com.example.evaluation.Controller;

import com.example.evaluation.constants.StringConstants;
import com.example.evaluation.dto.OrderItemsRequest;
import com.example.evaluation.entity.Order;
import com.example.evaluation.exception.CommonException;
import com.example.evaluation.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody @Valid
                                 Order order) throws CommonException {
        log.info("Order created Successfully");
        return orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable("id") int id) throws CommonException{
        log.info("Order found Successfully");
        return orderService.getOrder(id);
    }

    @PutMapping("/{id}/update")
    public Order updateOrder(@PathVariable("id") int id, @RequestParam(name = "param") String type, @RequestBody  List<OrderItemsRequest> orderItems) throws CommonException {
        log.info("Order updated Successfully");
        return orderService.updateOrder(id,type,orderItems);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable("id") int id) throws CommonException {
        orderService.deleteOrder(id);
        log.info("Order deleted Successfully");
        return StringConstants.orderDeleted;
    }

}
