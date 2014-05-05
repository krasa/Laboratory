package krasa.laboratory.commons;

public class Average {

	private int index = 0;
	private int values[];

	public Average(int size) {
		values = new int[size];
	}

	public void add(int i) {
		values[index] = i;
		if (++index >= values.length) {
			index = 0;
		}
	}

	public int getAverage() {
		int i = 0;
		for (int value : values) {
			i += value;
		}
		return i / values.length;
	}
}
