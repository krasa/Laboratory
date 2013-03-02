package krasa.laboratory.proxy;

public class VerifyResult {
	private Object underlying;
	protected int numberOfFailed;
	protected StringBuilder stringBuilder;

	public VerifyResult(Object underlying) {
		this.underlying = underlying;
	}

	public void failed(String name) {
		if (numberOfFailed > 0) {
			stringBuilder.append(", ");

		}
		if (stringBuilder == null) {
			stringBuilder = new StringBuilder();
			stringBuilder.append(underlying.getClass()).append(" - offending methods: ");
		}
		stringBuilder.append(name);
		numberOfFailed++;
	}

	public String getResultAsString() {
		return stringBuilder.toString();
	}

	public boolean isFailed() {
		return numberOfFailed > 0;
	}

	public int getNumberOfFailed() {
		return numberOfFailed;
	}
}
