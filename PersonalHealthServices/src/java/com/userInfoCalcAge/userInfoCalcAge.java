/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userInfoCalcAge;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
public class userInfoCalcAge extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            String name = request.getParameter("name");
            String ic = request.getParameter("ic");
            String gender = request.getParameter("gender");
            double weight = Double.parseDouble(request.getParameter("weight"));
            double height = Double.parseDouble(request.getParameter("height"));

            int age = calculateAge(ic);
        
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet userInfoCalcAge</title>");
            out.println("<style>");
            out.println("body { background-color: #f2f2f2; text-align: center; padding: 50px; }");
            out.println("h1 { color: #444; }");
            out.println("form { background-color: white; display: inline-block; padding-bottom: 20px; padding-left: 100px; padding-right: 100px;  border-radius: 12px; box-shadow: 0 0 15px rgba(0,0,0,0.1); }");
            out.println("p { font-size: 18px; margin: 10px 0; }");
            out.println("strong { color: #007BFF; font-size: 20px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet userInfoCalcAge at " + request.getContextPath() + "</h1>");
            out.println("<form>");
            out.println("<div style='max-width: 600px; margin: 10px; padding: 20px;'>");
            out.println("<h2><b>User Info Summary<b></h2>");
            out.println("<p><strong>Name:</strong> " + name + "</p>");
            out.println("<p><strong>IC:</strong> " + ic + "</p>");
            out.println("<p><strong>Gender:</strong> " + gender + "</p>");
            out.println("<p><strong>Weight:</strong> " + weight + " kg</p>");
            out.println("<p><strong>Height:</strong> " + height + " cm</p>");
            out.println("<p><strong>Age:</strong> " + age + " years</p>");
            out.println("<a href=\"index.html\" style=\"text-decoration:none;\"><button type=\"button\" style=\"padding:10px 20px; margin-top:30px; font-size:16px; background-color:#007BFF; color:white; border:none; border-radius:5px; cursor:pointer;\">Back</button></a>");
            out.println("<a href=\"dashboard.html\" style=\"text-decoration:none;\"><button type=\"button\" style=\"padding:10px 20px; margin-top:30px; font-size:16px; background-color:#007BFF; color:white; border:none; border-radius:5px; cursor:pointer;\">Next</button></a>");
            out.println("</div>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
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

    public int calculateAge(String ic) {
        int year = Integer.parseInt(ic.substring(0, 2)); // get YY
        int currentYear = java.time.LocalDate.now().getYear();
        year += (year > 30) ? 1900 : 2000; // assumes ICs > 30 belong to 1900s
        return currentYear - year;
    }
}
