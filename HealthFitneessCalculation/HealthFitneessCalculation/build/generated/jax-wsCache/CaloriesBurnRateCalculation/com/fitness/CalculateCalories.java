
package com.fitness;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for calculateCalories complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="calculateCalories">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="weight_kg" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="duration_min" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="activity_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="height_cm" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "calculateCalories", propOrder = {
    "weightKg",
    "durationMin",
    "activityType",
    "age",
    "heightCm",
    "gender"
})
public class CalculateCalories {

    @XmlElement(name = "weight_kg")
    protected double weightKg;
    @XmlElement(name = "duration_min")
    protected double durationMin;
    @XmlElement(name = "activity_type")
    protected String activityType;
    protected int age;
    @XmlElement(name = "height_cm")
    protected double heightCm;
    protected String gender;

    /**
     * Gets the value of the weightKg property.
     * 
     */
    public double getWeightKg() {
        return weightKg;
    }

    /**
     * Sets the value of the weightKg property.
     * 
     */
    public void setWeightKg(double value) {
        this.weightKg = value;
    }

    /**
     * Gets the value of the durationMin property.
     * 
     */
    public double getDurationMin() {
        return durationMin;
    }

    /**
     * Sets the value of the durationMin property.
     * 
     */
    public void setDurationMin(double value) {
        this.durationMin = value;
    }

    /**
     * Gets the value of the activityType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityType() {
        return activityType;
    }

    /**
     * Sets the value of the activityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityType(String value) {
        this.activityType = value;
    }

    /**
     * Gets the value of the age property.
     * 
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the value of the age property.
     * 
     */
    public void setAge(int value) {
        this.age = value;
    }

    /**
     * Gets the value of the heightCm property.
     * 
     */
    public double getHeightCm() {
        return heightCm;
    }

    /**
     * Sets the value of the heightCm property.
     * 
     */
    public void setHeightCm(double value) {
        this.heightCm = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

}
