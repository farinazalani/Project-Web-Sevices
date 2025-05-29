package com.bmi;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;


public class bmi extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/PersonalHealthServices/bmiService.wsdl")
    private BmiService_Service service;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Read input values from the form
            double height = Double.parseDouble(request.getParameter("height")); // in cm
            double weight = Double.parseDouble(request.getParameter("weight")); // in kg

            // Call the SOAP Web Service method
            double result = calculateBMI(weight, height);

            // Output result
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>BMI Result</title>");
            out.println("<style>");
            out.println("body { background-color: #f2f2f2; text-align: center; padding: 50px; }");
            out.println("h1 { color: #444; }");
            out.println("form { background-color: white; display: inline-block; padding: 100px; border-radius: 12px; box-shadow: 0 0 15px rgba(0,0,0,0.1); }");
            out.println("p { font-size: 18px; margin: 10px 0; }");
            out.println("strong { color: #007BFF; font-size: 20px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet bmi at " + request.getContextPath() + "</h1>");
            out.println("<form>");
            out.println("<h2>BMI Calculator Result</h2>");
            out.println("<p>Height: " + height + " cm</p>");
            out.println("<p>Weight: " + weight + " kg</p>");
            out.println("<p><strong>Your BMI is: " + String.format("%.2f", result) + "</strong></p>");
            out.println("<a href=\"index.html\" style=\"text-decoration:none;\"><button type=\"button\" style=\"padding:10px 20px; margin-top:30px; font-size:16px; background-color:#007BFF; color:white; border:none; border-radius:5px; cursor:pointer;\">Back</button></a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "BMI Calculator Servlet";
    }

    private double calculateBMI(double weight, double height) {
        // Connect to the web service
        BmiService port = service.getBmiServicePort();
        return port.calculateBMI(weight, height); // web service handles calculation
    }
}
