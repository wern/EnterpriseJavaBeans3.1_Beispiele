package einstein.server.oeffentlich;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class SpielException extends Exception {

	public SpielException() {
		super();
	}

	public SpielException(String pArg0, Throwable pArg1) {
		super(pArg0, pArg1);
	}

	public SpielException(String pArg0) {
		super(pArg0);
	}

	public SpielException(Throwable pArg0) {
		super(pArg0);
	}

}
