
package com.userInfoClacAge;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.userInfoClacAge package. 
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

    private final static QName _DisplayInfoResponse_QNAME = new QName("http://userInformation.com/", "displayInfoResponse");
    private final static QName _DisplayInfo_QNAME = new QName("http://userInformation.com/", "displayInfo");
    private final static QName _CalculateAgeResponse_QNAME = new QName("http://userInformation.com/", "calculateAgeResponse");
    private final static QName _CalculateAge_QNAME = new QName("http://userInformation.com/", "calculateAge");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.userInfoClacAge
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CalculateAgeResponse }
     * 
     */
    public CalculateAgeResponse createCalculateAgeResponse() {
        return new CalculateAgeResponse();
    }

    /**
     * Create an instance of {@link CalculateAge }
     * 
     */
    public CalculateAge createCalculateAge() {
        return new CalculateAge();
    }

    /**
     * Create an instance of {@link DisplayInfoResponse }
     * 
     */
    public DisplayInfoResponse createDisplayInfoResponse() {
        return new DisplayInfoResponse();
    }

    /**
     * Create an instance of {@link DisplayInfo }
     * 
     */
    public DisplayInfo createDisplayInfo() {
        return new DisplayInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DisplayInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://userInformation.com/", name = "displayInfoResponse")
    public JAXBElement<DisplayInfoResponse> createDisplayInfoResponse(DisplayInfoResponse value) {
        return new JAXBElement<DisplayInfoResponse>(_DisplayInfoResponse_QNAME, DisplayInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DisplayInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://userInformation.com/", name = "displayInfo")
    public JAXBElement<DisplayInfo> createDisplayInfo(DisplayInfo value) {
        return new JAXBElement<DisplayInfo>(_DisplayInfo_QNAME, DisplayInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateAgeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://userInformation.com/", name = "calculateAgeResponse")
    public JAXBElement<CalculateAgeResponse> createCalculateAgeResponse(CalculateAgeResponse value) {
        return new JAXBElement<CalculateAgeResponse>(_CalculateAgeResponse_QNAME, CalculateAgeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateAge }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://userInformation.com/", name = "calculateAge")
    public JAXBElement<CalculateAge> createCalculateAge(CalculateAge value) {
        return new JAXBElement<CalculateAge>(_CalculateAge_QNAME, CalculateAge.class, null, value);
    }

}
