package krasa.laboratory.commons.ws;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;
import org.springframework.ws.client.core.WebServiceTemplate;

public abstract class SmartAdapter implements InitializingBean {

	private WebServiceTemplate webServiceTemplate;
	@AdapterProperty("externalSystem")
	private String externalSystem;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(webServiceTemplate);
		Assert.notNull(externalSystem);
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
