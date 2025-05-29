package com.ecommerce.ecommerce_events.controller;

import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import com.ecommerce.ecommerce_events.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void testGetAllOrders() throws Exception {
        Mockito.when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateOrder() throws Exception {
        CustomerOrder mockOrder = new CustomerOrder();
        mockOrder.setDescription("Test order");

        Mockito.when(orderRepository.save(any(CustomerOrder.class))).thenReturn(mockOrder);

        mockMvc.perform(post("/api/orders")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(mockOrder)))
                .andExpect(status().isOk());
    }
}
