package org.example.service.order;

import org.example.entity.Inventory;
import org.example.entity.Item;
import org.example.entity.Order;
import org.example.exception.ItemNotFoundException;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private MessageSource messageSource;

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order getOrderByIdOrThrow(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ItemNotFoundException(
                                messageSource.getMessage("order.not.found", null, Locale.ROOT)
                        )
                );
        return order;
    }

    @Override
    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Order order){
        orderRepository.delete(order);
    }
}
