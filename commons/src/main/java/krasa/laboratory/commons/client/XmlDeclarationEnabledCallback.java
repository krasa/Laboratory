package krasa.laboratory.commons.client;

import java.io.IOException;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

public class XmlDeclarationEnabledCallback implements WebServiceMessageCallback {

	@Override
	public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {
		enableXmlDeclarationInRequest(webServiceMessage);
	}

	protected void enableXmlDeclarationInRequest(WebServiceMessage webServiceMessage) {
		SOAPMessage soapMessage = ((SaajSoapMessage) webServiceMessage).getSaajMessage();
		try {
			soapMessage.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "true");
		} catch (SOAPException e) {
			throw new RuntimeException(e);
		}
	}
}
