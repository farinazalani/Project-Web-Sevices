package org.fitness;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Daniel Iman
 */

@WebService(serviceName = "HealthService")
public class BodyFatCalculator {

    @WebMethod(operationName = "generateBodyFatReport")
    public String generateBodyFatReport(
            @WebParam(name = "gender") String gender,
            @WebParam(name = "weight") double weight,
            @WebParam(name = "height") double height,
            @WebParam(name = "waist") double waist,
            @WebParam(name = "neck") double neck,
            @WebParam(name = "hip") double hip) {

        // Validate gender
        if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
            return "Error: Gender must be 'Male' or 'Female'.";
        }

        // Validate measurements
        if (height <= 0 || waist <= 0 || neck <= 0 || weight <= 0) {
            return "Error: Height, waist, neck, and weight must be positive values.";
        }

        if (gender.equalsIgnoreCase("Female") && hip <= 0) {
            return "Error: Hip measurement is required for females.";
        }

        double bodyFat;

        if (gender.equalsIgnoreCase("Male")) {
            bodyFat = 495 / (1.0324 - 0.19077 * Math.log10(waist - neck) + 0.15456 * Math.log10(height)) - 450;

        } else {
            bodyFat = 495 / (1.29579 - 0.35004 * Math.log10(waist + hip - neck) + 0.22100 * Math.log10(height)) - 450;

        }

        String category = getBodyFatCategory(gender, bodyFat);

        // Build report
        StringBuilder report = new StringBuilder();
        report.append("----- Body Fat Report -----\n");
        report.append("Gender: ").append(gender).append("\n"); 
        report.append("Weight: ").append(weight).append(" kg\n"); 
        report.append("Height: ").append(height).append(" cm\n"); 
        report.append(String.format("Body Fat: %.2f%%\n", bodyFat)); 
        report.append("Category: ").append(category).append("\n");
        report.append("----------------");

        return report.toString();
    } 

    private String getBodyFatCategory(String gender, double bodyFat) {
        if (gender.equalsIgnoreCase("Male")) {
            if (bodyFat < 6) return "Essential Fat";
            else if (bodyFat < 14) return "Athletic";
            else if (bodyFat < 18) return "Fit";
            else if (bodyFat < 26) return "Acceptable";
            else return "Obese";
        } else {
            if (bodyFat < 14) return "Essential Fat";
            else if (bodyFat < 21) return "Athletic";
            else if (bodyFat < 25) return "Fit";
            else if (bodyFat < 32) return "Acceptable";
            else return "Obese";
        }
    }
}

