package com.ecommerce.ecommerce_events.integration;

import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import com.ecommerce.ecommerce_events.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderFlowIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFullOrderFlow() throws Exception {
        // Prepare request
        CustomerOrder order = new CustomerOrder();
        order.setDescription("Integration test order");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                objectMapper.writeValueAsString(order), headers);

        // Send POST
        ResponseEntity<CustomerOrder> response = restTemplate.postForEntity(
                "/api/orders", request, CustomerOrder.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify in DB
        List<CustomerOrder> orders = orderRepository.findAll();
        assertThat(orders).isNotEmpty();

        // Check message in RabbitMQ (assuming using default exchange/queue)
        Object received = rabbitTemplate.receiveAndConvert("orderQueue");
        assertThat(received).isNotNull();
        assertThat(received.toString()).contains("Integration test order");
    }
}
