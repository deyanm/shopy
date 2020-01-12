package com.deyanm.shopy.ui.model;

import java.util.List;

public class Product {

    private List<String> availableColors;
    private String name;
    private String description;
    private String category;
    private int code;
    private float price;
    private float minPrice;
    private String image;
    private List<String> allImages;
    private String shopName;
    private float sale;
    private String timeStamp;
    private int shoppingCartQuantity;
    private int discount;
    private String discountPrice;

    public Product(String name, String shopName, String description, String category, int code, float price, float minPrice, String timeStamp) {
        this.name = name;
        this.shopName = shopName;
        this.description = description;
        this.category = category;
        this.code = code;
        this.price = price;
        this.minPrice = minPrice;
        this.timeStamp = timeStamp;
    }

    public Product(String name, String description, String category, String imageUrl, int code, float price, float sale) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.code = code;
        this.price = price;
        this.image = imageUrl;
        this.sale = sale;
    }

    public Product() {
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<String> getAvailableColors() {
        return availableColors;
    }

    public void setAvailableColors(List<String> availableColors) {
        this.availableColors = availableColors;
    }

    public int getShoppingCartQuantity() {
        return shoppingCartQuantity;
    }

    public void setShoppingCartQuantity(int shoppingCartQuantity) {
        this.shoppingCartQuantity = shoppingCartQuantity;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public List<String> getAllImages() {
        return allImages;
    }

    public void setAllImages(List<String> allImages) {
        this.allImages = allImages;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }
}