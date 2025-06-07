package com.bodyfat; // Your package name (changed from com.bmi)

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bodyFatCalculator") // Maps this servlet to the URL /bodyFatCalculator
public class bodyfat extends HttpServlet { // Class name 'bodyfat' matches file name

    private static final long serialVersionUID = 1L;

    public bodyfat() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Set character encoding for HTML output
        PrintWriter out = response.getWriter();

        try {
            // Retrieve parameters from the request. Ensure these names match your HTML form's 'name' attributes.
            String genderParam = request.getParameter("bf-gender"); // e.g., 'male' or 'female'
            String weightStr = request.getParameter("bf-weight"); // Weight in kg
            String heightStr = request.getParameter("bf-height"); // Height in cm
            String neckStr = request.getParameter("bf-neck");   // Neck circumference in cm
            String waistStr = request.getParameter("bf-waist"); // Waist circumference in cm
            String hipStr = request.getParameter("bf-hip");     // Hip circumference in cm (required for females)

            // --- Input Validation: Check for null or empty strings for all *essential* fields ---
            if (genderParam == null || genderParam.isEmpty() ||
                weightStr == null || weightStr.isEmpty() ||
                heightStr == null || heightStr.isEmpty() ||
                neckStr == null || neckStr.isEmpty() ||
                waistStr == null || waistStr.isEmpty()) {

                displayError(out, "All required fields must be filled. Please go back and provide valid inputs.");
                return;
            }

            // --- Parse values and handle NumberFormatException for numeric inputs ---
            double weight; // in kg
            double height; // in cm
            double neck;   // in cm
            double waist;  // in cm
            double hip = 0.0; // Initialize hip to 0.0; will be updated for females

            try {
                weight = Double.parseDouble(weightStr);
                height = Double.parseDouble(heightStr);
                neck = Double.parseDouble(neckStr);
                waist = Double.parseDouble(waistStr);

                // Hip circumference is conditionally required for females
                if ("female".equalsIgnoreCase(genderParam)) {
                    if (hipStr == null || hipStr.isEmpty()) {
                        displayError(out, "For females, Hip Circumference is a required field.");
                        return;
                    }
                    hip = Double.parseDouble(hipStr);
                }
            } catch (NumberFormatException e) {
                // If any parsing fails, it means non-numeric input was provided
                displayError(out, "Invalid numeric input. Please enter numbers for all measurements.");
                return;
            }

            // --- Convert metric inputs (cm, kg) to imperial (inches, lbs) for the U.S. Navy Method ---
            // The U.S. Navy method typically uses inches and pounds.
            // 1 cm = 0.393701 inches
            // 1 kg = 2.20462 pounds (though weight isn't directly used in the BF% formula, good to note for context)
            
            double heightIn = height * 0.393701;
            double neckIn = neck * 0.393701;
            double waistIn = waist * 0.393701;
            double hipIn = hip * 0.393701; // Will be 0 for males, effectively ignored in male formula

            double bodyFatPercentage = 0.0;
            String category = "";
            String calculationError = null; // To capture specific issues during calculation

            // --- Apply U.S. Navy Body Fat Percentage Formulas ---
            if ("male".equalsIgnoreCase(genderParam)) {
                // Male Formula: BF% = 495 / (1.0324 - 0.19077 * log10(waist - neck) + 0.15456 * log10(height)) - 450
                
                // Critical check to prevent Math.log10(non-positive number) errors
                if (heightIn <= 0 || (waistIn - neckIn) <= 0) {
                    calculationError = "Invalid measurements for male calculation: Height must be positive, and Waist must be greater than Neck circumference.";
                } else {
                    double logWaistNeckDiff = Math.log10(waistIn - neckIn);
                    double logHeight = Math.log10(heightIn);
                    
                    double denominator = 1.0324 - (0.19077 * logWaistNeckDiff) + (0.15456 * logHeight);
                    if (denominator == 0) { // Prevent division by zero, though highly unlikely with these formulas
                        calculationError = "A division by zero occurred during calculation. Please check your measurements.";
                    } else {
                        bodyFatPercentage = (495.0 / denominator) - 450.0;
                    }
                }
                
                // Categorization for Men (example ranges, often used with Navy method)
                if (bodyFatPercentage < 6) category = "Essential Fat";
                else if (bodyFatPercentage >= 6 && bodyFatPercentage < 14) category = "Athletes";
                else if (bodyFatPercentage >= 14 && bodyFatPercentage < 18) category = "Fitness";
                else if (bodyFatPercentage >= 18 && bodyFatPercentage < 25) category = "Average";
                else category = "Obese";

            } else if ("female".equalsIgnoreCase(genderParam)) {
                // Female Formula: BF% = 495 / (1.29579 - 0.35004 * log10(waist + hip - neck) + 0.22100 * log10(height)) - 450
                
                // Critical check to prevent Math.log10(non-positive number) errors
                if (heightIn <= 0 || (waistIn + hipIn - neckIn) <= 0) {
                    calculationError = "Invalid measurements for female calculation: Height must be positive, and (Waist + Hip - Neck) must be positive.";
                } else {
                    double logCombinedCircumference = Math.log10(waistIn + hipIn - neckIn);
                    double logHeight = Math.log10(heightIn);
                    
                    double denominator = 1.29579 - (0.35004 * logCombinedCircumference) + (0.22100 * logHeight);
                    if (denominator == 0) { // Prevent division by zero
                        calculationError = "A division by zero occurred during calculation. Please check your measurements.";
                    } else {
                        bodyFatPercentage = (495.0 / denominator) - 450.0;
                    }
                }
                
                // Categorization for Women (example ranges, often used with Navy method)
                if (bodyFatPercentage < 14) category = "Essential Fat";
                else if (bodyFatPercentage >= 14 && bodyFatPercentage < 21) category = "Athletes";
                else if (bodyFatPercentage >= 21 && bodyFatPercentage < 25) category = "Fitness";
                else if (bodyFatPercentage >= 25 && bodyFatPercentage < 32) category = "Average";
                else category = "Obese";

            } else {
                // Handle cases where an invalid gender value is received (e.g., from tampering)
                displayError(out, "Invalid gender specified. Please select Male or Female.");
                return;
            }

            // If a specific calculation error occurred (e.g., negative log input), display it
            if (calculationError != null) {
                displayError(out, calculationError);
                return;
            }
            
            // Cap bodyFatPercentage at 0% if the formula yields a negative result due to unusual inputs
            if (bodyFatPercentage < 0) {
                bodyFatPercentage = 0;
                category = "Extremely Low / Essential Fat"; // Acknowledge potentially very low result
            }

            // --- Generate and Output the HTML Result Page ---
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Body Fat % Result</title>");
            out.println("<meta charset=\"UTF-8\">"); // Ensure proper character rendering
            out.println("<style>");
            // --- EMBEDDED CSS for the result page ---
            out.println("body {");
            out.println("    font-family: Arial, sans-serif;");
            out.println("    margin: 0;");
            out.println("    display: flex;");
            out.println("    justify-content: center;");
            out.println("    align-items: center;");
            out.println("    min-height: 100vh;");
            out.println("    background-color: #f4f4f4;");
            out.println("    color: #333;");
            out.println("}");
            out.println(".main-content {");
            out.println("    padding: 30px;");
            out.println("    background-color: #fff;");
            out.println("    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);");
            out.println("    border-radius: 8px;");
            out.println("    width: 100%;");
            out.println("    max-width: 500px;");
            out.println("    text-align: center;");
            out.println("    box-sizing: border-box;");
            out.println("}");
            out.println("h1 {");
            out.println("    color: #0056b3;");
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
            out.println(".bodyfat-category {");
            out.println("    font-weight: bold;");
            out.println("    font-size: 1.2em;");
            out.println("    padding: 10px 15px;");
            out.println("    border-radius: 5px;");
            out.println("    display: inline-block;");
            out.println("    margin-top: 15px;");
            out.println("    margin-bottom: 20px;");
            out.println("}");
            // Category-specific colors
            out.println(".essential-fat { color: #007bff; background-color: #e6f2ff; border: 1px solid #99ccff; }");
            out.println(".extremely-low-essential-fat { color: #8a2be2; background-color: #ede2fa; border: 1px solid #c2a8e8; }"); // For capped 0%
            out.println(".athletes { color: #28a745; background-color: #e6ffed; border: 1px solid #80ed99; }");
            out.println(".fitness { color: #17a2b8; background-color: #e0f2f4; border: 1px solid #81d4fa; }");
            out.println(".average { color: #FFD700; background-color: #fffde7; border: 1px solid #ffe082; }");
            out.println(".obese { color: #DC3545; background-color: #ffe6e6; border: 1px solid #ff8080; }");
            out.println("ul {");
            out.println("    list-style-type: none;");
            out.println("    padding: 0;");
            out.println("    margin: 20px auto;");
            out.println("    width: fit-content;");
            out.println("}");
            out.println("ul li {");
            out.println("    margin-bottom: 8px;");
            out.println("    text-align: left;");
            out.println("    padding-left: 1.5em;");
            out.println("    position: relative;");
            out.println("}");
            out.println("ul li::before {");
            out.println("    content: '\\2022';"); // Unicode for a bullet point
            out.println("    color: #007bff;");
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
            out.println("</style>");
            out.println("</head><body>");

            out.println("<div class=\"main-content\">");
            out.println("<h1>Your Body Fat Percentage</h1>");
            out.printf("<p>Gender: %s</p>", capitalize(genderParam));
            out.printf("<p>Weight: %.2f kg</p>", weight);
            out.printf("<p>Height: %.2f cm</p>", height);
            out.printf("<p>Neck: %.2f cm</p>", neck);
            out.printf("<p>Waist: %.2f cm</p>", waist);
            if ("female".equalsIgnoreCase(genderParam)) { // Only show hip for females
                out.printf("<p>Hip: %.2f cm</p>", hip);
            }
            out.printf("<p>Your Body Fat Percentage is: <strong>%.2f%%</strong></p>", bodyFatPercentage);
            // Replace spaces with hyphens and convert to lowercase for CSS class name
            out.println("<p class=\"bodyfat-category " + category.toLowerCase().replace(" ", "-") + "\">Category: " + category + "</p>");

            out.println("<p>Body Fat Categories (U.S. Navy Method Estimates):</p>");
            out.println("<ul>");
            out.println("<li><strong>Men:</strong></li>");
            out.println("<li>Essential Fat = 2-5%</li>");
            out.println("<li>Athletes = 6-13%</li>");
            out.println("<li>Fitness = 14-17%</li>");
            out.println("<li>Average = 18-24%</li>");
            out.println("<li>Obese = 25%+</li>");
            out.println("</ul>");
            out.println("<ul>");
            out.println("<li><strong>Women:</strong></li>");
            out.println("<li>Essential Fat = 10-13%</li>");
            out.println("<li>Athletes = 14-20%</li>");
            out.println("<li>Fitness = 21-24%</li>");
            out.println("<li>Average = 25-31%</li>");
            out.println("<li>Obese = 32%+</li>");
            out.println("</ul>");

            out.println("<p><a href=\"index.html\">Back to User Information</a></p>"); // Link back to the form
            out.println("</div>"); // Close main-content div
            out.println("</body></html>");

        } catch (Exception e) {
            // Catch any unexpected errors during processing and display a generic error message
            // In a production environment, you would log e.printStackTrace() to a server log file for debugging.
            // System.err.println("An unexpected error occurred in BodyFatServlet: " + e.getMessage());
            // e.printStackTrace(); // For development/debugging purposes
            displayError(out, "An unexpected error occurred. Please try again or contact support if the problem persists.");
        } finally {
            // Always close the PrintWriter to ensure all content is flushed and resources are released
            if (out != null) {
                out.close();
            }
        }
    }

    // --- Helper Method: To display consistent error pages ---
    private void displayError(PrintWriter out, String message) {
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Error</title>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; min-height: 100vh; background-color: #f4f4f4; margin: 0; }");
        out.println(".error-container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.1); width: 100%; max-width: 450px; text-align: center; box-sizing: border-box; }");
        out.println("h1 { color: #DC3545; margin-bottom: 20px; }"); // Red color for error messages
        out.println("p { text-align: center; }");
        out.println("a { color: #007bff; text-decoration: none; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class=\"error-container\">");
        out.println("<h1>Error: " + message + "</h1>");
        out.println("<p><a href=\"index.html\">Go back to Body Fat Calculator</a></p>"); // Link back to the form
        out.println("</div>");
        out.println("</body></html>");
    }

    // --- Handles direct GET requests to the servlet URL (e.g., typing /bodyFatCalculator in browser) ---
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Body Fat Calculator</title>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; min-height: 100vh; background-color: #f4f4f4; margin: 0; }");
        out.println(".message-container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.1); width: 100%; max-width: 450px; text-align: center; box-sizing: border-box; }");
        out.println("h1 { color: #0056b3; margin-bottom: 20px; }");
        out.println("p { text-align: center; }");
        out.println("a { color: #007bff; text-decoration: none; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class=\"message-container\">");
        out.println("<h1>Please use the Body Fat Calculator form to submit data.</h1>");
        out.println("<p><a href=\"index.html\">Go to Body Fat Calculator</a></p>"); // Link back to the form
        out.println("</div>");
        out.println("</body></html>");
        if (out != null) {
            out.close();
        }
    }
    
    // --- Helper method to capitalize the first letter of a string for display (e.g., "male" to "Male") ---
    private String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}