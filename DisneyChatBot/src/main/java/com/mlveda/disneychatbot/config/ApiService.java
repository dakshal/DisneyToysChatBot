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
import retrofit.http.GET;
import retrofit.http.POST;

/**
 *
 * @author incredible
 */
public interface ApiService {

    static String disney_access_token = "EAACEdEose0cBAJZC3ZAWJgrDrKuEhOh8PC29BQSDmjHdCWYaBcRkikQb7SMDPdwMIES4I4nC3oh0cxDwP4YM6gDVOaHr9YY5lvHz3inWKBZAZAyG56KUf3KcYFzoEZAO52qNYV88QJEIpF8imELVSZCPiAuD4LZCnXB4IUm87Aij3l93HZC2CMZAt";
    static String userID = "";

    @POST("/messages?access_token=" + disney_access_token)
    public void sendTextMessageToUser(@Body TextMessageResponse textMessage, Callback<Response> messageSent);

    @POST("/messages?access_token=" + disney_access_token)
    public void sendAttachmentToUser(@Body SendAttachmentMessage messageData, Callback<Response> callback);

    //"https://graph.facebook.com/v2.6/<USER_ID>?fields=first_name,last_name,profile_pic,locale,timezone,gender&access_token=PAGE_ACCESS_TOKEN"
    @GET(userID + "?fields=first_name,last_name,profile_pic,locale,timezone,gender&access_token=" + disney_access_token)
    public void getUserDetails(@Body HashMap<String, Object> params, Callback<Response> callback);

}
