package com.joshua.gallery.backend.model.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserIdOrderByIdDesc(int memberId);
}
