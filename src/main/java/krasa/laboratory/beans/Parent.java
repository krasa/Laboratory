package krasa.laboratory.beans;

import krasa.laboratory.service.HelloService;

import org.springframework.beans.factory.annotation.Autowired;

public class Parent extends AbstractBean {
	private String foo;
	@Autowired
	HelloService helloService;
	ChildBean childBean;

	public Parent() {
	}

	public ChildBean getChildBean() {
		return childBean;
	}

	public void setChildBean(ChildBean childBean) {
		this.childBean = childBean;
	}

	public Parent(String foo) {
		this.foo = foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public void doSomething() {
		helloService.hello();
	}

	public String getFoo() {
		return foo;
	}

}
