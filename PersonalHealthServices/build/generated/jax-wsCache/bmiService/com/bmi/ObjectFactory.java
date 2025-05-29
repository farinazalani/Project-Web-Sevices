
package com.bmi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bmi package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CalculateBMIResponse_QNAME = new QName("http://bmiService.com/", "calculateBMIResponse");
    private final static QName _CalculateBMI_QNAME = new QName("http://bmiService.com/", "calculateBMI");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bmi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CalculateBMIResponse }
     * 
     */
    public CalculateBMIResponse createCalculateBMIResponse() {
        return new CalculateBMIResponse();
    }

    /**
     * Create an instance of {@link CalculateBMI }
     * 
     */
    public CalculateBMI createCalculateBMI() {
        return new CalculateBMI();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateBMIResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bmiService.com/", name = "calculateBMIResponse")
    public JAXBElement<CalculateBMIResponse> createCalculateBMIResponse(CalculateBMIResponse value) {
        return new JAXBElement<CalculateBMIResponse>(_CalculateBMIResponse_QNAME, CalculateBMIResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateBMI }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bmiService.com/", name = "calculateBMI")
    public JAXBElement<CalculateBMI> createCalculateBMI(CalculateBMI value) {
        return new JAXBElement<CalculateBMI>(_CalculateBMI_QNAME, CalculateBMI.class, null, value);
    }

}
