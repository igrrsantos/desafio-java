package com.ecommerce.ecommerce_events.observer;

import com.ecommerce.ecommerce_events.domain.CustomerOrder;

public interface OrderSubject {
    void registerObserver(OrderObserver observer);
    void removeObserver(OrderObserver observer);
    void notifyObservers(CustomerOrder order);
    void processOrder(CustomerOrder order);
}