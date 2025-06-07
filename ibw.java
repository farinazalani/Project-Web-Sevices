package com.ibw; // Package name remains 'com.ibw' as specified

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/idealBodyWeightCalculator") // Servlet mapping for the web.xml or annotation-based deployment
public class ibw extends HttpServlet { // Changed class name to be more descriptive and follow Java conventions

    private static final long serialVersionUID = 1L; // Recommended for HttpServlet

    public ibw() {
        super(); // Call the parent constructor
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Set response content type and character encoding
        PrintWriter out = response.getWriter(); // Get the PrintWriter to write HTML response

        try {
            // Retrieve parameters from the request. These names must match your HTML form's 'name' attributes.
            String genderParam = request.getParameter("ibw-gender");
            String heightStr = request.getParameter("ibw-height"); // Height is expected in centimeters

            // --- Input Validation: Check for null or empty strings for essential fields ---
            if (genderParam == null || genderParam.isEmpty() ||
                heightStr == null || heightStr.isEmpty()) {

                displayError(out, "Gender and Height are required fields. Please go back and provide valid inputs.");
                return; // Stop further processing if validation fails
            }

            // --- Parse height and handle NumberFormatException for numeric input ---
            double heightCm; // Variable to store height in centimeters
            try {
                heightCm = Double.parseDouble(heightStr); // Convert String height to double

                // Basic range validation for height to ensure realistic input
                if (heightCm <= 50 || heightCm > 250) { // Assuming heights typically range from 50cm to 250cm
                    displayError(out, "Please enter a realistic height in centimeters (e.g., between 50 and 250 cm).");
                    return; // Stop if height is out of a reasonable range
                }

            } catch (NumberFormatException e) {
                // If parsing fails, it means non-numeric input was provided for height
                displayError(out, "Invalid numeric input for height. Please enter a number.");
                return; // Stop if height is not a valid number
            }

            // --- Calculate Ideal Body Weight (IBW) using the Hamwi Formula (adapted for cm) ---
            // The Hamwi formula typically uses inches, so we need to convert cm to inches.
            // Conversion factors: 1 inch = 2.54 cm, 5 feet = 60 inches = 152.4 cm (baseline)

            double ibwKg = 0.0; // Initialize Ideal Body Weight in kilograms
            double heightInches = heightCm / 2.54; // Convert height from cm to inches
            double inchesOver5Feet = heightInches - 60; // Calculate inches above/below 5 feet (60 inches) baseline

            // Hamwi Formula details:
            // Men: 48 kg (for the first 5 feet) + 2.7 kg for each inch over 5 feet
            // Women: 45.5 kg (for the first 5 feet) + 2.2 kg for each inch over 5 feet

            if ("male".equalsIgnoreCase(genderParam)) { // Case-insensitive comparison for gender
                if (heightInches < 60) {
                    // For heights under 5 feet, subtract weight for each inch under 5 feet.
                    // Math.abs(inchesOver5Feet) is used to ensure a positive value for subtraction.
                    ibwKg = 48 - (2.7 * Math.abs(inchesOver5Feet));
                } else {
                    ibwKg = 48 + (2.7 * inchesOver5Feet);
                }
            } else if ("female".equalsIgnoreCase(genderParam)) {
                if (heightInches < 60) {
                    // For heights under 5 feet, subtract weight for each inch under 5 feet.
                    ibwKg = 45.5 - (2.2 * Math.abs(inchesOver5Feet));
                } else {
                    ibwKg = 45.5 + (2.2 * inchesOver5Feet);
                }
            } else {
                // This case should ideally be caught by initial validation if gender is a dropdown,
                // but included for robustness if manual input or other issues occur.
                displayError(out, "Invalid gender specified. Please select Male or Female.");
                return; // Stop if gender is not recognized
            }

            // Ensure IBW doesn't result in negative or unrealistically low values, especially for very short heights
            if (ibwKg < 10) { // An arbitrary lower bound; can be adjusted based on specific requirements
                ibwKg = 10;
            }

            // --- Generate and Output the HTML Result Page ---
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Ideal Body Weight Result</title>");
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
            out.println(".ibw-summary {");
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
            out.println("<h1>Your Ideal Body Weight (IBW)</h1>");
            out.printf("<p>Gender: %s</p>", capitalize(genderParam));
            out.printf("<p>Height: %.2f cm</p>", heightCm);
            // Display IBW, formatted to one decimal place
            out.printf("<p class=\"ibw-summary\">Your estimated Ideal Body Weight is: <strong>%.1f kg</strong></p>", ibwKg);

            out.println("<p>This estimate is based on the **Hamwi Formula**, a widely used method for calculating ideal body weight.</p>");
            out.println("<p>Please note that IBW formulas provide a general guideline. Factors like muscle mass, body composition, and individual health conditions can influence what a healthy weight is for you.</p>");
            out.println("<p>The Hamwi Formula uses a baseline of 5 feet (152.4 cm) and adds/subtracts weight for every inch above/below that height:</p>");
            out.println("<ul>");
            out.println("<li><strong>Men:</strong> 48 kg for the first 5 feet + 2.7 kg for each additional inch.</li>");
            out.println("<li><strong>Women:</strong> 45.5 kg for the first 5 feet + 2.2 kg for each additional inch.</li>");
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
        out.println("<p><a href=\"index.html\">Go back to IBW Calculator</a></p>"); // Link back to the main form
        out.println("</div>");
        out.println("</body></html>");
    }

    // --- Handles direct GET requests to the servlet URL (e.g., typing /idealBodyWeightCalculator in browser) ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Ideal Body Weight Calculator</title>");
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
        out.println("<h1>Please use the Ideal Body Weight Calculator form to submit data.</h1>");
        out.println("<p><a href=\"index.html\">Go to IBW Calculator</a></p>"); // Link back to the main form
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