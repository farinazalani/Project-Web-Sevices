
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

    private final static QName _GenerateBodyFatReportResponse_QNAME = new QName("http://fitness.org/", "generateBodyFatReportResponse");
    private final static QName _GenerateBodyFatReport_QNAME = new QName("http://fitness.org/", "generateBodyFatReport");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fitness
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GenerateBodyFatReport }
     * 
     */
    public GenerateBodyFatReport createGenerateBodyFatReport() {
        return new GenerateBodyFatReport();
    }

    /**
     * Create an instance of {@link GenerateBodyFatReportResponse }
     * 
     */
    public GenerateBodyFatReportResponse createGenerateBodyFatReportResponse() {
        return new GenerateBodyFatReportResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerateBodyFatReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fitness.org/", name = "generateBodyFatReportResponse")
    public JAXBElement<GenerateBodyFatReportResponse> createGenerateBodyFatReportResponse(GenerateBodyFatReportResponse value) {
        return new JAXBElement<GenerateBodyFatReportResponse>(_GenerateBodyFatReportResponse_QNAME, GenerateBodyFatReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerateBodyFatReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fitness.org/", name = "generateBodyFatReport")
    public JAXBElement<GenerateBodyFatReport> createGenerateBodyFatReport(GenerateBodyFatReport value) {
        return new JAXBElement<GenerateBodyFatReport>(_GenerateBodyFatReport_QNAME, GenerateBodyFatReport.class, null, value);
    }

}
