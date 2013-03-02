package krasa.laboratory.proxy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SpyCoreTest {

	@Test
	public void gettersOk() throws Exception {
		SpyCore core = new SpyCore();
		SomeClasss object = new SomeClasss();
		object.setOne("one");
		object.setTwo(2);
		object.setThree(3L);

		SomeClasss spy = core.createSpy(object);

		System.err.println(spy.getOne());
		System.err.println(spy.getTwo());
		System.err.println(spy.getThree());
		core.verifyAllGettersCalled(spy);
	}

	@Test
	public void notCalledGetters() throws Exception {
		SpyCore core = new SpyCore();
		SomeClasss object = new SomeClasss();

		SomeClasss spy = core.createSpy(object);

		try {
			core.verifyAllGettersCalled(spy);
		} catch (VerifyException e) {
			assertEquals(3, e.getVerify().getNumberOfFailed());
			System.out.println(e.toString());
		}
	}
}
