package com.joshua.gallery.backend.model.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(int userId);
    Cart findByUserIdAndItemId(int userId, int ItemId);
    void deleteByUserId(int userId);
}
