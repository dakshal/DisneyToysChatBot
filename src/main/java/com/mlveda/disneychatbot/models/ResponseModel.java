/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlveda.disneychatbot.models;

/**
 *
 * @author incredible
 */
public class ResponseModel {

    long CollectionID = 0;
    long ProductID = 0;
    String Option1;
    String Option2;
    String Option3;
    int length = 0;

    public ResponseModel(long CollectionID, int length) {
        this.CollectionID = CollectionID;
        this.length = length;
    }

//    public ResponseModel(long CollectionID, long ProductID) {
//        this.CollectionID = CollectionID;
//        this.ProductID = ProductID;
//    }
//
//    public ResponseModel(long CollectionID, long ProductID, String Option1) {
//        this.CollectionID = CollectionID;
//        this.ProductID = ProductID;
//        this.Option1 = Option1;
//    }
//
//    public ResponseModel(long CollectionID, long ProductID, String Option1, String Option2) {
//        this.CollectionID = CollectionID;
//        this.ProductID = ProductID;
//        this.Option1 = Option1;
//        this.Option2 = Option2;
//    }
//
//    public ResponseModel(long CollectionID, long ProductID, String Option1, String Option2, String Option3) {
//        this.CollectionID = CollectionID;
//        this.ProductID = ProductID;
//        this.Option1 = Option1;
//        this.Option2 = Option2;
//        this.Option3 = Option3;
//    }
    
    public long getCollectionID() {
        return CollectionID;
    }

    public void setCollectionID(long CollectionID) {
        this.CollectionID = CollectionID;
    }

    public long getProductID() {
        return ProductID;
    }

    public void setProductID(long ProductID) {
        this.ProductID = ProductID;
    }

    public String getOption1() {
        return Option1;
    }

    public void setOption1(String Option1) {
        this.Option1 = Option1;
    }

    public String getOption2() {
        return Option2;
    }

    public void setOption2(String Option2) {
        this.Option2 = Option2;
    }

    public String getOption3() {
        return Option3;
    }

    public void setOption3(String Option3) {
        this.Option3 = Option3;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    
}
