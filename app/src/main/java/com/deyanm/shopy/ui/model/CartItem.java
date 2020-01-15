package com.deyanm.shopy.ui.model;

public class CartItem {
    private Product product;
    private int cartQuantity;

    public CartItem(Product product, int cartQuantity) {
        this.product = product;
        this.cartQuantity = cartQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }
}
