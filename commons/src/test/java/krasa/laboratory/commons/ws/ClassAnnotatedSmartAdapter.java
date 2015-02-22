package krasa.laboratory.commons.ws;

import org.springframework.stereotype.Component;

@Component
@Adapter(key = "classAnnotatedSmartAdapter", marshaller = "testAdapterMarshaller")
public class ClassAnnotatedSmartAdapter extends SmartAdapter {

}
