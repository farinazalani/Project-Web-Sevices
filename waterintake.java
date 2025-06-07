package com.waterintake; // Package name remains 'com.ibw' as specified

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/waterIntakeCalculator") // Changed servlet mapping for water intake
public class waterintake extends HttpServlet { // Changed class name to reflect its new purpose

    private static final long serialVersionUID = 1L; // Recommended for HttpServlet

    public waterintake() {
        super(); // Call the parent constructor
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Set response content type and character encoding
        PrintWriter out = response.getWriter(); // Get the PrintWriter to write HTML response

        try {
            // Retrieve parameters from the request. These names must match your HTML form's 'name' attributes.
            String weightStr = request.getParameter("water-weight"); // User's weight in kg
            String activityLevelParam = request.getParameter("water-activity"); // User's activity level

            // --- Input Validation: Check for null or empty strings for essential fields ---
            if (weightStr == null || weightStr.isEmpty() ||
                activityLevelParam == null || activityLevelParam.isEmpty()) {

                displayError(out, "Weight and Activity Level are required fields. Please go back and provide valid inputs.");
                return; // Stop further processing if validation fails
            }

            // --- Parse weight and handle NumberFormatException ---
            double weightKg; // Variable to store weight in kilograms
            try {
                weightKg = Double.parseDouble(weightStr); // Convert String weight to double

                // Basic range validation for weight (e.g., between 20 kg and 300 kg)
                if (weightKg <= 20 || weightKg > 300) {
                    displayError(out, "Please enter a realistic weight in kilograms (e.g., between 20 and 300 kg).");
                    return; // Stop if weight is out of a reasonable range
                }

            } catch (NumberFormatException e) {
                // If parsing fails, it means non-numeric input was provided for weight
                displayError(out, "Invalid numeric input for weight. Please enter a number.");
                return; // Stop if weight is not a valid number
            }

            // --- Calculate Ideal Daily Water Intake ---
            // A common guideline: approximately 30-35 ml per kg of body weight.
            // We'll use a base of 33ml/kg and adjust for activity level.

            double baseWaterIntakeMl = weightKg * 33; // Base calculation: 33 ml per kg
            double totalWaterIntakeMl = baseWaterIntakeMl; // Initialize with base intake

            // Adjust water intake based on activity level
            switch (activityLevelParam.toLowerCase()) { // Use toLowerCase for case-insensitive comparison
                case "sedentary":
                    // No additional intake for sedentary
                    break;
                case "moderately_active":
                    totalWaterIntakeMl += 500; // Add 500 ml for moderate activity
                    break;
                case "active":
                    totalWaterIntakeMl += 1000; // Add 1000 ml (1 Liter) for active
                    break;
                case "very_active":
                    totalWaterIntakeMl += 1500; // Add 1500 ml (1.5 Liters) for very active
                    break;
                default:
                    // If an unknown activity level is passed, provide a default or error
                    // For simplicity, we'll proceed with base calculation if activity is unrecognized.
                    // A more robust app might display an error or prompt for valid input.
                    break;
            }

            // Convert total water intake from milliliters to liters for display
            double totalWaterIntakeLiters = totalWaterIntakeMl / 1000.0;

            // --- Generate and Output the HTML Result Page ---
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Daily Water Intake Result</title>");
            out.println("<meta charset=\"UTF-8\">"); // Ensure proper character encoding for the HTML page
            out.println("<style>");
            // --- EMBEDDED CSS for the result page styling ---
            out.println("body {");
            out.println("    font-family: Arial, sans-serif;");
            out.println("    margin: 0;");
            out.println("    display: flex;");
            out.println("    justify-content: center;");
            out.println("    align-items: center;");
            out.println("    min-height: 100vh;");
            out.println("    background-color: #e6f7ff; /* Light blue background */");
            out.println("    color: #333;");
            out.println("}");
            out.println(".main-content {");
            out.println("    padding: 30px;");
            out.println("    background-color: #fff;");
            out.println("    box-shadow: 0 0 20px rgba(0, 123, 255, 0.2); /* Blue shadow */");
            out.println("    border-radius: 10px;");
            out.println("    width: 100%;");
            out.println("    max-width: 550px;");
            out.println("    text-align: center;");
            out.println("    box-sizing: border-box;");
            out.println("    border: 1px solid #b3d9ff;"); /* Light blue border */
            out.println("}");
            out.println("h1 {");
            out.println("    color: #007bff;"); /* Blue header */
            out.println("    margin-bottom: 25px;");
            out.println("    border-bottom: 2px solid #e0f2ff; /* Lighter blue border */");
            out.println("    padding-bottom: 10px;");
            out.println("}");
            out.println("p {");
            out.println("    font-size: 1.1em;");
            out.println("    line-height: 1.6;");
            out.println("    margin-bottom: 10px;");
            out.println("}");
            out.println("p strong {");
            out.println("    color: #0056b3; /* Darker blue */");
            out.println("    font-size: 1.2em;");
            out.println("}");
            out.println(".water-summary {"); // Changed class name
            out.println("    font-weight: bold;");
            out.println("    font-size: 1.8em;");
            out.println("    padding: 20px 25px;");
            out.println("    border-radius: 10px;");
            out.println("    display: inline-block;");
            out.println("    margin-top: 30px;");
            out.println("    margin-bottom: 40px;");
            out.println("    color: #28a745;"); /* Green for positive result */
            out.println("    background-color: #e6ffed;");
            out.println("    border: 2px solid #80ed99;");
            out.println("    box-shadow: 0 0 10px rgba(40, 167, 69, 0.2);");
            out.println("}");
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
            out.println("<h1>Your Estimated Daily Water Intake</h1>");
            out.printf("<p>Your Weight: %.1f kg</p>", weightKg);
            out.printf("<p>Your Activity Level: %s</p>", formatActivityLevel(activityLevelParam));
            // Display water intake, formatted to one decimal place
            out.printf("<p class=\"water-summary\">Your estimated daily water intake is: <strong>%.1f Liters</strong></p>", totalWaterIntakeLiters);

            out.println("<p>This estimate is based on general guidelines, suggesting approximately <strong>33ml of water per kilogram of body weight</strong>, adjusted for activity level.</p>");
            out.println("<p>Individual needs can vary based on factors like climate, health conditions, and specific physical demands.</p>");
            out.println("<p>Here's how activity level affects the estimate:</p>");
            out.println("<ul>");
            out.println("<li><strong>Sedentary:</strong> Base intake (approx. 33ml/kg).</li>");
            out.println("<li><strong>Moderately Active:</strong> Base intake + 0.5 Liters.</li>");
            out.println("<li><strong>Active:</strong> Base intake + 1.0 Liter.</li>");
            out.println("<li><strong>Very Active:</strong> Base intake + 1.5 Liters.</li>");
            out.println("</ul>");

            out.println("<p><a href=\"index.html\">Calculate Again</a></p>"); // Link back to the form page
            out.println("</div>"); // Close main-content div
            out.println("</body></html>"); // Close HTML tags

        } catch (Exception e) {
            // Catch any unexpected exceptions during processing and display a generic error message
            // In a production environment, you would also log e.printStackTrace() to a server log file for debugging.
            displayError(out, "An unexpected error occurred. Please try again or contact support if the problem persists.");
        } finally {
            // Always close the PrintWriter to ensure all content is flushed and resources are released
            if (out != null) {
                out.close();
            }
        }
    }

    // --- Helper Method: To display consistent error pages for input validation or unexpected errors ---
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
        out.println("<p><a href=\"waterIntake.html\">Go back to Water Intake Calculator</a></p>"); // Link back to the main form
        out.println("</div>");
        out.println("</body></html>");
    }

    // --- Handles direct GET requests to the servlet URL (e.g., typing /waterIntakeCalculator in browser) ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Daily Water Intake Calculator</title>");
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
        out.println("<h1>Please use the Daily Water Intake Calculator form to submit data.</h1>");
        out.println("<p><a href=\"waterIntake.html\">Go to Water Intake Calculator</a></p>"); // Link back to the main form
        out.println("</div>");
        out.println("</body></html>");
        if (out != null) {
            out.close();
        }
    }

    // --- Helper method to format activity level for display (e.g., "moderately_active" to "Moderately Active") ---
    private String formatActivityLevel(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        // Replace underscores with spaces and capitalize each word
        String[] words = s.toLowerCase().split("_");
        StringBuilder formatted = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formatted.append(Character.toUpperCase(word.charAt(0)))
                         .append(word.substring(1))
                         .append(" ");
            }
        }
        return formatted.toString().trim(); // Remove trailing space
    }
}