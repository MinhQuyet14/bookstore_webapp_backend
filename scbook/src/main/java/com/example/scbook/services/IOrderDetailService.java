package com.example.scbook.services;

import com.example.scbook.dtos.OrderDetailDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    void deleteOrderDetail(Long id);
    List<OrderDetail> findByOrderId(Long orderId);
}
