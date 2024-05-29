package com.example.evaluation.controller;

import com.example.evaluation.Controller.OrderController;
import com.example.evaluation.exception.CommonException;
import com.example.evaluation.service.OrderService;
import com.example.evaluation.util.TestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.evaluation.util.TestData.order;
import static com.example.evaluation.util.TestData.orderItemsRequestList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    private final TestData testData=new TestData();

    @BeforeEach
    void setUp(){
        testData.setUpOrder();
        testData.setUpOrderItems();
        testData.setUpOrderItemsRequests();
        testData.setUpProducts();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void createOrder() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(order);
        when(orderService.createOrder(order)).thenReturn(order);
        this.mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getOrderById() throws Exception {
        when(orderService.getOrder(0)).thenReturn(order);
        this.mockMvc.perform(get("/orders/" + 0 ))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteOrderById() throws Exception{
        when(orderService.deleteOrder(0)).thenReturn("Order deleted Successfully");
        this.mockMvc.perform(delete("/orders/" + 0 ))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void addOrder() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(orderItemsRequestList);
        when(orderService.updateOrder(9, "add", orderItemsRequestList)).thenReturn(order);
        this.mockMvc.perform(put("/orders/0/update")
                        .param("param", "add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void removeOrder() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(orderItemsRequestList);
        when(orderService.updateOrder(0, "remove", orderItemsRequestList)).thenReturn(order);
        this.mockMvc.perform(put("/orders/0/update")
                        .param("param", "remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void updateOrder() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(orderItemsRequestList);
        when(orderService.updateOrder(0, "update", orderItemsRequestList)).thenReturn(order);
        this.mockMvc.perform(put("/orders/0/update")
                        .param("param", "update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //failure
    @Test
    void deleteOrderByIdException() throws Exception{
        when(orderService.deleteOrder(2)).thenThrow(new CommonException("000","Order not found"));
        this.mockMvc.perform(delete("/orders/" + 2))
                .andDo(print()).andExpect(status().isInternalServerError());
    }

    @Test
    void getOrderByIdException() throws Exception{
        when(orderService.getOrder(2)).thenThrow(new CommonException("000","Order not found"));
        this.mockMvc.perform(get("/orders/" + 2))
                .andDo(print()).andExpect(status().isInternalServerError());
    }

}
