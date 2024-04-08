package com.example.scbook.services;

import com.example.scbook.dtos.OrderDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order getOrder(Long id);
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long id);
    List<Order> findByUserId(Long userId);
}
