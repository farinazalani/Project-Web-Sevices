package com.bmi;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bmiCalculator") // This annotation maps the servlet to the URL /bmiCalculator
public class bmi extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Retrieve weight and height from the request parameters
            String weightStr = request.getParameter("weight");
            String heightStr = request.getParameter("height");

            // Basic validation
            if (weightStr == null || weightStr.isEmpty() || heightStr == null || heightStr.isEmpty()) {
                out.println("<html><body>");
                out.println("<h1>Error: Please provide both weight and height.</h1>");
                out.println("<p><a href=\"index.html\">Go back</a></p>"); // Assuming your HTML form is in index.html
                out.println("</body></html>");
                return;
            }

            double weight = Double.parseDouble(weightStr);
            double heightCm = Double.parseDouble(heightStr);

            // Convert height from cm to meters for BMI calculation
            double heightM = heightCm / 100.0;

            // Calculate BMI
            double bmiValue = weight / (heightM * heightM);

            // Display the result
            out.println("<!DOCTYPE html>"); // Added HTML5 doctype for modern rendering
            out.println("<html><head><title>BMI Result</title>");
            out.println("<meta charset=\"UTF-8\">"); // Specify character encoding
            out.println("<style>");
            // --- EMBEDDED CSS STARTS HERE ---
            out.println("body {");
            out.println("    font-family: Arial, sans-serif;");
            out.println("    margin: 0;");
            out.println("    display: flex;");
            out.println("    min-height: 100vh;"); // Ensure body takes at least full viewport height
            out.println("    background-color: #f4f4f4;"); // Light grey background for the page
            out.println("    color: #333;"); // Default text color
            out.println("    justify-content: center;"); // Center content horizontally
            out.println("    align-items: center;"); // Center content vertically
            out.println("}");
            out.println(".main-content {");
            out.println("    padding: 30px;");
            out.println("    background-color: #fff;"); // White background for the content area
            out.println("    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);"); // Subtle shadow for depth
            out.println("    border-radius: 8px;"); // Slightly rounded corners
            out.println("    width: 450px;"); // Fixed width for the content area
            out.println("    text-align: center;"); // Center text within the content area
            out.println("}");
            // The sidebar styles are included here but won't apply unless you add the HTML structure for a sidebar.
            out.println(".sidebar {");
            out.println("    width: 250px;");
            out.println("    background-color: #f0f0f0;");
            out.println("    padding: 20px;");
            out.println("    border-left: 1px solid #ccc;");
            out.println("    box-sizing: border-box;");
            out.println("}");
            out.println(".sidebar h3 {");
            out.println("    color: #555;");
            out.println("    margin-top: 0;");
            out.println("    border-bottom: 1px solid #ddd;");
            out.println("    padding-bottom: 10px;");
            out.println("    margin-bottom: 15px;");
            out.println("}");
            out.println(".sidebar ul {");
            out.println("    list-style-type: none;");
            out.println("    padding: 0;");
            out.println("    margin: 0;");
            out.println("}");
            out.println(".sidebar li {");
            out.println("    margin-bottom: 10px;");
            out.println("}");
            out.println(".sidebar a {");
            out.println("    text-decoration: none;");
            out.println("    color: #007bff;");
            out.println("    transition: color 0.3s ease;");
            out.println("}");
            out.println(".sidebar a:hover {");
            out.println("    text-decoration: underline;");
            out.println("    color: #0056b3;");
            out.println("}");
            out.println("h1 {");
            out.println("    color: #0056b3;"); // A nice shade of blue for headings
            out.println("    margin-bottom: 25px;");
            out.println("    border-bottom: 2px solid #eee;");
            out.println("    padding-bottom: 10px;");
            out.println("}");
            out.println("p {");
            out.println("    font-size: 1.1em;");
            out.println("    line-height: 1.6;");
            out.println("    margin-bottom: 10px;");
            out.println("}");
            out.println("p strong {");
            out.println("    color: #007bff;");
            out.println("    font-size: 1.2em;");
            out.println("}");
            out.println(".bmi-category {");
            out.println("    font-weight: bold;");
            out.println("    font-size: 1.2em;");
            out.println("    padding: 10px 15px;");
            out.println("    border-radius: 5px;");
            out.println("    display: inline-block;"); // Allows padding and background to apply correctly
            out.println("    margin-top: 15px;");
            out.println("    margin-bottom: 20px;");
            out.println("}");
            out.println(".underweight {");
            out.println("    color: #FFA500;"); // Orange
            out.println("    background-color: #fff3e0;"); // Light orange background
            out.println("    border: 1px solid #ffcc80;");
            out.println("}");
            out.println(".normal {");
            out.println("    color: #28a745;"); // Green
            out.println("    background-color: #e6ffed;"); // Light green background
            out.println("    border: 1px solid #80ed99;");
            out.println("}");
            out.println(".overweight {");
            out.println("    color: #FFD700;"); // Gold
            out.println("    background-color: #fffde7;"); // Light gold background
            out.println("    border: 1px solid #ffe082;");
            out.println("}");
            out.println(".obese {");
            out.println("    color: #DC3545;"); // Red
            out.println("    background-color: #ffe6e6;"); // Light red background
            out.println("    border: 1px solid #ff8080;");
            out.println("}");
            out.println("ul {");
            out.println("    list-style-type: none;"); // Removed default bullet points
            out.println("    padding: 0;");
            out.println("    margin: 20px auto;"); // Centered and added margin
            out.println("    width: fit-content;"); // Make list width fit content
            out.println("}");
            out.println("ul li {");
            out.println("    margin-bottom: 8px;");
            out.println("    text-align: left;"); // Align list items to the left
            out.println("    padding-left: 1.5em;"); /* space for custom bullet */
            out.println("    position: relative;"); /* for custom bullet */
            out.println("}");
            out.println("ul li::before {");
            out.println("    content: '\\2022';"); /* Unicode for a bullet point */
            out.println("    color: #007bff;"); /* Blue color for bullets */
            out.println("    font-weight: bold;");
            out.println("    display: inline-block;");
            out.println("    width: 1em;");
            out.println("    margin-left: -1.5em;");
            out.println("    position: absolute;");
            out.println("}");
            out.println("a {");
            out.println("    color: #007bff;");
            out.println("    text-decoration: none;");
            out.println("    transition: color 0.3s ease;");
            out.println("    font-size: 1.1em;");
            out.println("    margin-top: 20px;");
            out.println("    display: inline-block;");
            out.println("}");
            out.println("a:hover {");
            out.println("    text-decoration: underline;");
            out.println("    color: #0056b3;");
            out.println("}");
            // --- EMBEDDED CSS ENDS HERE ---
            out.println("</style>");
            out.println("</head><body>");

            // Main Content Area
            out.println("<div class=\"main-content\">");
            out.println("<h1>Your BMI Result</h1>");
            out.printf("<p>Weight: %.2f kg</p>", weight);
            out.printf("<p>Height: %.2f cm</p>", heightCm);
            out.printf("<p>Your BMI is: <strong>%.2f</strong></p>", bmiValue);

            String bmiCategory = "";
            String categoryClass = "";

            if (bmiValue < 18.5) {
                bmiCategory = "Underweight";
                categoryClass = "underweight";
            } else if (bmiValue >= 18.5 && bmiValue < 24.9) {
                bmiCategory = "Normal weight";
                categoryClass = "normal";
            } else if (bmiValue >= 25 && bmiValue < 29.9) {
                bmiCategory = "Overweight";
                categoryClass = "overweight";
            } else {
                bmiCategory = "Obese";
                categoryClass = "obese";
            }
            out.println("<p class=\"bmi-category " + categoryClass + "\">Category: " + bmiCategory + "</p>");

            out.println("<p>BMI Categories:</p>");
            out.println("<ul>");
            out.println("<li>Underweight = &lt; 18.5</li>"); // Use &lt; for '<' in HTML
            out.println("<li>Normal weight = 18.5 to 24.9</li>");
            out.println("<li>Overweight = 25 to 29.9</li>");
            out.println("<li>Obesity = 30 or greater</li>");
            out.println("</ul>");

            out.println("<p><a href=\"index.html\">Back to User Information</a></p>"); // Link back to the form
            out.println("</div>"); // Close main-content div

            out.println("</body></html>");

        } catch (NumberFormatException e) {
            out.println("<!DOCTYPE html>"); // Added HTML5 doctype
            out.println("<html><head><title>Error</title>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; min-height: 100vh; background-color: #f4f4f4; margin: 0; }");
            out.println("h1 { color: #DC3545; text-align: center; }"); // Red color for error
            out.println("p { text-align: center; }");
            out.println("a { color: #007bff; text-decoration: none; }");
            out.println("a:hover { text-decoration: underline; }");
            out.println(".error-container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.1); width: 400px; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<div class=\"error-container\">");
            out.println("<h1>Error: Invalid input. Please enter numeric values for weight and height.</h1>");
            out.println("<p><a href=\"index.html\">Go back to Calculator</a></p>");
            out.println("</div>");
            out.println("</body></html>");
        } finally {
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>"); // Added HTML5 doctype
        out.println("<html><head><title>BMI Calculator</title>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; min-height: 100vh; background-color: #f4f4f4; margin: 0; }");
        out.println("h1 { color: #0056b3; text-align: center; }");
        out.println("p { text-align: center; }");
        out.println("a { color: #007bff; text-decoration: none; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println(".message-container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.1); width: 400px; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class=\"message-container\">");
        out.println("<h1>Please use the BMI Calculator form to submit data.</h1>");
        out.println("<p><a href=\"index.html\">Go to BMI Calculator</a></p>");
        out.println("</div>");
        out.println("</body></html>");
        out.close();
    }
}