package com.ecommerce.ecommerce_events.strategy;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;

public class OrderProcessor {
    private OrderProcessingStrategy strategy;

    public OrderProcessor(OrderProcessingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(OrderProcessingStrategy strategy) {
        this.strategy = strategy;
    }

    public void process(CustomerOrder order) {
        strategy.processOrder(order);
    }
}
