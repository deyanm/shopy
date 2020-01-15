package com.deyanm.shopy.ui.model;

import java.util.List;

public class Cart {
    private List<CartItem> cartItems;
    private String userUId;

    public Cart() {

    }

    public Cart(List<CartItem> cartItems, String userUId) {
        this.cartItems = cartItems;
        this.userUId = userUId;
    }


    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getUserUId() {
        return userUId;
    }

    public void setUserUId(String userUId) {
        this.userUId = userUId;
    }
}