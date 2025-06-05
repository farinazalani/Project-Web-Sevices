package org.WaterIntake;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Web Service for calculating daily water intake based on weight and activity level.
 * Supports input validation.
 * 
 * Author: tengk
 */

public class WaterIntakeCalc {

    public String calculateWaterIntake(String gender, double weightKg, String activityLevel) {
        if (gender == null || (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female"))) {
            return "Error: Gender must be 'male' or 'female'.";
        }

        if (weightKg <= 0) {
            return "Error: Weight must be more than 0 kg.";
        }

        if (activityLevel == null || activityLevel.trim().isEmpty()) {
            return "Error: Activity level cannot be empty.";
        }

        String level = activityLevel.toLowerCase().trim();
        double multiplier;

        switch (level) {
            case "light": multiplier = 1.0; break;
            case "moderate": multiplier = 1.2; break;
            case "intense": multiplier = 1.4; break;
            default: return "Error: Invalid activity level.";
        }

        double baseWater = weightKg * 0.033;
        double recommendedWater = baseWater * multiplier;

        return String.format("Gender: %s<br>Weight: %.2f kg<br>Activity: %s<br><br><strong>Recommended water intake: %.2f liters per day.</strong>",
                gender, weightKg, level, recommendedWater);
    }
}
