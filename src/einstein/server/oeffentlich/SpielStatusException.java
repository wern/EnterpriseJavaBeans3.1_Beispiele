package einstein.server.oeffentlich;

import javax.ejb.ApplicationException;

/** Wird geworfen, wenn eine Operation ausgef�hrt wird, die
 * im aktuellen Status der Partie f�r den Aufrufenden grade
 * nicht erlaubt ist. Ist z.B. Spieler A mit W�rfeln dran,
 * kann er in diesem Moment keinen Zug machen.
 */
@ApplicationException(rollback=true)
public class SpielStatusException extends Exception {
	public SpielStatusException() { super(); }
	public SpielStatusException(String pArg0) { super(pArg0); }
}
