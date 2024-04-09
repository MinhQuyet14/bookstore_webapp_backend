package com.example.scbook.controllers;

import com.example.scbook.dtos.OrderDetailDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.OrderDetail;
import com.example.scbook.responses.OrderDetailResponse;
import com.example.scbook.services.Impl.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(newOrderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") Long id
    ) throws DataNotFoundException {
        return ResponseEntity.ok(orderDetailService.getOrderDetail(id));
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId){
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails
                .stream()
                .map(OrderDetailResponse::fromOrderDetail)// =~ orderDetail -> OrderDetailResponse.fromOrderDetail(orderDetail)
                .toList();
        return ResponseEntity.ok(orderDetailResponses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        try {
            return ResponseEntity.ok(OrderDetailResponse
                    .fromOrderDetail(orderDetailService.updateOrderDetail(id, orderDetailDTO))
            );
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
