package com.ecommerce.ecommerce_events.observer;

import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {

    static class MockObserver implements OrderObserver {
        boolean called = false;

        @Override
        public void update(CustomerOrder order) {
            called = true;
        }

        boolean wasCalled() {
            return called;
        }
    }

    @Test
    void testObserversAreCalled() {
        OrderManager manager = new OrderManager();
        MockObserver observer = new MockObserver();
        manager.registerObserver(observer);

        CustomerOrder order = new CustomerOrder();
        order.setDescription("Pedido 123");
        manager.processOrder(order);

        assertTrue(observer.wasCalled());
    }
}
