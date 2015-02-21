package krasa.laboratory.commons.ws;

import org.springframework.stereotype.Component;


@Component
@Adapter(name = "testAdapter", marshaller = "testAdapterMarshaller")
public class ClassAnnotatedSmartAdapter extends SmartAdapter {

}
