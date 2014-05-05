package krasa.laboratory.commons;

import java.util.List;

/**
 * @author Vojtech Krasa
 */
public class OverlappingCheck {
	public static boolean isOverlapping(List<Range> mergedList, Range addedItem) {
		boolean overlap = true;
		for (Range item : mergedList) {
			overlap =
					item.start < addedItem.end && item.end > addedItem.start;
			if (overlap) {
				break;
			}
		}
		return overlap;
	}

	public static class Range {
		int start;
		int end;

		public Range(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
}
