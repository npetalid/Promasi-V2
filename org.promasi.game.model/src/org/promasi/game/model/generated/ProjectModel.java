//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.15 at 05:36:54 PM CEST 
//


package org.promasi.game.model.generated;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="_projectDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_overallProgress" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="_projectPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="_difficultyLevel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="_projectTasks">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="value" type="{}projectTaskModel" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="_taskBridges" type="{}taskBridgeModel" maxOccurs="unbounded" minOccurs="0"/>
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
    "projectDuration",
    "overallProgress",
    "projectPrice",
    "difficultyLevel",
    "projectTasks",
    "taskBridges"
})
@XmlRootElement(name = "projectModel")
public class ProjectModel
    extends AGameModel
{

    @XmlElement(name = "_name")
    protected String name;
    @XmlElement(name = "_description")
    protected String description;
    @XmlElement(name = "_projectDuration")
    protected int projectDuration;
    @XmlElement(name = "_overallProgress")
    protected double overallProgress;
    @XmlElement(name = "_projectPrice")
    protected double projectPrice;
    @XmlElement(name = "_difficultyLevel")
    protected double difficultyLevel;
    @XmlElement(name = "_projectTasks", required = true)
    protected ProjectModel.ProjectTasks projectTasks;
    @XmlElement(name = "_taskBridges", nillable = true)
    protected List<TaskBridgeModel> taskBridges;

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
     * Gets the value of the projectDuration property.
     * 
     */
    public int getProjectDuration() {
        return projectDuration;
    }

    /**
     * Sets the value of the projectDuration property.
     * 
     */
    public void setProjectDuration(int value) {
        this.projectDuration = value;
    }

    /**
     * Gets the value of the overallProgress property.
     * 
     */
    public double getOverallProgress() {
        return overallProgress;
    }

    /**
     * Sets the value of the overallProgress property.
     * 
     */
    public void setOverallProgress(double value) {
        this.overallProgress = value;
    }

    /**
     * Gets the value of the projectPrice property.
     * 
     */
    public double getProjectPrice() {
        return projectPrice;
    }

    /**
     * Sets the value of the projectPrice property.
     * 
     */
    public void setProjectPrice(double value) {
        this.projectPrice = value;
    }

    /**
     * Gets the value of the difficultyLevel property.
     * 
     */
    public double getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Sets the value of the difficultyLevel property.
     * 
     */
    public void setDifficultyLevel(double value) {
        this.difficultyLevel = value;
    }

    /**
     * Gets the value of the projectTasks property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectModel.ProjectTasks }
     *     
     */
    public ProjectModel.ProjectTasks getProjectTasks() {
        return projectTasks;
    }

    /**
     * Sets the value of the projectTasks property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectModel.ProjectTasks }
     *     
     */
    public void setProjectTasks(ProjectModel.ProjectTasks value) {
        this.projectTasks = value;
    }

    /**
     * Gets the value of the taskBridges property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taskBridges property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaskBridges().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaskBridgeModel }
     * 
     * 
     */
    public List<TaskBridgeModel> getTaskBridges() {
        if (taskBridges == null) {
            taskBridges = new ArrayList<TaskBridgeModel>();
        }
        return this.taskBridges;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="value" type="{}projectTaskModel" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class ProjectTasks {

        protected List<ProjectModel.ProjectTasks.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProjectModel.ProjectTasks.Entry }
         * 
         * 
         */
        public List<ProjectModel.ProjectTasks.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<ProjectModel.ProjectTasks.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="value" type="{}projectTaskModel" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected String key;
            protected ProjectTaskModel value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKey(String value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link ProjectTaskModel }
             *     
             */
            public ProjectTaskModel getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link ProjectTaskModel }
             *     
             */
            public void setValue(ProjectTaskModel value) {
                this.value = value;
            }

        }

    }

}
