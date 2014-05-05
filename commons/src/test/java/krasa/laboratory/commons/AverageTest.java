package krasa.laboratory.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AverageTest {

	@Test
	public void testGetAverage() throws Exception {
		Average average = new Average(2);
		assertEquals(0, average.getAverage());

		average.add(6);
		assertEquals(3, average.getAverage());

		average.add(8);
		assertEquals(7, average.getAverage());

		average.add(4);
		assertEquals(6, average.getAverage());

		average.add(5);
		assertEquals(4, average.getAverage());

		average.add(0);
		assertEquals(2, average.getAverage());
	}

	@Test
	public void testGetAverage2() throws Exception {
		Average average = new Average(10);
		assertEquals(0, average.getAverage());
		average.add(10);
		assertEquals(1, average.getAverage());
		average.add(10);
		assertEquals(2, average.getAverage());
		average.add(10);
		assertEquals(3, average.getAverage());
		average.add(10);
		assertEquals(4, average.getAverage());
		average.add(10);
		assertEquals(5, average.getAverage());
		average.add(10);
		assertEquals(6, average.getAverage());
		average.add(10);
		assertEquals(7, average.getAverage());
		average.add(10);
		assertEquals(8, average.getAverage());
		average.add(10);
		assertEquals(9, average.getAverage());
		average.add(10);
		assertEquals(10, average.getAverage());

		average.add(0);
		assertEquals(9, average.getAverage());
		average.add(0);
		assertEquals(8, average.getAverage());
		average.add(0);
		assertEquals(7, average.getAverage());
		average.add(0);
		assertEquals(6, average.getAverage());
		average.add(0);
		assertEquals(5, average.getAverage());
		average.add(0);
		assertEquals(4, average.getAverage());
		average.add(0);
		assertEquals(3, average.getAverage());
		average.add(0);
		assertEquals(2, average.getAverage());
		average.add(0);
		assertEquals(1, average.getAverage());
		average.add(0);
		assertEquals(0, average.getAverage());
	}
}
