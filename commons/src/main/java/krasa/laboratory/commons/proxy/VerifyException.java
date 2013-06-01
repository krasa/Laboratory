package krasa.laboratory.commons.proxy;

public class VerifyException extends RuntimeException {
	private final VerifyResult verify;

	public VerifyException(String resultAsString, VerifyResult verify) {
		super(resultAsString);
		this.verify = verify;
	}

	public VerifyResult getVerify() {
		return verify;
	}
}
