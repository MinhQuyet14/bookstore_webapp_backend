package com.example.scbook.controllers;

import com.example.scbook.dtos.OrderDTO;
import com.example.scbook.models.Order;
import com.example.scbook.responses.OrderResponse;
import com.example.scbook.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid OrderDTO orderDTO,
            BindingResult result
    ){
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages.toString());
            }
            Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId){
        try {
            List<Order> orders = orderService.findByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId){
        try {
            Order existingOrder = orderService.getOrder(orderId);
            OrderResponse orderResponse = OrderResponse.fromOrder(existingOrder);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable Long id,
            @Valid @RequestBody OrderDTO orderDTO
    ) {
        try {
            Order order = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
