package help.lixin.framework.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class VerifyCodeException extends AuthenticationException {
	private static final long serialVersionUID = 2150653403756672866L;

	public VerifyCodeException(String msg) {
		super(msg);
	}
}
