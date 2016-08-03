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
public class TextMessageResponse {

    private Recipient recipient;
    private SendMessage message;

    public TextMessageResponse(Recipient recipient, SendMessage message) {
        this.recipient = recipient;
        this.message = message;
    }
    
    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public SendMessage getMessage() {
        return message;
    }

    public void setMessage(SendMessage message) {
        this.message = message;
    }

}
