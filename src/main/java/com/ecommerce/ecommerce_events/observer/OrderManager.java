package com.ecommerce.ecommerce_events.observer;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import com.ecommerce.ecommerce_events.singleton.OrderStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class OrderManager implements OrderSubject {
    private static final Logger logger = LoggerFactory.getLogger(OrderManager.class);
    private List<OrderObserver> observers = new ArrayList<>();

    @Override
    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(CustomerOrder order) {
        for (OrderObserver observer : observers) {
            observer.update(order);
        }
    }

    public void processOrder(CustomerOrder order) {
        logger.info("Processando pedido no OrderManager: {}", order.getDescription());
        OrderStatistics stats = OrderStatistics.getInstance();
        stats.incrementOrders();
        logger.info("Total de pedidos processados até agora: {}", stats.getTotalOrdersProcessed());
        notifyObservers(order);
    }
}
