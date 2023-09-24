package com.joshua.gallery.backend.model.cart;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int memberId;

    @Column
    private int itemId;

    public void addMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void addItemId(int itemId) {
        this.itemId = itemId;
    }
}
