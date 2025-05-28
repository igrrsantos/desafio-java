package com.ecommerce.ecommerce_events.observer;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;

public class ShippingService implements OrderObserver {
    @Override
    public void update(CustomerOrder order) {
        System.out.println("Preparando envio para o pedido: " + order.getDescription());
    }
}