package com.joshua.gallery.backend.model.cart;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "cart_tb")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int userId;

    @Column
    private int itemId;

    public void addUserId(int userId) {
        this.userId = userId;
    }

    public void addItemId(int itemId) {
        this.itemId = itemId;
    }
}
