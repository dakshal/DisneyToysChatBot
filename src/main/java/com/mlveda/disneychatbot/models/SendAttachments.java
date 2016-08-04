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
public class SendAttachments {

    private String type;
    private SendPayload payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SendPayload getPayload() {
        return payload;
    }

    public void setPayload(SendPayload payload) {
        this.payload = payload;
    }

}
