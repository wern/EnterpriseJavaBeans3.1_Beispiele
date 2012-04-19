package einstein.server.oeffentlich;

import java.io.Serializable;

/** Representation eines Zugs. Das Attribut steinId gibt die interne ID
 * des zu bewegenden Steins an, das Attribut ziel gibt die Zielposition
 * an, zu welcher der Stein bewegt werden soll.
 */
public class Zug implements Serializable {
	private int steinId;
	private Position ziel;

	public Zug() {}

	public Zug(int steinId, Position ziel) {
		this.steinId = steinId;
		this.ziel = ziel;
	}
	
	public int getSteinId() { return steinId; }
	public Position getZiel() { return ziel; }
    
    public String toString() { return steinId + "->" + ziel.x + "," + ziel.y; }
}
