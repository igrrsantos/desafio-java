package com.ecommerce.ecommerce_events.observer;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationService implements OrderObserver {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Override
    public void update(CustomerOrder order) {
        logger.info("Notificando cliente: Seu pedido '{}' foi recebido!", order.getDescription());
    }
}