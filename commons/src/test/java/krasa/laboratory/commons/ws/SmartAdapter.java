package krasa.laboratory.commons.ws;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.ws.client.core.WebServiceTemplate;


public abstract class SmartAdapter {

    WebServiceTemplate webServiceTemplate;
    String externalSystem;

    public void foo() {

    }

    @Required
    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    @Required
    public void setExternalSystem(String externalSystem) {
        this.externalSystem = externalSystem;
    }

    public String getExternalSystem() {
        return externalSystem;
    }

    public WebServiceTemplate getWebServiceTemplate() {
        return webServiceTemplate;
    }
}
