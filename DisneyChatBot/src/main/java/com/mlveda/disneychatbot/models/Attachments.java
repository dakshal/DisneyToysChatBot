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
public class Attachments {

    public String type;
    public Payload payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

}

//-----------------------------------com.mlveda.disneychatbot.models.Entry.java-----------------------------------
//
//package com.mlveda.disneychatbot.models;
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.annotation.Generated;
//
//@Generated("org.jsonschema2pojo")
//public class Entry {
//
//public String id;
//public Integer time;
//public List<Messaging> messaging = new ArrayList<Messaging>();
//
//}
//-----------------------------------com.mlveda.disneychatbot.models.Message.java-----------------------------------
//
//package com.mlveda.disneychatbot.models;
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.annotation.Generated;
//
//@Generated("org.jsonschema2pojo")
//public class Message {
//
//public String object;
//public List<Entry> entry = new ArrayList<Entry>();
//
//}
//-----------------------------------com.mlveda.disneychatbot.models.Message_.java-----------------------------------
//
//package com.mlveda.disneychatbot.models;
//
//import javax.annotation.Generated;
//
//@Generated("org.jsonschema2pojo")
//public class Message_ {
//
//public String mid;
//public Integer seq;
//public String text;
//
//}
//-----------------------------------com.mlveda.disneychatbot.models.Messaging.java-----------------------------------
//
//package com.mlveda.disneychatbot.models;
//
//import javax.annotation.Generated;
//
//@Generated("org.jsonschema2pojo")
//public class Messaging {
//
//public Sender sender;
//public Recipient recipient;
//public Integer timestamp;
//public Message_ message;
//
//}
//-----------------------------------com.mlveda.disneychatbot.models.Recipient.java-----------------------------------
//
//package com.mlveda.disneychatbot.models;
//
//import javax.annotation.Generated;
//
//@Generated("org.jsonschema2pojo")
//public class Recipient {
//
//public String id;
//
//}
//-----------------------------------com.mlveda.disneychatbot.models.Sender.java-----------------------------------
//
//package com.mlveda.disneychatbot.models;
//
//import javax.annotation.Generated;
//
//@Generated("org.jsonschema2pojo")
//public class Sender {
//
//public String id;
//
//}
