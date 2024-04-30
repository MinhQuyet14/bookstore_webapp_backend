package com.example.scbook.services.Impl;

import com.example.scbook.dtos.OrderDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.Order;
import com.example.scbook.models.Status;
import com.example.scbook.models.User;
import com.example.scbook.repositories.OrderRepository;
import com.example.scbook.repositories.UserRepository;
import com.example.scbook.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
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
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Cannot find order with id: "+ id));
        User existingUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find user with id: "+ orderDTO.getUserId()));

        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(modelMapper->modelMapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order != null){
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
