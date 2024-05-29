package com.example.evaluation.Controller;

import com.example.evaluation.dto.OrderItemsRequest;
import com.example.evaluation.entity.Order;
import com.example.evaluation.exception.OrderItemNotFoundException;
import com.example.evaluation.exception.OrderNotFoundException;
import com.example.evaluation.exception.ProductNotFoundException;
import com.example.evaluation.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody @Valid
                                 Order order) throws ProductNotFoundException {
        return orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable("id") int id) throws OrderNotFoundException{
        return orderService.getOrder(id);
    }

    @PutMapping("/{id}/update")
    public Order updateOrder(@PathVariable("id") int id, @RequestParam(name = "param") String type, @RequestBody  List<OrderItemsRequest> orderItems) throws OrderNotFoundException, OrderItemNotFoundException {
        return orderService.updateOrder(id,type,orderItems);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable("id") int id) throws OrderNotFoundException {
        orderService.deleteOrder(id);
        return "Order deleted Successfully";
    }

}
