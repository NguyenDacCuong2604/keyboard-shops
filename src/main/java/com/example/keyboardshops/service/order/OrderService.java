package com.example.keyboardshops.service.order;

import com.example.keyboardshops.enums.OrderStatus;
import com.example.keyboardshops.exceptions.ResourceNotFoundException;
import com.example.keyboardshops.model.Cart;
import com.example.keyboardshops.model.Order;
import com.example.keyboardshops.model.OrderItem;
import com.example.keyboardshops.model.Product;
import com.example.keyboardshops.repository.OrderRepository;
import com.example.keyboardshops.repository.ProductRepository;
import com.example.keyboardshops.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = getOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalPriceAmount(orderItemList));

        cartService.clearCart(cart.getId());

        return orderRepository.save(order);
    }


    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        return order;
    }

    private List<OrderItem> getOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
        }).toList();
    }

    private BigDecimal calculateTotalPriceAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
