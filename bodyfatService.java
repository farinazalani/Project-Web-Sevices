/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bodyfatService;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Web service for estimating Body Fat Percentage.
 * This is a simplified estimation and not a precise clinical measurement.
 * @author user
 */
@WebService(serviceName = "bodyfatService")
public class bodyfatService {

    /**
     * Estimates Body Fat Percentage based on a simplified formula (e.g., adapted for basic inputs).
     * This method provides a general estimate and should not replace professional assessment.
     *
     * @param gender The gender of the person ("male" or "female").
     * @param weight The weight of the person in kilograms (kg).
     * @param height The height of the person in centimeters (cm).
     * @param age The age of the person in years.
     * @return The estimated body fat percentage, rounded to two decimal places. Returns -1.0 if inputs are invalid.
     */
    @WebMethod(operationName = "CalculateBodyFat")
    public double estimateBodyFatPercentage(
            @WebParam(name = "gender") String gender,
            @WebParam(name = "weight") double weight,
            @WebParam(name = "height") double height,
            @WebParam(name = "age") int age) {

        if (weight <= 0 || height <= 0 || age <= 0 || gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Weight, Height, Age, and Gender must be valid positive inputs.");
        }

        gender = gender.trim().toLowerCase();
        double bodyFatPercentage = 0.0;
        double bmi = (weight / ((height / 100.0) * (height / 100.0))); // Calculate BMI first

        // A simplified formula (e.g., Deurenberg formula, adapted for basic inputs)
        // Note: This formula is a simplification and not as accurate as formulas using more specific body measurements.
        if ("male".equals(gender)) {
            bodyFatPercentage = (1.20 * bmi) + (0.23 * age) - 10.8 - 5.4;
        } else if ("female".equals(gender)) {
            bodyFatPercentage = (1.20 * bmi) + (0.23 * age) - 5.4; // Women typically have higher body fat
        } else {
            throw new IllegalArgumentException("Invalid gender. Please use 'male' or 'female'.");
        }

        // Ensure body fat percentage is not negative and within a reasonable range (e.g., 5% to 50%)
        if (bodyFatPercentage < 5.0) bodyFatPercentage = 5.0;
        if (bodyFatPercentage > 50.0) bodyFatPercentage = 50.0;

        return Math.round(bodyFatPercentage * 100.0) / 100.0;
    }
}