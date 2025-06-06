
package com.fitness.wsclient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "BodyFatCalculator", targetNamespace = "http://fitness.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BodyFatCalculator {


    /**
     * 
     * @param gender
     * @param weight
     * @param waist
     * @param neck
     * @param hip
     * @param height
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "generateBodyFatReport", targetNamespace = "http://fitness.org/", className = "com.fitness.wsclient.GenerateBodyFatReport")
    @ResponseWrapper(localName = "generateBodyFatReportResponse", targetNamespace = "http://fitness.org/", className = "com.fitness.wsclient.GenerateBodyFatReportResponse")
    @Action(input = "http://fitness.org/BodyFatCalculator/generateBodyFatReportRequest", output = "http://fitness.org/BodyFatCalculator/generateBodyFatReportResponse")
    public String generateBodyFatReport(
        @WebParam(name = "gender", targetNamespace = "")
        String gender,
        @WebParam(name = "weight", targetNamespace = "")
        double weight,
        @WebParam(name = "height", targetNamespace = "")
        double height,
        @WebParam(name = "waist", targetNamespace = "")
        double waist,
        @WebParam(name = "neck", targetNamespace = "")
        double neck,
        @WebParam(name = "hip", targetNamespace = "")
        double hip);

}
