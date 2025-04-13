/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Asus
 */
@WebServlet(name = "GetDeliveryStatus", urlPatterns = {"/GetDeliveryStatus"})
public class GetDeliveryStatus extends HttpServlet {

   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String orderID = request.getParameter("orderID"); 
        String deliveryDate = request.getParameter("deliveryDate"); 
        String productName = request.getParameter("productName");
        String status = request.getParameter("OrderStatus"); 
        String courier = request.getParameter("corier");
        String DelPerson = request.getParameter("Delivery Person"); 
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        
        out.println("<body>");
        out.println("<h2>Your Delivery Status:<h2>");
        out.println("Order ID:" + orderID + "<br>");
        out.println("Product List " + productName + "<br>");
        out.println("Total Price:" + totalPrice + "<br>");
        out.println("Delivery Date:" + deliveryDate + "<br>");
        out.println("Status:" + status + "<br>");
        out.println("Courier:" + courier + "<br>");
        out.println("Delivery Person" + DelPerson + "<br>");
        out.println("</body>");
        
        
    }

   

}

