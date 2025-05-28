package com.ecommerce.ecommerce_events.observer;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;

public interface OrderObserver {
    void update(CustomerOrder order);
}
