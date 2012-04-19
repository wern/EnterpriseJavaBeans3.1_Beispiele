package einstein.server.intern;

import java.util.Date;
import java.util.Random;

import einstein.server.intern.entities.SteinEntity;
import einstein.server.oeffentlich.Position;

/** Kleines Helferlein rund um das Einstein-Spielbrett. Die Klasse
 * ist z.B. der alleinige Lieferant für Würfelergebnisse (siehe Funktion
 * {@link #zufallsZahl16()} und liefert die Startpositionen für Spielsteine.
 * 
 * @author jlessner
 *
 */
public class SpielBrettHelfer {
	
    /** Startpositionen für die Spielsteine des Spielers A. Die Positionen für
     * einen B-Spieler ergeben sich durch Spiegelung.
     */
	public static int[][] STARTPOSITIONEN = { {1,3}, {1,4}, {2,4}, {1,5}, {2,5}, {3,5} };

	private static Random rand = new Random(new Date().getTime());

	public static int zufallsZahl(int max) {
		return rand.nextInt(max);
	}

	public static int zufallsZahl16() {
		return zufallsZahl(6) + 1;
	}
	
    public static boolean imZiel(SteinEntity stein) {
        return imZiel(stein.getPosition(), stein.isSpielerA());
    }
	
    public static boolean imZiel(Position position, boolean spielerA) {
        return spielerA ?
            position.gleich(5, 1) :
            position.gleich(1, 5);
    }
	
}
