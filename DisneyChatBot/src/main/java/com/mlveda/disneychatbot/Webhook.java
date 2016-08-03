/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlveda.disneychatbot;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mlveda.disneychatbot.config.ApiService;
import com.mlveda.disneychatbot.config.MLVedaResponse;
import com.mlveda.disneychatbot.config.RestClient;
import com.mlveda.disneychatbot.database.MongoProvider;
import com.mlveda.disneychatbot.models.Attachments;
import com.mlveda.disneychatbot.models.Button;
import com.mlveda.disneychatbot.models.Collection;
import com.mlveda.disneychatbot.models.CollectionModel;
import com.mlveda.disneychatbot.models.Element;
import com.mlveda.disneychatbot.models.Entry;
import com.mlveda.disneychatbot.models.Message;
import com.mlveda.disneychatbot.models.Messaging;
import com.mlveda.disneychatbot.models.FBChatBotWebhookResponse;
import com.mlveda.disneychatbot.models.Option;
import com.mlveda.disneychatbot.models.Payload;
import com.mlveda.disneychatbot.models.PayloadButton;
import com.mlveda.disneychatbot.models.Product;
import com.mlveda.disneychatbot.models.ProductCollectionMapping;
import com.mlveda.disneychatbot.models.Recipient;
import com.mlveda.disneychatbot.models.RedirectButton;
import com.mlveda.disneychatbot.models.SendAttachment;
import com.mlveda.disneychatbot.models.SendAttachmentMessage;
import com.mlveda.disneychatbot.models.SendAttachments;
import com.mlveda.disneychatbot.models.SendMessage;
import com.mlveda.disneychatbot.models.SendPayload;
import com.mlveda.disneychatbot.models.TextMessageResponse;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *
 * @author incredible
 */
@WebServlet(name = "Webhook", urlPatterns = {"/webhook"})
public class Webhook extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final Gson gson = new Gson();
    private final ApiService service = RestClient.instance.getApiService();

//    public String[] elementTitle = new String[]{"Casio Scientific", "Cltllzen CT-512 Basic", "Casio FX991ES Plus Scientific Calculator", "Cltllzen Notebook Type Scientific", "Texas Instruments BA II Plus Financial"};
//    public String[] elementSubTitle = new String[]{"Price Rs. 390 + Rs. 50 delivery charge\n4.4 user rating", "Price Rs. 100 + Rs. 100 delivery charge\n3.5 user rating", "Price Rs. 895 + Rs. 100 delivery charge\n4.7 user rating", "Price Rs. 300 + Rs. 50 delivery charge\n3.7 user rating", "Price Rs. 2,850 + Rs. 100 delivery charge\n4.4 user rating"};
//    public String[] elementItemURL = new String[]{"http://www.flipkart.com/casio-scientific/p/itmd42zcderygges?pid=CALEAXGWBZRNMED9&al=0VV%2Fo0h0R%2FlQGaNytcUSkMldugMWZuE75aUsiwTbcEN%2FtbeNXjslkocG%2BQexgPVO%2BkeMe8Sn%2FR8%3D&ref=L%3A-199285331275200905&srno=p_1&findingMethod=Search&otracker=start", "http://www.flipkart.com/cltllzen-ct-512-basic/p/itme7zc58fpphwtv?pid=CALE7ZC52F2MW2DZ&al=0VV%2Fo0h0R%2FlQGaNytcUSkMldugMWZuE75aUsiwTbcEM%2FDyl0zmgBbv0vHpiFgsF1%2FB5AahOzqsI%3D&ref=L%3A-199285331275200905&srno=p_3&otracker=from-search", "http://www.flipkart.com/casio-fx991es-plus-scientific-calculator/p/itmdcmmvuzgsdrsk?pid=CALDCMMV9NTQHR2H&al=0VV%2Fo0h0R%2FlQGaNytcUSkMldugMWZuE75aUsiwTbcENbOwAyMJf4U%2Fd4%2FtWZ1ElZmuEmDgnCohc%3D&ref=L%3A-199285331275200905&srno=p_5&otracker=from-search", "http://www.flipkart.com/cltllzen-notebook-type-scientific/p/itme7ayvtpqnzpzg?pid=CALE7AYVTBFBJ94C&al=0VV%2Fo0h0R%2FlQGaNytcUSkMldugMWZuE75aUsiwTbcENjQomTUF8KpiBi0HZoixj9Jtp6x6APD1Y%3D&ref=L%3A-199285331275200905&srno=p_6&otracker=from-search", "http://www.flipkart.com/texas-instruments-ba-ii-plus-financial/p/itmd42zjwwj2de2b?pid=CALD42ZGAYH3AZH4&al=0VV%2Fo0h0R%2FntY%2FWp4sZ0i8ldugMWZuE75aUsiwTbcEMeyG07ZeVS3JVZ97IrGvQOyCCvGp7pBKE%3D&ref=L%3A-199285331275200905&srno=p_11&otracker=from-search"};
//    public String[] elementImageURL = new String[]{"http://img5a.flixcart.com/image/calculator/e/d/9/casio-fx-82ms-400x400-imaeax7vasgcd6eg.jpeg", "http://img5a.flixcart.com/image/calculator/j/a/v/cltllzen-ct-512-ct-512-black-400x400-imae7ygyqsh55ph6.jpeg", "http://img5a.flixcart.com/image/calculator/r/2/h/casio-fx991es-plus-fx991es-plus-400x400-imadnzghbjmaqyuw.jpeg", "http://img5a.flixcart.com/image/calculator/9/4/c/cltllzen-notebook-type-ct-8814v-w-400x400-imae79ydb6qtwvvd.jpeg", "http://img6a.flixcart.com/image/calculator/z/h/4/texas-instruments-ba-ii-ba-ii-plus-400x400-imadnzj3uvfg7xvc.jpeg"};
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getMethod().equals("GET")) {
            webhookSetupDetails(request, response);
        } else if (request.getMethod().equals("POST")) {

            String requestParams = getBody(request);
            System.out.println(requestParams);

            FBChatBotWebhookResponse body = gson.fromJson(requestParams, FBChatBotWebhookResponse.class);

            if (body.getObject().equals("page")) {
                ArrayList<Entry> entry = body.getEntry();

                if (entry.size() > 0) {
                    for (int i = 0; i < entry.size(); i++) {
                        ArrayList<Messaging> message = entry.get(i).getMessaging();
                        for (int j = 0; j < message.size(); j++) {
                            Messaging event = message.get(j);
                            if (event.getOptin() != null) {

                            } else if (event.getMessage() != null) {

                                receivedMessage(event);

                            } else if (event.getDelivery() != null) {

                            } else if (event.getPostback() != null) {
                                receivedPostback(event);
                            } else {
                                System.err.println("else is received" + gson.toJson(event));
                            }
                        }
                    }
                } else {
                    System.err.println("Entry size is zero");
                }
            }

//            String payloadRequest = getBody(request);
//            System.out.println(payloadRequest);
//            JSONObject body = new JSONObject(payloadRequest);
//            if (body.get("object").equals("page")) {
//                JSONArray entryArray = body.getJSONArray("entry");
//                for (int i = 0; i < entryArray.length(); i++) {
//                    JSONObject object = entryArray.getJSONObject(i);
//
//                    String pageID = object.getString("id");
//                    long pageTime = object.getLong("time");
//
//                    JSONArray messageEvent = object.getJSONArray("messaging");
//
//                    for (int j = 0; j < messageEvent.length(); j++) {
//                        JSONObject event = messageEvent.getJSONObject(j);
//                        if (!event.isNull("optin")) {
//
//                        } else if (!event.isNull("message")) {
//                            receivedMessage(event);
//                        } else if (!event.isNull("delivery")) {
//
//                        } else if (!event.isNull("postback")) {
//                            receivedPostback(event);
//                        } else {
//                            System.out.println();
//                        }
//                    }
//                }
//            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void webhookSetupDetails(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getQueryString());
        String queryString = request.getQueryString();
        if (queryString == null) {
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("{}");
            } catch (IOException ex) {
                Logger.getLogger(Webhook.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String tokens[] = queryString.split("&");
            String mode = tokens[0].split("=")[1];
            String challenge = tokens[1].split("=")[1];
            String verify_token = tokens[2].split("=")[1];

            if (mode.equalsIgnoreCase("subscribe") && verify_token.equalsIgnoreCase("this_is_a_testing_verification_token")) {
                response.setContentType("application/json");
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println(challenge);
                } catch (IOException ex) {
                    Logger.getLogger(Webhook.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                response.setContentType("application/json");
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("Invalid Access Token");
                } catch (IOException ex) {
                    Logger.getLogger(Webhook.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    private void receivedMessage(Messaging event) {
        String senderID = event.getSender().getId();
        String recipientID = event.getRecipient().getId();

        long timeOfMessage = event.getTimestamp();
        Message message = event.getMessage();

        System.out.println("Received message for user " + senderID + " and page " + recipientID + " at " + timeOfMessage + " with message: " + message.toString());

        String messageId = message.getMid();

        String messageText = null;
        Attachments messageAttachments;
        if (!message.getIsEcho()) {

            if (message.getText() != null) {
                messageText = message.getText();
                if (recipientID.equalsIgnoreCase("1694830117425781")) {
//                    sendTextMessageToSensex(senderID, "Hello welcome to sensex");
                } else if (messageText.equalsIgnoreCase("hi")) {
                    sendTextMessage(senderID, "hi, "+" welcomes you");
//                    sendCollection(senderID, getCollectionList());
//                    sendCalculator(senderID, messageText);
                } else if (messageText.contains("address")) {
//                    sendOrderDetails(senderID, messageText);
                } else {
                    sendTextMessage(senderID, "your result is: " + eval(messageText));
                }
            } else if (message.getAttachments() != null) {
                sendTextMessage(senderID, "attachment received");
                System.out.println(gson.toJson(message.getAttachments()));
            } else {
                sendTextMessage(senderID, "Something went wrong");
            }
//        if (!message.isNull("attachments")) {
//            messageAttachments = message.getString("attachments");
//        }

//        ScriptEngineManager mgr = new ScriptEngineManager();
//        ScriptEngine engine = mgr.getEngineByName("JavaScript");
//        Object total = null;
//        try {
//            System.out.println(engine.eval(messageText));
//            total = engine.eval(messageText);
//        } catch (ScriptException ex) {
//            Logger.getLogger(Webhook.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            if (messageText != null) {
//
//            } else {
//            if (!message.isNull("attachments")) {
//                messageAttachments = message.getJSONObject("attachments");
//            } else {
//                sendTextMessage(senderID, "Something went wrong");
//            }
//            }
        } else {
//            sendTextMessage(recipientID, "message received");
        }

    }

    private void sendTextMessage(String recipientID, String messageText) {

        TextMessageResponse messageData = new TextMessageResponse(new Recipient(recipientID), new SendMessage(messageText));

//        String messageData = "{recipient:{id:" + recipientID + "}, message:{text:" + messageText + "}}";
//        JSONObject messageData = new JSONObject();
//        JSONObject message = new JSONObject();
//        message.put("text", messageText);
//        messageData.put("recipient", recipient);
//        messageData.put("message", message);
        System.out.println(gson.toJson(messageData));

        service.sendTextMessageToUser(messageData, new Callback<Response>() {

            @Override
            public void failure(RetrofitError re) {
                System.err.println("something went wrong on facebook response");
            }

            @Override
            public void success(Response result, Response rspns) {
                System.out.println("facebook response: " + gson.toJson(rspns));
            }
        });

    }

    public ArrayList<Collection> getCollectionList() {

        MongoDatabase db = MongoProvider.getInstance();

//        MongoCollection<Product> products = db.getCollection("disneytoys.myshopify.com_products", Product.class);
        BasicDBObject document = new BasicDBObject();
        QueryBuilder qb = new QueryBuilder();
        qb.or(new QueryBuilder().put("title").notEquals("Frontpage").get(), new QueryBuilder().put("published_at").notEquals(null).get());
        document.putAll(qb.get());

        FindIterable<Document> coll = db.getCollection("disneytoys.myshopify.com_collection_new").find(and(ne("title", "Frontpage"), ne("published_at", null)));
//        FindIterable<Document> coll = db.getCollection("disneytoys.myshopify.com_collection").find(document);

//        FindIterable<Document> foundDocument = coll.find(and(ne("title", "Frontpage"), ne("published_at", null)));
        final Type collectionType = new TypeToken<ArrayList<Collection>>() {
        }.getType();

        final ArrayList<Collection> collection = new ArrayList<>();

        System.out.println();
        System.out.println();

        System.out.println("document: " + document);
        coll.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {

                collection.add(gson.fromJson(gson.toJson(document), Collection.class));

//                System.out.println("document: " + document);
//                ArrayList<Collection> c = gson.fromJson(gson.toJson(document.get("collections")), collectionType);
//                System.out.println("collection: " + c);
//                if (!c.isEmpty()) {
//                    collection.addAll(c);
//                }
//                collection.add(t);
            }
        });
        System.out.println();
        System.out.println();

        return collection;
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') {
                    nextChar();
                }
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) {
//                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) {
                        x += parseTerm(); // addition
                    } else if (eat('-')) {
                        x -= parseTerm(); // subtraction
                    } else {
                        return x;
                    }
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) {
                        x *= parseFactor(); // multiplication
                    } else if (eat('x')) {
                        x *= parseFactor(); // multiplication
                    } else if (eat('/')) {
                        x /= parseFactor(); // division
                    } else {
                        return x;
                    }
                }
            }

            double parseFactor() {
                if (eat('+')) {
                    return parseFactor(); // unary plus
                }
                if (eat('-')) {
                    return -parseFactor(); // unary minus
                }
                double x = 0;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') {
                        nextChar();
                    }
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') {
                        nextChar();
                    }
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) {
                        x = Math.sqrt(x);
                    } else if (func.equals("sin")) {
                        x = Math.sin(Math.toRadians(x));
                    } else if (func.equals("cos")) {
                        x = Math.cos(Math.toRadians(x));
                    } else if (func.equals("tan")) {
                        x = Math.tan(Math.toRadians(x));
                    } else {
//                        throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
//                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) {
                    x = Math.pow(x, parseFactor()); // exponentiation
                }
                return x;
            }
        }.parse();
    }

//    private void callSendApi(String messageData) throws Exception {
//
////        String url = "https://requestb.in/1g3689z1";
////        String url = "https://graph.facebook.com/v2.6/me/messages";
////        String url = "https://selfsolve.apple.com/wcResults.do";
////        String access_token = "?access_token=EAAYvnguYXe4BADnrdo6rsdsaQXbZAzLGWxenwEa5LZA7PiGjLdDWqsK5O7mrBX23ZCOtSdxaCZAmXrzJoIZAVF8ZACsY3xqqevA0Usu2cl3pXrvcNkZAsHekUGDZAhBFiNPp92yYKyJR5z2JjFPGvZCxhs0cO2MSJzZAneGdswKsw9fgZDZD";
//
//        HttpClient client = HttpClientBuilder.create().build();
//        url = url + access_token;
//        HttpPost post = new HttpPost(url);
//
//        // add header
//        post.setEntity(new StringEntity(messageData, "UTF8"));
//        post.setHeader("Content-type", "application/json");
////	post.setHeader("User-Agent", USER_AGENT);
////        post.setHeader("qs", access_token);
////        post.setHeader("json", messageData);
//
////	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
////	urlParameters.add(new BasicNameValuePair("qs", "{access_token: EAAYvnguYXe4BADHGRQJE3iVNHvRqqFwYCTbLS5XvjEbZCf7PkP1eSqQpPd7yebCZCUuHtZARk6bDJWvnqZCnaMYaZBe8YIFQK2580vwX0xtm45ZBrYs1Y7N8eiFgGZCGqHj6vDEeZB2ah3xZBWTTszPyiSdEQ19WrOlv7caaql7ZCsUQZDZD}"));
////	urlParameters.add(new BasicNameValuePair("json", messageData));
////	urlParameters.add(new BasicNameValuePair("locale", ""));
////	urlParameters.add(new BasicNameValuePair("caller", ""));
////	urlParameters.add(new BasicNameValuePair("num", "12345"));
////
////	post.setEntity((HttpEntity) urlParameters);
//        HttpResponse response = client.execute(post);
//
//        System.out.println("Response Code : "
//                + response.getStatusLine().getStatusCode() + "\t\t message: " + messageData);
//
//        BufferedReader rd = new BufferedReader(
//                new InputStreamReader(response.getEntity().getContent()));
//
//        StringBuffer result = new StringBuffer();
//        String line = "";
//        while ((line = rd.readLine()) != null) {
//            result.append(line);
//        }
//    }
//
//    private void sendCalculator(String recipientID, String messageText) {
//
//        JSONObject messageData = new JSONObject();
//        JSONObject recipient = new JSONObject();
//        recipient.put("id", recipientID);
//
//        JSONObject message = new JSONObject();
//
//        JSONObject attachment = new JSONObject();
//        attachment.put("type", "template");
//        JSONObject payload = new JSONObject();
//        payload.put("template_type", "generic");
//        JSONArray elements = new JSONArray();
//
//        for (int i = 0; i < 5; i++) {
//            JSONObject elementObject = new JSONObject();
//            elementObject.put("title", elementTitle[i]);
//            elementObject.put("subtitle", elementSubTitle[i]);
//            elementObject.put("item_url", elementItemURL[i]);
//            elementObject.put("image_url", elementImageURL[i]);
//
//            JSONArray buttons = new JSONArray();
//
//            JSONObject buttonObject = new JSONObject();
//            buttonObject.put("type", "web_url");
//            buttonObject.put("url", elementItemURL[i]);
//            buttonObject.put("title", "Check on Flipkart");
//            buttons.put(buttonObject);
//
//            JSONObject buttonPostBack = new JSONObject();
//            buttonPostBack.put("type", "postback");
//            buttonPostBack.put("payload", "buy " + i);
//            buttonPostBack.put("title", "Buy this item");
//            buttons.put(buttonPostBack);
//
//            elementObject.put("buttons", buttons);
//
//            elements.put(elementObject);
//        }
//
//        payload.put("elements", elements);
//
//        attachment.put("payload", payload);
//
//        message.put("attachment", attachment);
//
////        message.put("text", messageText);
//        messageData.put("recipient", recipient);
//        messageData.put("message", message);
//
//        System.out.println(messageData.toString());
//
//        try {
//            callSendApi(messageData.toString());
//        } catch (Exception ex) {
//            Logger.getLogger(Webhook.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
    private void receivedPostback(Messaging event) {
        String senderID = event.getSender().getId();
        String recipientID = event.getRecipient().getId();

        long timeOfPostBack = event.getTimestamp();
        String postbackMessage = event.getPostback().getPayload();
        System.out.println(postbackMessage);

        if (postbackMessage.contains("collectionId")) {
            sendProductList(senderID, getProducts(postbackMessage.split(":")[1]), postbackMessage.split(":")[1]);
//            sendTextMessage(senderID, "Postback was Called with: "+ postbackMessage);
        } else if (postbackMessage.contains("productID")) {
            sendVarientList(senderID, getVarients(postbackMessage.split(":")[1].split("@")[1]), 1, postbackMessage.split(":")[1]);
//            sendTextMessage(senderID, "Postback was Called with: "+ postbackMessage);
        } else if (postbackMessage.contains("varient")) {
            sendVarientList(senderID, getVarients(postbackMessage.split(":")[1].split("@")[1]), postbackMessage.split(":")[1].split("@").length - 1, postbackMessage.split(":")[1]);
//            sendTextMessage(senderID, "Postback was Called with: "+ postbackMessage);
        } else if (postbackMessage.contains("back")) {

            String[] choises = postbackMessage.split(":");

            if (choises[1].split("@").length == 2) {
                sendCollection(senderID, getCollectionList());
            } else if (choises[1].split("@").length == 3) {
                sendProductList(senderID, getProducts(choises[1]), choises[1]);
            } else {
                String[] selectedCategories = choises[1].split("@");
                String items = "";
                for (int i = 0; i < selectedCategories.length - 1; i++) {
                    items = items + selectedCategories[i] + "@";
                }
                sendVarientList(senderID, getVarients(choises[1].split("@")[1]), choises[1].split("@").length - 1, items.substring(0, items.length() - 1));
            }
//            sendVarientList(senderID, getVarients(choises[1].split("@")[1]), choises[1].split("@").length - 1, choises[1]);
//            sendTextMessage(senderID, "Postback was Called with: "+ postbackMessage);

        } else {
            sendTextMessage(senderID, "Postback was Called");
        }

//        if (postbackMessage.contains("buy")) {
//            sendTextMessage(senderID, "Enter your address for delivery:");
//        } else {
//            sendTextMessage(senderID, "Postback was Called");
//        }
    }
//    private void sendOrderDetails(String recipientID, String textMessage) {
//        JSONObject messageData = new JSONObject();
//        JSONObject recipient = new JSONObject();
//        recipient.put("id", recipientID);
//
//        JSONObject message = new JSONObject();
//
//        JSONObject attachment = new JSONObject();
//        attachment.put("type", "template");
//        JSONObject payload = new JSONObject();
//        payload.put("template_type", "receipt");
//        payload.put("recipient_name", "Amrish Patel");
//        payload.put("order_number", recipientID);
//        payload.put("currency", "INR");
//        payload.put("payment_method", "Visa 1234");
//        payload.put("timestamp", "1428444852");
//
//        JSONArray elements = new JSONArray();
//
////        for (int i = 0; i < 5; i++) {
//        JSONObject elementObject = new JSONObject();
//        elementObject.put("title", elementTitle[2]);
//        elementObject.put("subtitle", elementSubTitle[2]);
//        elementObject.put("quantity", 1);
//        elementObject.put("price", 100);
//        elementObject.put("currency", "INR");
//        elementObject.put("image_url", elementImageURL[2]);
//
////            elementObject.put("buttons", buttons);
//        elements.put(elementObject);
////        }
//
//        textMessage = textMessage.split(":")[1];
//
//        JSONObject street = new JSONObject();
//
//        street.put("street_1", textMessage);
//        street.put("street_2", "");
//        street.put("city", "Ahmedabad");
//        street.put("postal_code", "380028");
//        street.put("state", "gujarat");
//        street.put("country", "IN");
//
//        payload.put("address", street);
//
//        payload.put("summary", "{'subtotal': 100.00,'shipping_cost': 100.00,'total_tax': 28.00,'total_cost': 228.00}");
//
//        payload.put("elements", elements);
//
//        attachment.put("payload", payload);
//
//        message.put("attachment", attachment);
//
////        message.put("text", messageText);
//        messageData.put("recipient", recipient);
//        messageData.put("message", message);
//
//        System.out.println(messageData.toString());
//
//        try {
//            callSendApi(messageData.toString());
//        } catch (Exception ex) {
//            Logger.getLogger(Webhook.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    private void sendTextMessageToSensex(String recipientID, String messageText) {
//        JSONObject messageData = new JSONObject();
//        JSONObject recipient = new JSONObject();
//        recipient.put("id", recipientID);
//
//        JSONObject message = new JSONObject();
//        message.put("text", messageText);
//
//        messageData.put("recipient", recipient);
//        messageData.put("message", message);
//
//        System.out.println(messageData.toString());
//
//        try {
//            callSensexApi(messageData.toString());
//        } catch (Exception ex) {
//            Logger.getLogger(Webhook.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    private void callSensexApi(String messageData) throws Exception {
////        String url = "https://requestb.in/1g3689z1";
//        String url = "https://graph.facebook.com/v2.6/me/messages";
////        String url = "https://selfsolve.apple.com/wcResults.do";
//        String access_token = "?access_token=EAAYvnguYXe4BAP6jDZBo3eRyL43Aki7o6WBd7jLFsBP6RZALxr5PjQZBWtHNNwDti9aErga4pRdVaKlaZAwZCVAlvFeXXdURAqnZCFFWn9tD6Al0RYFTP0il3a8G2OOpUiHHYIb1ntZCYsAsIva1R46kRoIb5NchVv33EmQZBMdjZCwZDZD";
//
//        HttpClient client = HttpClientBuilder.create().build();
//        url = url + access_token;
//        HttpPost post = new HttpPost(url);
//
//        // add header
//        post.setEntity(new StringEntity(messageData, "UTF8"));
//        post.setHeader("Content-type", "application/json");
////	post.setHeader("User-Agent", USER_AGENT);
////        post.setHeader("qs", access_token);
////        post.setHeader("json", messageData);
//
////	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
////	urlParameters.add(new BasicNameValuePair("qs", "{access_token: EAAYvnguYXe4BADHGRQJE3iVNHvRqqFwYCTbLS5XvjEbZCf7PkP1eSqQpPd7yebCZCUuHtZARk6bDJWvnqZCnaMYaZBe8YIFQK2580vwX0xtm45ZBrYs1Y7N8eiFgGZCGqHj6vDEeZB2ah3xZBWTTszPyiSdEQ19WrOlv7caaql7ZCsUQZDZD}"));
////	urlParameters.add(new BasicNameValuePair("json", messageData));
////	urlParameters.add(new BasicNameValuePair("locale", ""));
////	urlParameters.add(new BasicNameValuePair("caller", ""));
////	urlParameters.add(new BasicNameValuePair("num", "12345"));
////
////	post.setEntity((HttpEntity) urlParameters);
//        HttpResponse response = client.execute(post);
//
//        System.out.println("Response Code : "
//                + response.getStatusLine().getStatusCode() + "\t\t message: " + messageData);
//
//        BufferedReader rd = new BufferedReader(
//                new InputStreamReader(response.getEntity().getContent()));
//
//        StringBuffer result = new StringBuffer();
//        String line = "";
//        while ((line = rd.readLine()) != null) {
//            result.append(line);
//        }
//    }

    private void sendCollection(String recepientID, ArrayList<Collection> collection) {

        SendAttachments attachment = new SendAttachments();

        attachment.setType("template");

        SendPayload payload = new SendPayload();

        ArrayList<Element> elements = new ArrayList<Element>();

        payload.setTemplateType("generic");

        for (int i = 0; i < collection.size(); i++) {

            if (collection.get(i).getPublishedAt() != null) {

                ArrayList<Button> button = new ArrayList<Button>();
                button.add(new PayloadButton("postback", "select " + collection.get(i).getTitle(), "collectionId:" + collection.get(i).getId()));
                button.add(new RedirectButton("web_url", "visit website", "https://www.mlveda.com"));

                Element element = new Element();
                if (collection.get(i).getImage() != null) {
                    element.setImage_url(collection.get(i).getImage().getSrc());
                }
                element.setButtons(button);
                element.setTitle(collection.get(i).getTitle());
                element.setSubtitle(collection.get(i).getBodyHtml());

                elements.add(element);

            }
        }

        payload.setElements(elements);

        attachment.setPayload(payload);

        System.out.println("message generated");

        SendAttachmentMessage messageData = new SendAttachmentMessage(new Recipient(recepientID), new SendAttachment(attachment));

        System.out.println(gson.toJson(messageData));

        service.sendAttachmentToUser(messageData, new Callback<Response>() {

            @Override
            public void failure(RetrofitError re) {
                System.err.println("something went wrong on facebook response");
            }

            @Override
            public void success(Response response, Response rspns) {
                System.out.println("facebook response: " + gson.toJson(rspns));
            }
        });

    }

    private void sendProductList(String recepientID, ArrayList<Product> products, String collectionID) {

        SendAttachments attachment = new SendAttachments();

        attachment.setType("template");

        SendPayload payload = new SendPayload();

        ArrayList<Element> elements = new ArrayList<Element>();

        payload.setTemplateType("generic");

        System.out.println("products: " + products.size());

        for (Product prod : products) {

            if (prod.getPublishedAt() != null) {

                System.out.println("product is published");

                ArrayList<Button> button = new ArrayList<Button>();
                button.add(new PayloadButton("postback", "select " + prod.getTitle(), "productID:" + collectionID + "@" + prod.getId()));
                button.add(new RedirectButton("postback", "back", "back"));

                Element element = new Element();
                if (prod.getImages().size() > 0) {
                    element.setImage_url(prod.getImages().get(0).getSrc());
                }
                element.setButtons(button);
                element.setTitle(prod.getTitle());
                element.setSubtitle(prod.getBodyHtml());

                elements.add(element);

            }
        }

        payload.setElements(elements);

        attachment.setPayload(payload);

        System.out.println("message generated");

        SendAttachmentMessage messageData = new SendAttachmentMessage(new Recipient(recepientID), new SendAttachment(attachment));

        System.out.println(gson.toJson(messageData));

        if (elements.size() == 0) {

            sendTextMessage(recepientID, "No Products to show");

        } else {

            service.sendAttachmentToUser(messageData, new Callback<Response>() {

                @Override
                public void failure(RetrofitError re) {
                    System.err.println("something went wrong on facebook response");
                }

                @Override
                public void success(Response response, Response rspns) {
                    System.out.println("facebook response: " + gson.toJson(rspns));
                }
            });
        }

    }

    private void sendVarientList(String recepientID, Product product, int position, String otherParams) {

        SendAttachments attachment = new SendAttachments();

        attachment.setType("template");

        SendPayload payload = new SendPayload();

        ArrayList<Element> elements = new ArrayList<Element>();

        payload.setTemplateType("generic");

//        System.out.println("products: " + products.size());
        System.out.println("product is published");

        System.out.println("option size: " + product.getOptions().size());

        for (Option option : product.getOptions()) {
            System.out.println("position : " + option.getPosition() + " selected: " + position);
            if (option.getPosition() == position) {
                System.out.println("varient size: " + option.getValues().size());
                for (String value : option.getValues()) {
                    ArrayList<Button> button = new ArrayList<Button>();
                    button.add(new PayloadButton("postback", "select " + value + " " + option.getName(), "varients:" + otherParams + "@" + value));
                    button.add(new RedirectButton("postback", "back", "back:" + otherParams));

                    Element element = new Element();
                    if (product.getImages().size() > 0) {
                        element.setImage_url(product.getImages().get(0).getSrc());
                    }
                    element.setButtons(button);
                    element.setTitle(value + " " + product.getTitle());
                    element.setSubtitle("");

                    elements.add(element);
                }
                break;
            }
        }

        payload.setElements(elements);

        attachment.setPayload(payload);

        System.out.println("message generated");

        SendAttachmentMessage messageData = new SendAttachmentMessage(new Recipient(recepientID), new SendAttachment(attachment));

        System.out.println(gson.toJson(messageData));

        if (elements.size() == 0) {

            sendTextMessage(recepientID, "No more varients to show");
            sendTextMessage(recepientID, otherParams);

        } else {
            service.sendAttachmentToUser(messageData, new Callback<Response>() {

                @Override
                public void failure(RetrofitError re) {
                    System.err.println("something went wrong on facebook response");
                }

                @Override
                public void success(Response response, Response rspns) {
                    System.out.println("facebook response: " + gson.toJson(rspns));
                }
            });
        }
    }

    private ArrayList<Product> getProducts(String collectionID) {
        MongoDatabase db = MongoProvider.getInstance();

//        MongoCollection<Product> products = db.getCollection("disneytoys.myshopify.com_products", Product.class);
        System.out.println("collectionID: " + collectionID);

        FindIterable<Document> coll = db.getCollection("disneytoys.myshopify.com_collection_mapping_new").find(eq("collection_id", Long.parseLong(collectionID)));

        final Type collectionType = new TypeToken<ArrayList<ProductCollectionMapping>>() {
        }.getType();
        final Type productType = new TypeToken<ArrayList<Product>>() {
        }.getType();

        final ArrayList<ProductCollectionMapping> map = new ArrayList<>();
        final ArrayList<Product> products = new ArrayList<>();

        System.out.println();
        System.out.println("coll: " + coll.first());
        System.out.println();

        coll.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                map.add(gson.fromJson(gson.toJson(document), ProductCollectionMapping.class
                ));
//                System.out.println("document is: " + gson.toJson(document));
//                ArrayList<ProductCollectionMapping> c = gson.fromJson(gson.toJson(document.get("collects")), collectionType);
//                System.out.println(c);
//                map.addAll(c);
//                collection.add(t);
            }
        });

        System.out.println();
        System.out.println("map: " + map.size());
        System.out.println();

        for (ProductCollectionMapping pcm : map) {
            System.out.println("productID: " + pcm.getProductId());
            FindIterable<Document> prod = db.getCollection("disneytoys.myshopify.com_products_new").find(eq("id", pcm.getProductId()));
            System.out.println();
            System.out.println("prod: " + prod.toString());
            System.out.println();
            prod.forEach(new Block<Document>() {
                @Override
                public void apply(Document document) {
                    Product prod = gson.fromJson(gson.toJson(document), Product.class);
                    if (!products.contains(prod)) {
                        products.add(prod);
                    }
//                    System.out.println(gson.toJson(document));
//                    ArrayList<Product> c = gson.fromJson(gson.toJson(document.get("collects")), productType);
//                    System.out.println(c);
//                    products.addAll(c);
//                collection.add(t);
                }
            });
        }

        System.out.println();
        System.out.println();

        System.out.println("productlist: " + gson.toJson(products));

        return products;
    }

    private Product getVarients(String productID) {
        MongoDatabase db = MongoProvider.getInstance();

//        MongoCollection<Product> products = db.getCollection("disneytoys.myshopify.com_products", Product.class);
        System.out.println("collectionID: " + productID);

        System.out.println("productID: " + productID);
        FindIterable<Document> prod = db.getCollection("disneytoys.myshopify.com_products_new").find(eq("id", Long.parseLong(productID)));
        System.out.println();
        System.out.println("prod: " + prod.toString());
        System.out.println();

        Product product = gson.fromJson(gson.toJson(prod.first()), Product.class);

//        prod.forEach(new Block<Document>() {
//            @Override
//            public void apply(Document document) {
//                Product prod = gson.fromJson(gson.toJson(document), Product.class
//                );
//                if (!products.contains(prod)) {
//                    products.add(prod);
//                }
////                    System.out.println(gson.toJson(document));
////                    ArrayList<Product> c = gson.fromJson(gson.toJson(document.get("collects")), productType);
////                    System.out.println(c);
////                    products.addAll(c);
////                collection.add(t);
//            }
//        });
        System.out.println();
        System.out.println();

        System.out.println("productlist: " + gson.toJson(product));

        return product;
    }
}
