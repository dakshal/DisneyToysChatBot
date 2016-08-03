/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlveda.disneychatbot.models;

import com.google.gson.annotations.SerializedName;
import org.bson.Document;

/**
 *
 * @author incredible
 */
public class Collection extends Object {

    @SerializedName("id")
    public Long id;

    @SerializedName("handle")
    public String handle;

    @SerializedName("title")
    public String title;

    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("body_html")
    public String bodyHtml;

    @SerializedName("published_at")
    public String publishedAt;

    @SerializedName("sort_order")
    public String sortOrder;

    @SerializedName("template_suffix")
    public Object templateSuffix;

    @SerializedName("published_scope")
    public String publishedScope;

    @SerializedName("image")
    public Image image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Object getTemplateSuffix() {
        return templateSuffix;
    }

    public void setTemplateSuffix(Object templateSuffix) {
        this.templateSuffix = templateSuffix;
    }

    public String getPublishedScope() {
        return publishedScope;
    }

    public void setPublishedScope(String publishedScope) {
        this.publishedScope = publishedScope;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    

}
