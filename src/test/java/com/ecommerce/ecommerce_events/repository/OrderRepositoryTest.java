package com.ecommerce.ecommerce_events.repository;

import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Test
    void testSaveAndFind() {
        CustomerOrder order = new CustomerOrder();
        order.setDescription("Pedido Teste");
        repository.save(order);

        List<CustomerOrder> allOrders = repository.findAll();
        assertFalse(allOrders.isEmpty());
    }
}
