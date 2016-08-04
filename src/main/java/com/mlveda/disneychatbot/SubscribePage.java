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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
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
@WebServlet(name = "SubscribePage", urlPatterns = {"/subscribepage"})
public class SubscribePage extends HttpServlet {

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
        String requestParams = getBody(request);

        System.out.println(gson.toJson(requestParams));

        PagesInsertData installedPage = gson.fromJson(requestParams, PagesInsertData.class);
        PageArray body = gson.fromJson(requestParams, PageArray.class);

        System.out.println();
        System.out.println(gson.toJson(body));
        System.out.println();

        HttpSession session = request.getSession(true);
        int responsemsg;
        response.setContentType("application/json");

//        if (session == null) {
//            responsemsg = -2;   //Session Expired
////            jsonresponse.put("Message", responsemsg);
//            try (PrintWriter out = response.getWriter()) {
//                out.print("{\"message\" : " + responsemsg + "}");
//                out.close();
//            }
//            return;             //break flow
//        }

        String storename = "testing";
//        String storename = session.getAttribute("shop").toString();//request.getParameter("storename");
        if (request.getMethod().equals("POST")) {

//            System.out.println(request.getQueryString());
            PrintWriter out = response.getWriter();
            out.print("{ \"success\" : " + addPagesToDB(installedPage, storename) + "}");
            out.flush();
        } else {

            PrintWriter out = response.getWriter();
            out.print("{ \"success\" : false}");
            out.flush();
        }

    }

    private boolean addPagesToDB(PagesInsertData pages, String shopName) {
        MongoDatabase db = MongoProvider.getInstance();

        FindIterable<Document> collection = db.getCollection("facebook_pages").find(and(eq("shop_name", shopName), eq("id", pages.getId())));
//        final ArrayList<PagesInsertData> pageList = new ArrayList<>();

        System.out.println();
        System.out.println();

        PagesInsertData facebookPage = gson.fromJson(gson.toJson(collection.first()), PagesInsertData.class);

        boolean found = false;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

        ArrayList<PagesInsertData> responseObject = new ArrayList<>();

        pages.setCreatedAt(format.format(Calendar.getInstance().getTime()));
        pages.setUpdatedAt(format.format(Calendar.getInstance().getTime()));
        pages.setShopName(shopName);

        Document document = Document.parse(gson.toJson(pages));

        if (facebookPage.getId().equalsIgnoreCase(pages.getId())) {
            pages.setInstalled(facebookPage.isInstalled());
            pages.setCreatedAt(facebookPage.getCreatedAt());

            document = Document.parse(gson.toJson(pages));
            found = true;
        }

        if (found) {
            found = false;
            db.getCollection("facebook_pages").updateOne(new Document("id", pages.getId()), new Document("$set", document));
        } else {
            db.getCollection("facebook_pages").insertOne(document);
        }

        System.out.println("Done");

        return true;

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

}
