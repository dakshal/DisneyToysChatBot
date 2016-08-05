/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlveda.disneychatbot.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 *
 * @author incredible
 */
public class Image {

    private long id;
    
    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("src")
    private String src;

    private Long position;
    private String updatedAt;

    @SerializedName("product_id")
    private Long productId;
    
    private ArrayList<Long> variantIds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ArrayList<Long> getVariantIds() {
        return variantIds;
    }

    public void setVariantIds(ArrayList<Long> variantIds) {
        this.variantIds = variantIds;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

}
