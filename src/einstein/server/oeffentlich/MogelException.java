package einstein.server.oeffentlich;

/** Unchecked Exception, die geworfen wird, wenn ein Spieler
 * einen unerlaubten Zug versucht. Das sollte normalerweise bei
 * Verwendung des beiliegenden grafischen Clients gar nicht möglich
 * sein. Es könnte aber jemand auf die Idee kommen, den Client zu
 * manipulieren, um ungültige Züge durchzuführen. Außerdem könnte
 * der Client ja auch einen Bug haben.
 */
public class MogelException extends RuntimeException {
    public MogelException() {}
    public MogelException(String meldung) { super(meldung); }
}
