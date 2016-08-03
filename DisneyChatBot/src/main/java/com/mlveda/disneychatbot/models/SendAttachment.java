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
public class SendAttachment {

    private SendAttachments attachment;

    public SendAttachment(SendAttachments attachment) {
        this.attachment = attachment;
    }

    public SendAttachments getAttachment() {
        return attachment;
    }

    public void setAttachment(SendAttachments attachment) {
        this.attachment = attachment;
    }

}
