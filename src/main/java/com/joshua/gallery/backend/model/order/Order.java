package com.joshua.gallery.backend.model.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "order_tb")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int userId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String address;

    @Column(length = 10, nullable = false)
    private String payment;

    @Column(length = 16)
    private String cardNumber;

    @Column(length = 500, nullable = false)
    private String items; // 구입한 목록

    public void addUserId(int userId) {
        this.userId = userId;
    }

    public void addName(String name) {
        this.name = name;
    }

    public void addAddress(String address) {
        this.address = address;
    }

    public void addPayment(String payment) {
        this.payment = payment;
    }

    public void addItems(String items) {
        this.items = items;
    }

    public void addCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
