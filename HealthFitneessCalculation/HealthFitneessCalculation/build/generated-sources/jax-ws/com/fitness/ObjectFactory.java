
package com.fitness;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fitness package. 
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

    private final static QName _CalculateCaloriesResponse_QNAME = new QName("http://cal.org/", "calculateCaloriesResponse");
    private final static QName _CalculateCalories_QNAME = new QName("http://cal.org/", "calculateCalories");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fitness
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CalculateCaloriesResponse }
     * 
     */
    public CalculateCaloriesResponse createCalculateCaloriesResponse() {
        return new CalculateCaloriesResponse();
    }

    /**
     * Create an instance of {@link CalculateCalories }
     * 
     */
    public CalculateCalories createCalculateCalories() {
        return new CalculateCalories();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateCaloriesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cal.org/", name = "calculateCaloriesResponse")
    public JAXBElement<CalculateCaloriesResponse> createCalculateCaloriesResponse(CalculateCaloriesResponse value) {
        return new JAXBElement<CalculateCaloriesResponse>(_CalculateCaloriesResponse_QNAME, CalculateCaloriesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateCalories }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cal.org/", name = "calculateCalories")
    public JAXBElement<CalculateCalories> createCalculateCalories(CalculateCalories value) {
        return new JAXBElement<CalculateCalories>(_CalculateCalories_QNAME, CalculateCalories.class, null, value);
    }

}
