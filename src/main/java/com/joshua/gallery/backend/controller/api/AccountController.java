package com.joshua.gallery.backend.controller.api;

import com.joshua.gallery.backend.model.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

     @Autowired
     MemberRepository memberRepository;
}
