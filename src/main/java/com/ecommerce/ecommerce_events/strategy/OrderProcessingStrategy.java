package com.ecommerce.ecommerce_events.strategy;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;

public interface OrderProcessingStrategy {
    void processOrder(CustomerOrder order);
}
