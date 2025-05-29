package com.ecommerce.ecommerce_events.strategy;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import com.ecommerce.ecommerce_events.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StrategyTest {

    private OrderRepository mockRepo;

    @BeforeEach
    void setUp() {
        mockRepo = mock(OrderRepository.class);
    }

    @Test
    void testStandardStrategy() {
        StandardOrderProcessingStrategy strategy = new StandardOrderProcessingStrategy(mockRepo);
        CustomerOrder order = new CustomerOrder();
        order.setDescription("Pedido Teste");
        strategy.processOrder(order);
        assertFalse(order.isPriority());
    }

    @Test
    void testExpressStrategy() {
        ExpressOrderProcessingStrategy strategy = new ExpressOrderProcessingStrategy(mockRepo);
        CustomerOrder order = new CustomerOrder();
        order.setDescription("Pedido Express");
        strategy.processOrder(order);
        assertTrue(order.isPriority());
    }
}
