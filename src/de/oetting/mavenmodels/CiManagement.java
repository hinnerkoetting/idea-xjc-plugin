
package de.oetting.mavenmodels;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         
 *         The <code>&lt;CiManagement&gt;</code> element contains informations required to the
 *         continuous integration system of the project.
 *         
 *       
 * 
 * <p>Java class for CiManagement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CiManagement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="system" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="notifiers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="notifier" type="{http://maven.apache.org/POM/4.0.0}Notifier" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CiManagement", namespace = "http://maven.apache.org/POM/4.0.0", propOrder = {

})
public class CiManagement {

    @XmlElement(namespace = "http://maven.apache.org/POM/4.0.0")
    protected String system;
    @XmlElement(namespace = "http://maven.apache.org/POM/4.0.0")
    protected String url;
    @XmlElement(namespace = "http://maven.apache.org/POM/4.0.0")
    protected CiManagement.Notifiers notifiers;

    /**
     * Gets the value of the system property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystem() {
        return system;
    }

    /**
     * Sets the value of the system property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystem(String value) {
        this.system = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the notifiers property.
     * 
     * @return
     *     possible object is
     *     {@link CiManagement.Notifiers }
     *     
     */
    public CiManagement.Notifiers getNotifiers() {
        return notifiers;
    }

    /**
     * Sets the value of the notifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link CiManagement.Notifiers }
     *     
     */
    public void setNotifiers(CiManagement.Notifiers value) {
        this.notifiers = value;
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
     *         &lt;element name="notifier" type="{http://maven.apache.org/POM/4.0.0}Notifier" maxOccurs="unbounded" minOccurs="0"/>
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
        "notifier"
    })
    public static class Notifiers {

        @XmlElement(namespace = "http://maven.apache.org/POM/4.0.0")
        protected List<Notifier> notifier;

        /**
         * Gets the value of the notifier property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the notifier property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getNotifier().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Notifier }
         * 
         * 
         */
        public List<Notifier> getNotifier() {
            if (notifier == null) {
                notifier = new ArrayList<Notifier>();
            }
            return this.notifier;
        }

    }

}
