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
class Delivery {

    public ArrayList<String> mids;
    public long watermark;
    public long seq;

    public ArrayList<String> getMids() {
        return mids;
    }

    public void setMids(ArrayList<String> mids) {
        this.mids = mids;
    }

    public long getWatermark() {
        return watermark;
    }

    public void setWatermark(long watermark) {
        this.watermark = watermark;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

}
