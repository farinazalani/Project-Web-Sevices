package org.WaterIntake;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Web Service for calculating daily water intake based on weight and activity level.
 * Supports input validation.
 * 
 * Author: tengk
 */
@WebService(serviceName = "WaterIntakeCalc")
public class WaterIntakeCalc {

    @WebMethod
    public String calculateWaterIntake(
        @WebParam(name = "name") String name,
        @WebParam(name = "matricNumber") String matricNumber,
        @WebParam(name = "gender") String gender,
        @WebParam(name = "weightKg") double weightKg,
        @WebParam(name = "heightCm") double heightCm,
        @WebParam(name = "activityLevel") String activityLevel
    ) {
        // Validation
        if (name == null || name.trim().isEmpty()) {
            return "Error: Name cannot be empty.";
        }

        if (matricNumber == null || matricNumber.trim().isEmpty()) {
            return "Error: Matric number cannot be empty.";
        }

        if (gender == null || (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female"))) {
            return "Error: Gender must be 'male' or 'female'.";
        }

        if (weightKg <= 0) {
            return "Error: Weight must be more than 0 kg.";
        }

        if (heightCm <= 0) {
            return "Error: Height must be more than 0 cm.";
        }

        if (activityLevel == null || activityLevel.trim().isEmpty()) {
            return "Error: Activity level cannot be empty.";
        }

        String level = activityLevel.toLowerCase().trim();

        if (!level.equals("light") && !level.equals("moderate") && !level.equals("intense")) {
            return "Error: Activity level must be 'light', 'moderate', or 'intense'.";
        }

        // Calculation
        double baseWater = weightKg * 0.033;
        double multiplier;

        switch (level) {
            case "light":
                multiplier = 1.0;
                break;
            case "moderate":
                multiplier = 1.2;
                break;
            case "intense":
                multiplier = 1.4;
                break;
            default:
                multiplier = 1.0;
                break;
        }

        double recommendedWater = baseWater * multiplier;

        // Output
        return String.format(
            "Name: %s\nMatric No: %s\nGender: %s\nWeight: %.2f kg\nHeight: %.2f cm\nActivity: %s\n\nRecommended water intake: %.2f liters per day.",
            name, matricNumber, gender, weightKg, heightCm, level, recommendedWater
        );
    }
}
