package com.ecommerce.ecommerce_events.strategy;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import com.ecommerce.ecommerce_events.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StandardOrderProcessingStrategy implements OrderProcessingStrategy {

    private static final Logger logger = LoggerFactory.getLogger(StandardOrderProcessingStrategy.class);
    private final OrderRepository orderRepository;

    public StandardOrderProcessingStrategy(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void processOrder(CustomerOrder order) {
        if (order == null || order.getDescription() == null || order.getDescription().isEmpty()) {
            logger.warn("Pedido inválido: {}", order);
            return;
        }

        order.setPriority(false);
        logger.info("Processando pedido padrão: {}", order.getDescription());

        verifyStock(order);
        calculateShipping(order);
        sendConfirmation(order);

        orderRepository.save(order);
        logger.info("Pedido processado e salvo: {}", order.getDescription());
    }

    private void verifyStock(CustomerOrder order) {
        // Lógica para verificar disponibilidade de estoque
        logger.info("Verificando estoque para o pedido: {}", order.getDescription());
    }

    private void calculateShipping(CustomerOrder order) {
        // Lógica para calcular o frete
        logger.info("Calculando frete para o pedido: {}", order.getDescription());
    }

    private void sendConfirmation(CustomerOrder order) {
        // Lógica para enviar confirmação ao cliente
        logger.info("Enviando confirmação para o pedido: {}", order.getDescription());
    }
}
