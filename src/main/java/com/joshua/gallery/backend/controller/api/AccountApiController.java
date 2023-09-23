package com.joshua.gallery.backend.controller.api;

import com.joshua.gallery.backend.model.member.Member;
import com.joshua.gallery.backend.model.member.MemberRepository;
import com.joshua.gallery.backend.service.JwtService;
import com.joshua.gallery.backend.service.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class AccountApiController {

     @Autowired
     MemberRepository memberRepository;

     @PostMapping("/account/login")
     public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res) {
          Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
          if (member != null) {
               JwtService jwtService = new JwtServiceImpl();
               int id = member.getId();
               String token = jwtService.getToken("id", id);

               Cookie cookie = new Cookie("token", token);
               cookie.setHttpOnly(true); // 자바스크립트 접근 불가
               cookie.setPath("/");

               res.addCookie(cookie);
               return new ResponseEntity<>(id, HttpStatus.OK);
          }

          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
     }
}
