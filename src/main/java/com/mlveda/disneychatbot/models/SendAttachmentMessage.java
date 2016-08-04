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
public class SendAttachmentMessage{

    private Recipient recipient;
    private SendAttachment message;

    public SendAttachmentMessage(Recipient recipient, SendAttachment message) {
        this.recipient = recipient;
        this.message = message;
    }

    
    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public SendAttachment getMessage() {
        return message;
    }

    public void setMessage(SendAttachment message) {
        this.message = message;
    }
    
    

}
