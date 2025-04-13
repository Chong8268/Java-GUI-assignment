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
@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"})
public class GetOrderDetails extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            String OrderID = request.getParameter("OrderID");
            String Product = request.getParameter("Product");
            int quantity =  Integer.parseInt(request.getParameter("quantitiy"));
            double price =  Double.parseDouble(request.getParameter("price"));
            double shipFee = Double.parseDouble(request.getParameter("shipingFee"));
            double totalPrice = Double.parseDouble(request.getParameter("Total Price"));
            double SST = Double.parseDouble(request.getParameter("SST"));
            
            // Display the Product detials 
            
            out.println("<body>");
            out.println("Here's Your Order Details Listed:");
            out.println("Order ID :" + OrderID + "<br>");
            out.println("Product:" + Product + "Quantity:" + quantity + "x" ); 
            out.println("Price:" + "RM" +(price * quantity) + "<br>");
            out.println("SST(6% Tax):" + "RM" +(totalPrice * SST) + "<br>");
            out.println("Shipping Fee:" + "RM" + shipFee + "<br>");
            out.println("Total Price:" + "RM" + (totalPrice  + shipFee + SST) + "<br>");
            out.println("</body>");
           
    }}

    



