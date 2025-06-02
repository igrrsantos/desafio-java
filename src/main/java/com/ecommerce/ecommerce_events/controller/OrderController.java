package com.ecommerce.ecommerce_events.controller;

import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import com.ecommerce.ecommerce_events.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.queue}")
    private String queueName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderController(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public List<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public CustomerOrder createOrder(@RequestBody CustomerOrder order) throws Exception {
        CustomerOrder saved = orderRepository.save(order);
        String orderJson = objectMapper.writeValueAsString(saved);
        rabbitTemplate.convertAndSend(queueName, orderJson);
        return saved;
    }
}
