package com.joshua.gallery.backend.controller.api;

import com.joshua.gallery.backend.model.member.Member;
import com.joshua.gallery.backend.model.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api")
@RestController
public class AccountApiController {

     @Autowired
     MemberRepository memberRepository;

     @PostMapping("/account/login")
     public int login(@RequestBody Map<String, String> params) {
          Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
          if (member != null) {
               return member.getId();
          }

          return 0;
     }
}
