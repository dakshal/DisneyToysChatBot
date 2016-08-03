/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlveda.disneychatbot.models;

import java.util.ArrayList;

/**
 *
 * @author incredible
 */
public class PageArray {

    boolean success;
    ArrayList<PagesInsertData> data;

    public PageArray(ArrayList<PagesInsertData> data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public PageArray() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<PagesInsertData> getData() {
        return data;
    }

    public void setData(ArrayList<PagesInsertData> data) {
        this.data = data;
    }

}
