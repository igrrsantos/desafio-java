package com.ecommerce.ecommerce_events.listener;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import com.ecommerce.ecommerce_events.factory.OrderFactory;
import com.ecommerce.ecommerce_events.repository.OrderRepository;
import com.ecommerce.ecommerce_events.strategy.ExpressOrderProcessingStrategy;
import com.ecommerce.ecommerce_events.strategy.OrderProcessingStrategy;
import com.ecommerce.ecommerce_events.strategy.OrderProcessor;
import com.ecommerce.ecommerce_events.strategy.StandardOrderProcessingStrategy;
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

    @RabbitListener(queues = "ecommerce.orders")
    public void handleOrderEvent(String orderDescription) {
        CustomerOrder order = OrderFactory.createOrder("express", "Pedido #5678");
        order.setDescription(orderDescription);

        OrderProcessingStrategy strategy;
        if (orderDescription.contains("Express")) {
            strategy = new ExpressOrderProcessingStrategy(orderRepository);
        } else {
            strategy = new StandardOrderProcessingStrategy(orderRepository);
        }

        OrderProcessor processor = new OrderProcessor(strategy);
        processor.process(order);
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
