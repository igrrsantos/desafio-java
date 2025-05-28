package com.ecommerce.ecommerce_events.listener;

import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import com.ecommerce.ecommerce_events.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class OrderEventListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventListener.class);
    private final ExecutorService taskExecutor;
    private final OrderRepository orderRepository;

    public OrderEventListener(ExecutorService taskExecutor, OrderRepository orderRepository) {
        this.taskExecutor = taskExecutor;
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void handleOrderEvent(String message) {
        if (message.contains("FAIL")) {
            throw new RuntimeException("Simulated processing failure");
        }

        logger.info("Received order event, submitting to executor: {}", message);

        taskExecutor.submit(() -> {
            processOrder(message);
        });
    }

    private void processOrder(String message) {
        logger.info("Processing order in thread {}: {}", Thread.currentThread().getName(), message);
        try {
            Thread.sleep(2000);  // Simula trabalho pesado
            CustomerOrder order = new CustomerOrder(message.toUpperCase());
            orderRepository.save(order);
            logger.info("Saved order: {}", order.getDescription());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted", e);
        }
        logger.info("Finished processing order: {}", message);
    }
}
