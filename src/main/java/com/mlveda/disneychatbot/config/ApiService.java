/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlveda.disneychatbot.config;

import com.mlveda.disneychatbot.models.SendAttachmentMessage;
import com.mlveda.disneychatbot.models.TextMessageResponse;
import java.util.HashMap;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 *
 * @author incredible
 */
public interface ApiService {

    public String disney_access_token = "EAAZALggwEzGsBALF4wl7FzLMzMWoe0n43PgE2GmFTrZAg3BJp1uKwL2JLwaLmRyQQkZBipdQbvvTSUaxdgnK2ZBe9p2K9RLvpkLQcVKAobAjEkOV0LWEh9H1Q2yvs3kGNao4TSZA2rnRkzbQ7BbqSXYQmyYXDZBePQ2lMs7OKjTgZDZD";
    static String userID = "";

    @POST("/messages")
    public void sendTextMessageToUser(@Body TextMessageResponse textMessage, Callback<Response> messageSent);

    @POST("/messages")
    public void sendTextMessage(@Body TextMessageResponse textMessage, Callback<Response> messageSent);

    @POST("/messages")
    public void sendAttachmentToUser(@Body SendAttachmentMessage messageData, Callback<Response> callback);

    //"https://graph.facebook.com/v2.6/<USER_ID>?fields=first_name,last_name,profile_pic,locale,timezone,gender&access_token=PAGE_ACCESS_TOKEN"
    @GET(userID + "?fields=first_name,last_name,profile_pic,locale,timezone,gender&access_token=" + disney_access_token)
    public void getUserDetails(@Body HashMap<String, Object> params, Callback<Response> callback);

}
