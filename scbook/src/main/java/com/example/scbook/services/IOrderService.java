package com.example.scbook.services;

import com.example.scbook.dtos.OrderDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;
    OrderResponse getOrder(Long id);
    OrderResponse updateOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
    List<OrderResponse> getAllOrders(Long userId);
}
