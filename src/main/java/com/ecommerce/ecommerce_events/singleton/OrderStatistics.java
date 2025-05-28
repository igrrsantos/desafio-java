package com.ecommerce.ecommerce_events.singleton;

public class OrderStatistics {

    private static OrderStatistics instance;
    private int totalOrdersProcessed;

    private OrderStatistics() {
        this.totalOrdersProcessed = 0;
    }

    public static synchronized OrderStatistics getInstance() {
        if (instance == null) {
            instance = new OrderStatistics();
        }
        return instance;
    }

    public synchronized void incrementOrders() {
        totalOrdersProcessed++;
    }

    public synchronized int getTotalOrdersProcessed() {
        return totalOrdersProcessed;
    }
}
