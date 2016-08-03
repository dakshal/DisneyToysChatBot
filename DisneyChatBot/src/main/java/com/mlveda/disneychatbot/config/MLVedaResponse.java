/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlveda.disneychatbot.config;

import com.google.gson.Gson;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *
 * @author incredible
 */
public abstract class MLVedaResponse<T> implements retrofit.Callback<T> {

    private Gson gson;

    public MLVedaResponse() {
        gson = new Gson();
    }

    @Override
    public void success(T t, Response response) {
        done(t, null);
    }

    @Override
    public void failure(RetrofitError error) {
        done(null, error);
    }

    public abstract void done(T t, RetrofitError error);

}
    
