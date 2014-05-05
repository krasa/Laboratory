package krasa.laboratory.commons;

import org.junit.Test;

import static java.util.Arrays.asList;
import static krasa.laboratory.commons.OverlappingCheck.isOverlapping;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OverlappingCheckTest {

	@Test
	public void testIsOverlapping() throws Exception {
		assertFalse(isOverlapping(asList(new OverlappingCheck.Range(3, 4), new OverlappingCheck.Range(6, 7)), new OverlappingCheck.Range(4, 6)));

		assertTrue(isOverlapping(asList(new OverlappingCheck.Range(3, 5)), new OverlappingCheck.Range(4, 6)));
		assertTrue(isOverlapping(asList(new OverlappingCheck.Range(5, 7)), new OverlappingCheck.Range(4, 6)));
		assertTrue(isOverlapping(asList(new OverlappingCheck.Range(3, 7)), new OverlappingCheck.Range(4, 6)));
		assertTrue(isOverlapping(asList(new OverlappingCheck.Range(5, 5)), new OverlappingCheck.Range(4, 6)));

		assertTrue(isOverlapping(asList(new OverlappingCheck.Range(4, 6)), new OverlappingCheck.Range(4, 6)));
		assertTrue(isOverlapping(asList(new OverlappingCheck.Range(3, 4), new OverlappingCheck.Range(3, 5), new OverlappingCheck.Range(6, 7)), new OverlappingCheck.Range(4, 6)));
	}
}