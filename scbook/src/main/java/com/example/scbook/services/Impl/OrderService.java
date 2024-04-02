package com.example.scbook.services.Impl;

import com.example.scbook.dtos.OrderDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.Order;
import com.example.scbook.models.Status;
import com.example.scbook.models.User;
import com.example.scbook.repositories.OrderRepository;
import com.example.scbook.repositories.UserRepository;
import com.example.scbook.responses.OrderResponse;
import com.example.scbook.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(modelMapper->modelMapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(Status.PENDING);
        //check shipping date >= now
        Date shippingDate = orderDTO.getShippingDate() == null ? new Date() : orderDTO.getShippingDate();
        if (shippingDate.before(new Date())){
            throw new DataNotFoundException("Date must be at least now !");
        }
        order.setActive(true);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        return null;
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public List<OrderResponse> getAllOrders(Long userId) {
        return null;
    }
}
