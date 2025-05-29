/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmiService;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author user
 */
@WebService(serviceName = "bmiService")
public class bmiService {

    /**
     * This is a sample web service operation
     */
    @WebMethod
    public double calculateBMI(double weight, double height) {
        double heightM = height / 100;
        return Math.round((weight / (heightM * heightM)) * 100.0) / 100.0;
    }
}
