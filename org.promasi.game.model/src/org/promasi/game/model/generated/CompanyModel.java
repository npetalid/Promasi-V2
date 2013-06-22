//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.15 at 05:36:54 PM CEST 
//


package org.promasi.game.model.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{}aGameModel">
 *       &lt;sequence>
 *         &lt;element name="_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_startTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_endTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="_itDepartment" type="{}departmentModel" minOccurs="0"/>
 *         &lt;element name="_budget" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="_prestigePoints" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "description",
    "startTime",
    "endTime",
    "itDepartment",
    "budget",
    "prestigePoints"
})
@XmlRootElement(name = "companyModel")
public class CompanyModel
    extends AGameModel
{

    @XmlElement(name = "_name")
    protected String name;
    @XmlElement(name = "_description")
    protected String description;
    @XmlElement(name = "_startTime")
    protected String startTime;
    @XmlElement(name = "_endTime")
    protected String endTime;
    @XmlElement(name = "_itDepartment")
    protected DepartmentModel itDepartment;
    @XmlElement(name = "_budget")
    protected double budget;
    @XmlElement(name = "_prestigePoints")
    protected double prestigePoints;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the itDepartment property.
     * 
     * @return
     *     possible object is
     *     {@link DepartmentModel }
     *     
     */
    public DepartmentModel getItDepartment() {
        return itDepartment;
    }

    /**
     * Sets the value of the itDepartment property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartmentModel }
     *     
     */
    public void setItDepartment(DepartmentModel value) {
        this.itDepartment = value;
    }

    /**
     * Gets the value of the budget property.
     * 
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the value of the budget property.
     * 
     */
    public void setBudget(double value) {
        this.budget = value;
    }

    /**
     * Gets the value of the prestigePoints property.
     * 
     */
    public double getPrestigePoints() {
        return prestigePoints;
    }

    /**
     * Sets the value of the prestigePoints property.
     * 
     */
    public void setPrestigePoints(double value) {
        this.prestigePoints = value;
    }

}
