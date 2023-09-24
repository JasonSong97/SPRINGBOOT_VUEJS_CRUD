package com.joshua.gallery.backend.controller.api;

import com.joshua.gallery.backend.model.cart.Cart;
import com.joshua.gallery.backend.model.cart.CartRepository;
import com.joshua.gallery.backend.model.item.Item;
import com.joshua.gallery.backend.model.item.ItemRepository;
import com.joshua.gallery.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class CartApiController {

     @Autowired
     ItemRepository itemRepository;

     @Autowired
     CartRepository cartRepository;

     @Autowired
     JwtService jwtService;

     @GetMapping("/cart/items")
     public ResponseEntity getCartItems(@CookieValue(value = "token", required = false) String token) {
          // token 사용자 확인
          if (!jwtService.isValid(token)) {
               throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
          }
          int memberId = jwtService.getId(token);
          List<Cart> carts = cartRepository.findByMemberId(memberId);

          // itemIds 추출이유 : carts에는 어떤Item이 들어있는지 알 수 없음
          List<Integer> itemIds = carts.stream().map(Cart::getItemId).collect(Collectors.toList());
          List<Item> items = itemRepository.findByIdIn(itemIds); // item 내용들

          return new ResponseEntity<>(items, HttpStatus.OK);
     }

     @PostMapping("/cart/items/{itemId}")
     public ResponseEntity pushCartItem(@PathVariable("itemId") int itemId,
                                    @CookieValue(value = "token", required = false) String token) {
          // token 사용자 확인
          if (!jwtService.isValid(token)) {
               throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
          }
          int memberId = jwtService.getId(token);
          
          // cart 존재 유무에 따른 선택
          Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);
          if (cart == null) {
               Cart newCart = new Cart();
               newCart.addMemberId(memberId);
               newCart.addItemId(itemId);
               cartRepository.save(newCart);
          }

          return new ResponseEntity<>(HttpStatus.OK);
     }

     @DeleteMapping("/cart/items/{itemId}")
     public ResponseEntity removeCartItem(@PathVariable("itemId") int itemId,
                                        @CookieValue(value = "token", required = false) String token) {
          // token 사용자 확인
          if (!jwtService.isValid(token)) {
               throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
          }
          int memberId = jwtService.getId(token);
          Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);
          cartRepository.delete(cart);

          return new ResponseEntity<>(HttpStatus.OK);
     }
}
