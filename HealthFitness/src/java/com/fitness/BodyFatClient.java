package com.fitness;

import com.fitness.wsclient.BodyFatCalculator;
import com.fitness.wsclient.HealthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BodyFatClient extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            // Read inputs
            String gender = request.getParameter("gender");
            double weight = Double.parseDouble(request.getParameter("weight"));
            double height = Double.parseDouble(request.getParameter("height"));
            double waist = Double.parseDouble(request.getParameter("waist"));
            double neck = Double.parseDouble(request.getParameter("neck"));
            double hip = 0.0;
            if ("Female".equalsIgnoreCase(gender)) {
                hip = Double.parseDouble(request.getParameter("hip"));
            }

            // Create service and get port
            HealthService service = new HealthService();
            BodyFatCalculator calculator = service.getBodyFatCalculatorPort();

            // Call the web service operation
           String report = calculator.generateBodyFatReport(gender, weight, height, waist, neck, hip);

        // Parse the result to extract only needed data
        String[] lines = report.split("\n");
        String bodyFatLine = "", categoryLine = "";
        for (String line : lines) {
            if (line.startsWith("Body Fat:")) {
                bodyFatLine = line.replace("Body Fat:", "").trim();
            } else if (line.startsWith("Category:")) {
                categoryLine = line.replace("Category:", "").trim();
            }
        }
        String combinedReport = "Body Fat: " + bodyFatLine + " - Category: " + categoryLine;

        // Output
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Body Fat Report</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f5f5f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
        out.println(".card { background: white; border-radius: 10px; padding: 30px; width: 350px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2); text-align: center; }");
        out.println(".card h2 { margin-bottom: 20px; font-size: 22px; color: #2c3e50; }");
        out.println(".card p { font-size: 16px; margin: 8px 0; }");
        out.println(".body-fat { color: red; font-weight: bold; margin-top: 15px; }");
        out.println(".line { margin: 10px 0; border-top: 1px dashed #333; width: 80%; margin-left: auto; margin-right: auto; }");
        out.println(".back-btn { display: inline-block; margin-top: 20px; padding: 10px 20px; background-color: #3498db; color: white; border: none; border-radius: 5px; text-decoration: none; transition: background-color 0.3s ease; }");
        out.println(".back-btn:hover { background-color: #2980b9; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='card'>");
        out.println("<h2>----- Body Fat Report -----</h2>");
        out.println("<p><strong>Your Details:</strong></p>");
        out.println("<p><b>Gender:</b> " + gender + "</p>");
        out.println("<p><b>Weight:</b> " + weight + " kg</p>");
        out.println("<p><b>Height:</b> " + height + " cm</p>");
        out.println("<p class='body-fat'>" + combinedReport + "</p>");
        out.println("<div class='line'></div>");
        out.println("<a href='bodyFat.html' class='back-btn'>Calculate Again</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error processing request: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
