package com.ecommerce.ecommerce_events.processor;

import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class OrderItemProcessor implements ItemProcessor<String, CustomerOrder> {

    @Override
    public CustomerOrder process(String item) throws Exception {
        return new CustomerOrder(item.toUpperCase());
    }
}
