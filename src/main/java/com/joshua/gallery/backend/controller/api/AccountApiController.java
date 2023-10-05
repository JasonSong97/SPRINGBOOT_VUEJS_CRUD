package com.joshua.gallery.backend.controller.api;

import com.joshua.gallery.backend.model.user.User;
import com.joshua.gallery.backend.model.user.UserRepository;
import com.joshua.gallery.backend.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class AccountApiController {

     @Autowired
     UserRepository userRepository;

     @Autowired
     JwtService jwtService;

     @PostMapping("/account/logout")
     public ResponseEntity login(HttpServletResponse res) {
          Cookie cookie = new Cookie("token", null);
          cookie.setPath("/");
          cookie.setMaxAge(0);

          res.addCookie(cookie);
          throw new ResponseStatusException(HttpStatus.OK);
     }

     @PostMapping("/account/login")
     public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res) {
          User user = userRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
          if (user != null) {
               int id = user.getId();
               String token = jwtService.getToken("id", id);

               Cookie cookie = new Cookie("token", token);
               cookie.setHttpOnly(true); // 자바스크립트 접근 불가
               cookie.setPath("/");

               res.addCookie(cookie);
               return new ResponseEntity<>(id, HttpStatus.OK);
          }

          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
     }

     @GetMapping("/account/check")
     public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
          Claims claims = jwtService.getClaims(token);

          if (claims != null) {
               int id = Integer.parseInt(claims.get("id").toString());
               return new ResponseEntity<>(id, HttpStatus.OK);
          }

          return new ResponseEntity<>(null, HttpStatus.OK);
     }
}
