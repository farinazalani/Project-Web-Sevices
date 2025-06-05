/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.WaterIntake;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author tengk
 */
@WebServlet("/WaterIntakeCalc")
public class WaterIntakeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String gender = request.getParameter("gender");
        String weightStr = request.getParameter("weightKg");
        String activity = request.getParameter("activityLevel");

        String result;

        try {
            double weight = Double.parseDouble(weightStr);
            WaterIntakeCalc calc = new WaterIntakeCalc();
            result = calc.calculateWaterIntake(gender, weight, activity);
        } catch (NumberFormatException e) {
            result = "Error: Invalid weight input.";
        }

        request.setAttribute("result", result);
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}
