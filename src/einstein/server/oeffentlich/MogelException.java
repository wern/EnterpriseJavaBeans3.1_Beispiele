package einstein.server.oeffentlich;

/** Unchecked Exception, die geworfen wird, wenn ein Spieler
 * einen unerlaubten Zug versucht. Das sollte normalerweise bei
 * Verwendung des beiliegenden grafischen Clients gar nicht m�glich
 * sein. Es k�nnte aber jemand auf die Idee kommen, den Client zu
 * manipulieren, um ung�ltige Z�ge durchzuf�hren. Au�erdem k�nnte
 * der Client ja auch einen Bug haben.
 */
public class MogelException extends RuntimeException {
    public MogelException() {}
    public MogelException(String meldung) { super(meldung); }
}
