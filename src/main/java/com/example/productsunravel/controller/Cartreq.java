package com.example.productsunravel.controller;

public class Cartreq {
    private Long productId;
    private Integer userId;
    private int qty;

    public Cartreq(Long productId, Integer userId, int qty){
        this.productId = productId;
        this.userId = userId;
        this.qty = qty;
    }

    public Cartreq(){

    }

    @Override
    public String toString() {
        return "CartItems{" +
                "productId=" + productId +
                ", userId=" + userId +
                ", qty=" + qty +
                ",";
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getQty() {
        return this.qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }



}
