package com.ecommerce.ecommerce_events.observer;

import com.ecommerce.ecommerce_events.domain.CustomerOrder;

public class ShippingService implements OrderObserver {
    @Override
    public void update(CustomerOrder order) {
        System.out.println("Preparing shipping for order: " + order.getDescription());
    }
}