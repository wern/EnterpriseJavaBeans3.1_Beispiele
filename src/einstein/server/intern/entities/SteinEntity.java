package einstein.server.intern.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import einstein.server.oeffentlich.Position;
import einstein.server.oeffentlich.Stein;

/**
 * Datenbankrepräsentation eines Spielsteins. Das Attribut id stellt eine systemweit 
 * indeutige, automatisch gegnerierte Kennung dar. Da Attribut nr gibt die Augenzahl
 * des Steins an und das Flag spielerA gibt an, ob es sich um einen Stein des
 * Spielers A am Spieltisch handelt oder nicht. Die aktuelle Position des Steins wird
 * über die eingebettete Klasse {@link einstein.server.oeffentlich.Position} festgehalten.
 * 
 * @author jlessner
 *
 */
@Table(name="STEIN")
@Entity public class SteinEntity implements Serializable {
	@Id @GeneratedValue private int id;
	private int nr;
	private boolean spielerA; // True, wenn der Stein Spieler A gehört
	@Embedded private Position position;
    @ManyToOne TischEntity tisch;

	public SteinEntity() {}
	public SteinEntity(int nr, boolean spielerA, TischEntity tisch) {
		this.nr = nr;
		this.spielerA = spielerA;
        this.tisch = tisch;
	}
	
	public boolean isSpielerA() { return spielerA; }
	public int getId() { return id; }
	public int getNr() { return nr; }
	public Position getPosition() { return position; }

	public void setPosition(Position p) { position = p; }
    
    public Stein kopieren() {
        Stein stein = new Stein();
        stein.id = id;
        stein.nr = nr;
        stein.spielerA = spielerA;
        stein.position = position;
        return stein;
    }
    
    public static List<Stein> kopieren(List<SteinEntity> liste) {
        List<Stein> ergebnis = new ArrayList<Stein>();
        for (SteinEntity se: liste) {
            ergebnis.add(se.kopieren());
        }
        return ergebnis;
    }
    
    public String toString() {
        return id + "/" + (spielerA ? "A" : "B") + nr + "(" + position.x + "," + position.y + ")";
    }
}
