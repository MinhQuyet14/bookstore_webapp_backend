package com.example.scbook.services.Impl;

import com.example.scbook.dtos.OrderDetailDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.Order;
import com.example.scbook.models.OrderDetail;
import com.example.scbook.models.Product;
import com.example.scbook.repositories.OrderDetailRepository;
import com.example.scbook.repositories.OrderRepository;
import com.example.scbook.repositories.ProductRepository;
import com.example.scbook.services.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find order with orderId: " + orderDetailDTO.getOrderId()));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find order with productId: " + orderDetailDTO.getProductId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProduct())
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .build();
        return orderDetailRepository.save(orderDetail);
    }
    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("Cannot find order detail with id: " + id)
        );
    }
    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(
                        ()->new DataNotFoundException("Cannot find order detail with id: " + id)
                );
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(
                        ()->new DataNotFoundException("Cannot find order detail with orderId: " + orderDetailDTO.getOrderId())
                );
        Product existingproduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(
                        ()-> new DataNotFoundException("Cannot find order detail with productId: " + orderDetailDTO.getProductId())
                );
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProduct());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingproduct);
        return orderDetailRepository.save(existingOrderDetail);
    }
    @Override
    @Transactional
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
