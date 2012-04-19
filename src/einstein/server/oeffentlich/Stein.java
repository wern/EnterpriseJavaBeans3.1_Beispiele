package einstein.server.oeffentlich;

import java.io.Serializable;

/** Leichtgewichtige Repräsentation eines SteinEntity, das 
 * zur Weitergabe an einen Client geeignet ist.
 * 
 * @author jlessner
 */
public class Stein implements Serializable {
    public int id;
    public int nr;
    public boolean spielerA;
    public Position position;

    public Stein() {}
    
    public Stein(Stein pStein) {
    	this.id = pStein.id;
    	this.nr = pStein.nr;
    	this.spielerA = pStein.spielerA;
    	this.position = new Position(pStein.position.x, pStein.position.y);
	}

	public String toString() {
        return (spielerA ? "A" : "B") + nr + ":" + position.x + "," + position.y + "(" + id + ")";
    }
}
