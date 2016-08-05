/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlveda.disneychatbot.models;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author incredible
 */
public class Variant {

    @SerializedName("id")
    public long id;

    @SerializedName("product_id")
    public long productId;

    @SerializedName("title")
    public String title;

    @SerializedName("price")
    public String price;

    @SerializedName("sku")
    public String sku;

    @SerializedName("position")
    public long position;

    @SerializedName("grams")
    public long grams;

    @SerializedName("inventory_policy")
    public String inventoryPolicy;

    @SerializedName("compare_at_price")
    public String compareAtPrice;

    @SerializedName("fulfillment_service")
    public String fulfillmentService;

    @SerializedName("inventory_management")
    public String inventoryManagement;

    @SerializedName("option1")
    public String option1;

    @SerializedName("option2")
    public String option2;

    @SerializedName("option3")
    public String option3;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("taxable")
    public Boolean taxable;

    @SerializedName("barcode")
    public String barcode;

    @SerializedName("image_id")
    public long imageId;

    @SerializedName("inventory_quantity")
    public long inventoryQuantity;

    @SerializedName("weight")
    public Double weight;

    @SerializedName("weight_unit")
    public String weightUnit;

    @SerializedName("old_inventory_quantity")
    public long oldInventoryQuantity;

    @SerializedName("requires_shipping")
    public boolean requiresShipping;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getGrams() {
        return grams;
    }

    public void setGrams(long grams) {
        this.grams = grams;
    }

    public String getInventoryPolicy() {
        return inventoryPolicy;
    }

    public void setInventoryPolicy(String inventoryPolicy) {
        this.inventoryPolicy = inventoryPolicy;
    }

    public String getCompareAtPrice() {
        return compareAtPrice;
    }

    public void setCompareAtPrice(String compareAtPrice) {
        this.compareAtPrice = compareAtPrice;
    }

    public String getFulfillmentService() {
        return fulfillmentService;
    }

    public void setFulfillmentService(String fulfillmentService) {
        this.fulfillmentService = fulfillmentService;
    }

    public String getInventoryManagement() {
        return inventoryManagement;
    }

    public void setInventoryManagement(String inventoryManagement) {
        this.inventoryManagement = inventoryManagement;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public long getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(long inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public long getOldInventoryQuantity() {
        return oldInventoryQuantity;
    }

    public void setOldInventoryQuantity(long oldInventoryQuantity) {
        this.oldInventoryQuantity = oldInventoryQuantity;
    }

    public boolean isRequiresShipping() {
        return requiresShipping;
    }

    public void setRequiresShipping(boolean requiresShipping) {
        this.requiresShipping = requiresShipping;
    }


}
