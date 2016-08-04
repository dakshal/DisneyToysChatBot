/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlveda.disneychatbot.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author incredible
 */
public class MongoProvider {

    public static MongoProvider instance = new MongoProvider();

    private static final String URL = "localhost";
    private static final String DATABASE = "shopify_chatbot";
    private static MongoClient mongo = new MongoClient(URL, 27017);
    
    private MongoProvider(){
        
    }

    private static MongoDatabase db;
//    boolean auth = db.authenticate("username", "password".toCharArray());

    public static MongoDatabase getInstance() {
        if (db == null) {
            db = mongo.getDatabase(DATABASE);
        }
                
        return db;
    }

}
