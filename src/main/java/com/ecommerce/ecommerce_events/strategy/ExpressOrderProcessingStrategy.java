package com.ecommerce.ecommerce_events.strategy;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import com.ecommerce.ecommerce_events.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExpressOrderProcessingStrategy implements OrderProcessingStrategy {

    private static final Logger logger = LoggerFactory.getLogger(ExpressOrderProcessingStrategy.class);
    private final OrderRepository orderRepository;

    public ExpressOrderProcessingStrategy(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void processOrder(CustomerOrder order) {
        if (order == null || order.getDescription() == null || order.getDescription().isEmpty()) {
            logger.warn("Pedido inválido: {}", order);
            return;
        }

        order.setPriority(true);
        logger.info("Pedido marcado como prioritário: {}", order.getDescription());

        sendNotification(order);

        orderRepository.save(order);
        logger.info("Pedido processado e salvo: {}", order.getDescription());
    }

    private void sendNotification(CustomerOrder order) {
        logger.info("Notificação enviada para o cliente sobre o pedido: {}", order.getDescription());
    }
}
