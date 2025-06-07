/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calorieService;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Web service for calculating daily calorie needs.
 * @author user
 */
@WebService(serviceName = "calorieService")
public class calorieService {

    /**
     * Calculates the estimated daily calorie needs using the Mifflin-St Jeor equation for BMR
     * and applying an activity factor.
     *
     * @param gender The gender of the person ("male" or "female").
     * @param weightKg The weight of the person in kilograms (kg).
     * @param heightCm The height of the person in centimeters (cm).
     * @param age The age of the person in years.
     * @param activityLevel A string representing the activity level (e.g., "sedentary", "lightly_active", "moderately_active", "very_active", "extra_active").
     * @return The estimated daily calorie needs, rounded to the nearest whole number.
     */
    @WebMethod(operationName = "calculateCalories")
    public double calculateDailyCalories(
            @WebParam(name = "gender") String gender,
            @WebParam(name = "weightKg") double weightKg,
            @WebParam(name = "heightCm") double heightCm,
            @WebParam(name = "age") int age,
            @WebParam(name = "activityLevel") String activityLevel) {

        // Validate inputs
        if (weightKg <= 0 || heightCm <= 0 || age <= 0 || gender == null || gender.trim().isEmpty() || activityLevel == null || activityLevel.trim().isEmpty()) {
            throw new IllegalArgumentException("All inputs (weight, height, age, gender, activity level) must be valid and positive.");
        }

        gender = gender.trim().toLowerCase();
        activityLevel = activityLevel.trim().toLowerCase();

        double bmr; // Basal Metabolic Rate

        // Mifflin-St Jeor Equation for BMR:
        // Men: (10 * weight in kg) + (6.25 * height in cm) - (5 * age in years) + 5
        // Women: (10 * weight in kg) + (6.25 * height in cm) - (5 * age in years) - 161
        if ("male".equals(gender)) {
            bmr = (10 * weightKg) + (6.25 * heightCm) - (5 * age) + 5;
        } else if ("female".equals(gender)) {
            bmr = (10 * weightKg) + (6.25 * heightCm) - (5 * age) - 161;
        } else {
            throw new IllegalArgumentException("Invalid gender. Please use 'male' or 'female'.");
        }

        // Activity factors (Harris-Benedict principle, adjusted)
        double activityFactor;
        switch (activityLevel) {
            case "sedentary": // Little to no exercise
                activityFactor = 1.2;
                break;
            case "lightly_active": // Light exercise/sports 1-3 days/week
                activityFactor = 1.375;
                break;
            case "moderately_active": // Moderate exercise/sports 3-5 days/week
                activityFactor = 1.55;
                break;
            case "very_active": // Hard exercise/sports 6-7 days a week
                activityFactor = 1.725;
                break;
            case "extra_active": // Very hard exercise/physical job
                activityFactor = 1.9;
                break;
            default:
                throw new IllegalArgumentException("Invalid activity level. Options: 'sedentary', 'lightly_active', 'moderately_active', 'very_active', 'extra_active'.");
        }

        double dailyCalories = bmr * activityFactor;

        // Round to nearest whole number
        return Math.round(dailyCalories);
    }
}