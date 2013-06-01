package krasa.laboratory.server.beans;

import krasa.laboratory.server.service.HelloService;

import org.springframework.beans.factory.annotation.Autowired;

public class ChildBean extends AbstractBean {
	private String foo;

	@Autowired
	HelloService helloService;

	public ChildBean() {
	}

	public ChildBean(String foo) {
		this.foo = foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public String getFoo() {
		return foo;
	}

}
