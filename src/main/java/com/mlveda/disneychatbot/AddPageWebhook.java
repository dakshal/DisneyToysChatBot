/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlveda.disneychatbot;

import com.google.gson.Gson;
import static com.mlveda.disneychatbot.Webhook.getBody;
import com.mlveda.disneychatbot.config.ApiService;
import com.mlveda.disneychatbot.config.RestClient;
import com.mlveda.disneychatbot.database.MongoProvider;
import com.mlveda.disneychatbot.models.PageArray;
import com.mlveda.disneychatbot.models.PagesInsertData;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.bson.Document;

/**
 *
 * @author incredible
 */
@WebServlet(name = "AddPageWebhook", urlPatterns = {"/addPageWebhook"})
public class AddPageWebhook extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        String requestParams = gson.toJson(request.getQueryString());
//        String requestParams = request.getHeader("data");
        String requestParams = getBody(request);

        System.out.println(requestParams);

        PagesInsertData installedPage = gson.fromJson(requestParams, PagesInsertData.class);
        PageArray body = gson.fromJson(requestParams, PageArray.class);

        System.out.println();
        System.out.println(body);
        System.out.println();

        HttpSession session = request.getSession(true);
        int responsemsg;
        response.setContentType("application/json");

//        if (session == null) {
//            responsemsg = -2;   //Session Expired
////            jsonresponse.put("Message", responsemsg);
//            try (PrintWriter out = response.getWriter()) {
//                out.print("{'message' : " + responsemsg + "}");
//                out.close();
//            }
//            return;             //break flow
//        }

        String storename = "testing";

        try {
//            storename = session.getAttribute("shop").toString();//request.getParameter("storename");
        } catch (Exception e) {
//            PrintWriter out = response.getWriter();
//            out.print("{\"success\" : false}");
//            out.flush();
//            return;
        }
        if(storename.isEmpty()){
//            PrintWriter out = response.getWriter();
//            out.print("{\"success\" : false}");
//            out.flush();
//            return;
        }
        if (request.getMethod().equals("POST")) {

//            System.out.println(request.getQueryString());
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(getPagesData(body, storename)));
            out.flush();

        } else {

//            PrintWriter out = response.getWriter();
//            out.print("{ 'success' : false}");
//            out.flush();
//            return;
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

    private PageArray getPagesData(PageArray pages, String shopName) {

        MongoDatabase db = MongoProvider.getInstance();

        FindIterable<Document> collection = db.getCollection("facebook_pages").find(eq("shop_name", shopName));
        final ArrayList<PagesInsertData> pageList = new ArrayList<>();

        System.out.println();
        System.out.println();

//        System.out.println("document: " + document);
        collection.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {

                pageList.add(gson.fromJson(gson.toJson(document), PagesInsertData.class));

            }
        });
//        BasicDBObject document = new BasicDBObject();
//        QueryBuilder qb = new QueryBuilder().put("shop_name").is(shopName);
////        qb.or(new QueryBuilder().put("shop_name").is(shopName).get(), new QueryBuilder().put("published_at").notEquals(null).get());
//        document.putAll(qb.get());

        // convert JSON to DBObject directly
//            Document document = new Document("array", JSON.parse(gson.toJson(pages.getData())), new JSONCallback());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

        ArrayList<PagesInsertData> responseObject = new ArrayList<>();

        if (pages.getData().isEmpty()) {
            return new PageArray(responseObject, false);
        }

        for (PagesInsertData page : pages.getData()) {
        boolean found = false;

            page.setCreatedAt(format.format(Calendar.getInstance().getTime()));
            page.setUpdatedAt(format.format(Calendar.getInstance().getTime()));
            page.setShopName(shopName);

            Document document = Document.parse(gson.toJson(page));

            for (PagesInsertData facebookPage : pageList) {

                System.out.println("facebook id: " + facebookPage.getId() + " page id: " + page.getId());
                if (facebookPage.getId().equalsIgnoreCase(page.getId())) {
                    System.out.println("facebook id: " + facebookPage.getId() + " page id: " + page.getId());
                    page.setInstalled(facebookPage.isInstalled());
                    page.setCreatedAt(facebookPage.getCreatedAt());

                    document = Document.parse(gson.toJson(page));
                    found = true;
                    break;
                }

            }

            if (found) {
                found = false;
                db.getCollection("facebook_pages").updateOne(new Document("id", page.getId()), new Document("$set", document));
            } else {
                db.getCollection("facebook_pages").insertOne(document);
            }

            responseObject.add(page);

//            collection.insertOne(document);
        }

        return new PageArray(responseObject, true);

    }

}
