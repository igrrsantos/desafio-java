package com.ecommerce.ecommerce_events.factory;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;

public class OrderFactory {

    public static CustomerOrder createOrder(String type, String description) {
        CustomerOrder order = new CustomerOrder();
        order.setDescription(description);

        order.setPriority("express".equalsIgnoreCase(type));

        return order;
    }
}
