package com.example.keyboardshops.service.order;

import com.example.keyboardshops.dto.OrderDto;
import com.example.keyboardshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
