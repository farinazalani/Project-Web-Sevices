/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibwService;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Web service for calculating Ideal Body Weight (IBW) using the Hamwi formula.
 * @author user
 */
@WebService(serviceName = "ibwService")
public class ibwService { // Renamed class to IbwService

    /**
     * Calculates Ideal Body Weight (IBW) using the Hamwi formula.
     * The formula is based on a baseline height of 5 feet (60 inches) and
     * adds/subtracts weight for each inch above/below this baseline.
     *
     * @param gender The gender of the person ("male" or "female").
     * @param heightCm The height of the person in centimeters (cm).
     * @return The estimated Ideal Body Weight in kilograms (kg), rounded to one decimal place.
     */
    @WebMethod(operationName = "calculateIBW")
    public double calculateIBW(
            @WebParam(name = "gender") String gender,
            @WebParam(name = "heightCm") double heightCm) {

        // Validate inputs
        if (heightCm <= 0 || gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Height and Gender must be valid positive inputs.");
        }
        if (heightCm < 50 || heightCm > 250) { // Basic range validation for height
            throw new IllegalArgumentException("Please enter a realistic height in centimeters (e.g., between 50 and 250 cm).");
        }

        gender = gender.trim().toLowerCase();

        double ibwKg = 0.0;
        double heightInches = heightCm / 2.54; // Convert height from cm to inches
        double inchesOver5Feet = heightInches - 60; // Assuming 5 feet (60 inches) as baseline

        // Hamwi Formula:
        // Men: 48 kg + 2.7 kg for each inch over 5 feet
        // Women: 45.5 kg + 2.2 kg for each inch over 5 feet

        if ("male".equals(gender)) {
            // Adapted for heights under 5 feet: subtract weight per inch under
            ibwKg = 48 + (2.7 * inchesOver5Feet);
        } else if ("female".equals(gender)) {
            // Adapted for heights under 5 feet: subtract weight per inch under
            ibwKg = 45.5 + (2.2 * inchesOver5Feet);
        } else {
            throw new IllegalArgumentException("Invalid gender. Please use 'male' or 'female'.");
        }

        // Ensure IBW doesn't go negative or unrealistically low
        if (ibwKg < 10) { // Arbitrary lower bound
            ibwKg = 10;
        }

        // Round IBW to one decimal place
        return Math.round(ibwKg * 10.0) / 10.0;
    }
}