package einstein.server.oeffentlich;

import javax.ejb.ApplicationException;

/** Wird im Falle allgemeiner Fehler bei der Spielerverwaltung geworfen */
@ApplicationException(rollback = true)
public class VerwaltungsException extends Exception {

	public VerwaltungsException() {}

	public VerwaltungsException(String message) {
		super(message);
	}
}
