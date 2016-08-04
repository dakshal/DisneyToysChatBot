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
public class SendPayload {

    public String template_type;
    public ArrayList<Element> elements = new ArrayList<Element>();

    public String getTemplateType() {
        return template_type;
    }

    public void setTemplateType(String templateType) {
        this.template_type = templateType;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }

}
