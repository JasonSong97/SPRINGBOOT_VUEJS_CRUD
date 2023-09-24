package com.joshua.gallery.backend.controller.api;

import com.joshua.gallery.backend.dto.order.OrderDto;
import com.joshua.gallery.backend.model.order.Order;
import com.joshua.gallery.backend.model.order.OrderRepository;
import com.joshua.gallery.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api")
@RestController
public class OrderApiController {

     @Autowired
     JwtService jwtService;

     @Autowired
     OrderRepository orderRepository;

     @GetMapping("/orders")
     public ResponseEntity getOrder(@CookieValue(value = "token", required = false) String token) {
          // token 사용자 확인
          if (!jwtService.isValid(token)) {
               throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
          }

          List<Order> orders = orderRepository.findAll();
          return new ResponseEntity<>(orders, HttpStatus.OK);
     }

     @PostMapping("/orders")
     public ResponseEntity pushOrder(@RequestBody OrderDto orderDto, @CookieValue(value = "token", required = false) String token) {
          // token 사용자 확인
          if (!jwtService.isValid(token)) {
               throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
          }

          // Order 새롭게 만들기
          Order newOrder = new Order();
          newOrder.addMemberId(jwtService.getId(token));
          newOrder.addName(orderDto.getName());
          newOrder.addAddress(orderDto.getAddress());
          newOrder.addPayment(orderDto.getPayment());
          newOrder.addCardNumber(orderDto.getCardNumber());
          newOrder.addItems(orderDto.getItems());

          orderRepository.save(newOrder);

          return new ResponseEntity<>(HttpStatus.OK);
     }

}
