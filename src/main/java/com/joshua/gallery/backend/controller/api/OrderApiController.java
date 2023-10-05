package com.joshua.gallery.backend.controller.api;

import com.joshua.gallery.backend.dto.order.OrderDto;
import com.joshua.gallery.backend.model.cart.CartRepository;
import com.joshua.gallery.backend.model.order.Order;
import com.joshua.gallery.backend.model.order.OrderRepository;
import com.joshua.gallery.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

     @Autowired
     CartRepository cartRepository;

     @GetMapping("/orders")
     public ResponseEntity getOrder(@CookieValue(value = "token", required = false) String token) {
          // token 사용자 확인
          if (!jwtService.isValid(token)) {
               throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
          }

          int userId = jwtService.getId(token);
          List<Order> orders = orderRepository.findByUserIdOrderByIdDesc(userId);
          return new ResponseEntity<>(orders, HttpStatus.OK);
     }

     @Transactional
     @PostMapping("/orders")
     public ResponseEntity pushOrder(@RequestBody OrderDto orderDto, @CookieValue(value = "token", required = false) String token) {
          // token 사용자 확인
          if (!jwtService.isValid(token)) {
               throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
          }

          // Order 새롭게 만들기
          Order newOrder = new Order();
          int userId = jwtService.getId(token);
          newOrder.addUserId(userId);
          newOrder.addName(orderDto.getName());
          newOrder.addAddress(orderDto.getAddress());
          newOrder.addPayment(orderDto.getPayment());
          newOrder.addCardNumber(orderDto.getCardNumber());
          newOrder.addItems(orderDto.getItems());

          orderRepository.save(newOrder);

          // 구매완료 후 장바구니 비우기
          cartRepository.deleteByUserId(userId);

          return new ResponseEntity<>(HttpStatus.OK);
     }

}
