/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userInformation;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author user
 */
@WebService(serviceName = "userInfo")
public class userInfo {

    /**
     * This is a sample web service operation
     */
    @WebMethod 
    public String displayInfo(String name, String ic, String gender, double weight, double height) {
        return "Name: " + name + ", IC: " + ic + ", Gender: " + gender +
               ", Weight: " + weight + "kg, Height: " + height + "cm";
    }
    
}
