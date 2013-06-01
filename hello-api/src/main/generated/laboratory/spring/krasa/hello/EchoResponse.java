package laboratory.spring.krasa.hello;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for EchoResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EchoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OriginalMessage" type="{http://krasa.spring.laboratory/hello}genericString"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EchoResponse", propOrder = { "originalMessage" })
@XmlRootElement(name = "echoResponse")
public class EchoResponse {

	@XmlElement(name = "OriginalMessage", required = true)
	protected String originalMessage;

	/**
	 * Gets the value of the originalMessage property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOriginalMessage() {
		return originalMessage;
	}

	/**
	 * Sets the value of the originalMessage property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOriginalMessage(String value) {
		this.originalMessage = value;
	}

}
