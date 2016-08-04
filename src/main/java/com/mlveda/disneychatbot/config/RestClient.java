/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlveda.disneychatbot.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import java.util.concurrent.TimeUnit;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 *
 * @author incredible
 */
public class RestClient {

    public static final RestClient instance = new RestClient();

    private ApiService apiService;

    //Staging
//    public static final String API_BASE_URL = "http://staging.servify.in:5000/api/v1";
    public static final String API_BASE_URL = "https://graph.facebook.com/v2.6/me";

    public RestClient() {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(requestInterceptor)
                .setEndpoint(API_BASE_URL)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(okHttpClient))
                .build();

        apiService = restAdapter.create(ApiService.class);
    }

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addQueryParam("access_token", AppConstant.ACCESSTOKEN);
            request.addHeader("Content-type", "application/json");
//            request.addHeader("DeviceType",  "Android");
//            request.addHeader("Version", ""+BuildConfig.VERSION_CODE);
//            request.addHeader("App", "Servify");
        }
    };

    public ApiService getApiService() {
        return apiService;
    }
}
