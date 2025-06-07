package com.calorie;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calorieCalculator")
public class calorie extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public calorie() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Retrieve parameters from the request. Ensure these names match your HTML form's 'name' attributes.
            String genderParam = request.getParameter("cal-gender");
            String ageStr = request.getParameter("cal-age");
            String weightStr = request.getParameter("cal-weight"); // in kg
            String heightStr = request.getParameter("cal-height"); // in cm
            String activityParam = request.getParameter("cal-activity");

            // --- Input Validation: Check for null or empty strings for all essential fields ---
            if (genderParam == null || genderParam.isEmpty() ||
                ageStr == null || ageStr.isEmpty() ||
                weightStr == null || weightStr.isEmpty() ||
                heightStr == null || heightStr.isEmpty() ||
                activityParam == null || activityParam.isEmpty()) {

                displayError(out, "All required fields must be filled. Please go back and provide valid inputs.");
                return;
            }

            // --- Parse values and handle NumberFormatException for numeric inputs ---
            int age;       // years
            double weight; // kg
            double height; // cm
            double activityMultiplier; // Numerical value for activity level

            try {
                age = Integer.parseInt(ageStr);
                weight = Double.parseDouble(weightStr);
                height = Double.parseDouble(heightStr);
                activityMultiplier = Double.parseDouble(activityParam);

                // Basic range validation for inputs
                if (age <= 0 || weight <= 0 || height <= 0 || activityMultiplier <= 0) {
                    displayError(out, "Age, weight, height, and activity level must be positive values.");
                    return;
                }

            } catch (NumberFormatException e) {
                // If any parsing fails, it means non-numeric input was provided
                displayError(out, "Invalid numeric input. Please enter numbers for age, weight, and height.");
                return;
            }

            // --- Calculate Basal Metabolic Rate (BMR) using Mifflin-St Jeor Equation ---
            // For Men: BMR = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) + 5
            // For Women: BMR = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) - 161

            double bmr;
            if ("male".equalsIgnoreCase(genderParam)) {
                bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;
            } else if ("female".equalsIgnoreCase(genderParam)) {
                bmr = (10 * weight) + (6.25 * height) - (5 * age) - 161;
            } else {
                // This case should ideally be caught by initial validation if gender is a dropdown
                displayError(out, "Invalid gender specified. Please select Male or Female.");
                return;
            }

            // --- Calculate Total Daily Energy Expenditure (TDEE) ---
            double tdee = bmr * activityMultiplier;

            // --- Determine Activity Level Description for display ---
            String activityDescription = "";
            switch (activityParam) {
                case "1.2":
                    activityDescription = "Sedentary (little or no exercise)";
                    break;
                case "1.375":
                    activityDescription = "Lightly active (light exercise/sports 1-3 days/week)";
                    break;
                case "1.55":
                    activityDescription = "Moderately active (moderate exercise/sports 3-5 days/week)";
                    break;
                case "1.725":
                    activityDescription = "Very active (hard exercise/sports 6-7 days a week)";
                    break;
                case "1.9":
                    activityDescription = "Extra active (very hard exercise/physical job)";
                    break;
                default:
                    activityDescription = "Unknown Activity Level"; // Fallback
                    break;
            }

            // --- Generate and Output the HTML Result Page ---
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Calorie Needs Result</title>");
            out.println("<meta charset=\"UTF-8\">");
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
            out.println(".calorie-summary {");
            out.println("    font-weight: bold;");
            out.println("    font-size: 1.5em;");
            out.println("    padding: 15px 20px;");
            out.println("    border-radius: 8px;");
            out.println("    display: inline-block;");
            out.println("    margin-top: 25px;");
            out.println("    margin-bottom: 30px;");
            out.println("    color: #28a745;"); /* Green for positive result */
            out.println("    background-color: #e6ffed;");
            out.println("    border: 1px solid #80ed99;");
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
            out.println("<h1>Estimated Daily Calorie Needs</h1>");
            out.printf("<p>Gender: %s</p>", capitalize(genderParam));
            out.printf("<p>Weight: %.2f kg</p>", weight);
            out.printf("<p>Height: %.2f cm</p>", height);
            out.printf("<p>Age: %d years</p>", age);
            out.printf("<p>Activity Level: %s</p>", activityDescription);
            out.printf("<p>Your Basal Metabolic Rate (BMR) is: <strong>%.0f calories/day</strong></p>", bmr);
            out.printf("<p class=\"calorie-summary\">Your estimated Total Daily Energy Expenditure (TDEE) is: <strong>%.0f calories/day</strong></p>", tdee);

            out.println("<p>This is an estimate based on the Mifflin-St Jeor Equation. Individual needs may vary.</p>");
            out.println("<p>Daily Calorie Needs by Activity Level Multipliers:</p>");
            out.println("<ul>");
            out.println("<li><strong>Sedentary (little or no exercise):</strong> BMR x 1.2</li>");
            out.println("<li><strong>Lightly Active (light exercise/sports 1-3 days/week):</strong> BMR x 1.375</li>");
            out.println("<li><strong>Moderately Active (moderate exercise/sports 3-5 days/week):</strong> BMR x 1.55</li>");
            out.println("<li><strong>Very Active (hard exercise/sports 6-7 days a week):</strong> BMR x 1.725</li>");
            out.println("<li><strong>Extra Active (very hard exercise/physical job):</strong> BMR x 1.9</li>");
            out.println("</ul>");

            out.println("<p><a href=\"index.html\">Calculate Again</a></p>"); // Link back to the form
            out.println("</div>"); // Close main-content div
            out.println("</body></html>");

        } catch (Exception e) {
            // Catch any unexpected errors during processing and display a generic error message
            // In a production environment, you would log e.printStackTrace() to a server log file for debugging.
            // System.err.println("An unexpected error occurred in CalorieCalculatorServlet: " + e.getMessage());
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
        out.println("<p><a href=\"index.html\">Go back to Calorie Calculator</a></p>"); // Link back to the form
        out.println("</div>");
        out.println("</body></html>");
    }

    // --- Handles direct GET requests to the servlet URL (e.g., typing /calorieCalculator in browser) ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Calorie Calculator</title>");
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
        out.println("<h1>Please use the Calorie Calculator form to submit data.</h1>");
        out.println("<p><a href=\"index.html\">Go to Calorie Calculator</a></p>"); // Link back to the form
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