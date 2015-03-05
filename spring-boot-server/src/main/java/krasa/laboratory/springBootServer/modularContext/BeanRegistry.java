package krasa.laboratory.springBootServer.modularContext;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BeanRegistry {
	public List<Bean> beans = new ArrayList<Bean>();

}
