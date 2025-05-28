package com.ecommerce.ecommerce_events;
import com.ecommerce.ecommerce_events.domain.CustomerOrder;
import com.ecommerce.ecommerce_events.observer.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class EcommerceEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceEventsApplication.class, args);
	}

	@PostConstruct
	public void init() {
		OrderManager orderManager = new OrderManager();

		orderManager.registerObserver(new NotificationService());
		orderManager.registerObserver(new BillingService());
		orderManager.registerObserver(new ShippingService());

		CustomerOrder order = new CustomerOrder();
		order.setDescription("Pedido #1234");
		orderManager.processOrder(order);
	}
}