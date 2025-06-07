/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterintakeService;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Web service for estimating daily water intake.
 * @author user
 */
@WebService(serviceName = "waterintakeService")
public class waterintakeService { // Renamed class to WaterintakeService

    /**
     * Estimates recommended daily water intake based on weight and activity level.
     * The calculation uses a base of 33ml per kg of body weight, with additional
     * adjustments for increased activity.
     *
     * @param weightKg The weight of the person in kilograms (kg).
     * @param activityLevel A string representing the activity level (e.g., "sedentary", "moderately_active", "active", "very_active").
     * @return The estimated daily water intake in Liters, rounded to one decimal place.
     */
    @WebMethod(operationName = "CalculateWaterIntake")
    public double estimateWaterIntake(
            @WebParam(name = "weightKg") double weightKg,
            @WebParam(name = "activityLevel") String activityLevel) {

        // Validate inputs
        if (weightKg <= 0 || activityLevel == null || activityLevel.trim().isEmpty()) {
            throw new IllegalArgumentException("Weight and Activity Level must be valid inputs.");
        }
        if (weightKg < 20 || weightKg > 300) { // Basic range for weight
            throw new IllegalArgumentException("Please enter a realistic weight in kilograms (e.g., between 20 and 300 kg).");
        }

        activityLevel = activityLevel.trim().toLowerCase();

        // Base calculation: approximately 33 ml per kg of body weight
        double baseWaterIntakeMl = weightKg * 33.0;
        double totalWaterIntakeMl = baseWaterIntakeMl;

        // Adjust water intake based on activity level
        switch (activityLevel) {
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
                throw new IllegalArgumentException("Invalid activity level. Options: 'sedentary', 'moderately_active', 'active', 'very_active'.");
        }

        // Convert total water intake from milliliters to liters
        double totalWaterIntakeLiters = totalWaterIntakeMl / 1000.0;

        // Round to one decimal place
        return Math.round(totalWaterIntakeLiters * 10.0) / 10.0;
    }
}