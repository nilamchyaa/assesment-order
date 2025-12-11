package org.example.service.order;

import org.example.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<Order> getAllOrders(Pageable pageable);
    Order getOrderByIdOrThrow(Long id);
    void saveOrder(Order order);
    void deleteOrder(Order order);
}
