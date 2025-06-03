package org.cal;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.util.HashMap;
import java.util.Map;

@WebService(serviceName = "CaloriesBurnRateCalculation")
public class CaloriesBurnRateCalculation {

    private static final Map<String, Double> MET_VALUES = new HashMap<>();

    static {
        MET_VALUES.put("running", 9.8);
        MET_VALUES.put("walking", 3.8);
        MET_VALUES.put("cycling", 7.5);
    }

    @WebMethod(operationName = "calculateCalories")
    public double calculateCalories(
            @WebParam(name = "weight_kg") double weight,
            @WebParam(name = "duration_min") double duration,
            @WebParam(name = "activity_type") String activityType,
            @WebParam(name = "age") int age,
            @WebParam(name = "height_cm") double height,
            @WebParam(name = "gender") String gender
    ) {
        activityType = activityType.toLowerCase();

        if (!MET_VALUES.containsKey(activityType)) {
            return -1; // or throw a SOAP fault
        }

        if (weight <= 0 || duration <= 0) {
            return -1; // or return a validation error
        }

        double durationHours = duration / 60.0;
        double calories = MET_VALUES.get(activityType) * weight * durationHours;
        return Math.round(calories * 100.0) / 100.0;
    }
}