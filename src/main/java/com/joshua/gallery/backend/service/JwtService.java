package com.joshua.gallery.backend.service;

import io.jsonwebtoken.Claims;

public interface JwtService {

    String getToken(String key, Object value);

    Claims getClaims(String token);

    // 요청한 사용자가 올바른지 확인
    boolean isValid(String token);

    // 토큰 정보에서 사용자 Id 가져오기
    int getId(String token);
}
