package einstein.server.oeffentlich;
import java.io.Serializable;
import java.util.List;

/** Client-taugliche Repr�sentation der aktuellen Spielsituation an einem Tisch.
 * Die Datenstruktur enth�lt neben den Spielernamen und dem Spielstatus auch die
 * komplette Positionierung aller Spielsteine. Es handelt sich daher um eine relativ
 * gro�e Struktur, die im Idealfall nur zu Beginn einer Partie abgefragt wird, um
 * anschlie�end �ber {@link SpielStand} nur noch leichtgewichtige Aktualisierungen
 * anzufordern. Siehe {@link EinsteinSitzung} f�r die Anwendung.
 */
public class Partie implements Serializable {
	private List<Stein> aufstellung;
	private SpielStand stand;
	private String spielerA;
	private String spielerB;
    private boolean computerGegner;
	
	public List<Stein> getAufstellung() { return aufstellung; }
	public String getSpielerA() { return spielerA; }
	public String getSpielerB() { return spielerB; }
	public SpielStand getStand() { return stand; }
    public SpielStatus getStatus() { return stand.getStatus(); }
    public boolean isComputerGegner() { return computerGegner; }

	public Partie() {}

	public Partie(String spielerA, String spielerB, boolean computerGegner,
                  SpielStand stand, List<Stein> aufstellung) {
        this.spielerA = spielerA;
        this.spielerB = spielerB;
        this.computerGegner = computerGegner;
		this.stand = stand;
		this.aufstellung = aufstellung;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(stand.getStatus() + "/" + spielerA + "/" + spielerB + " ");
	    for (Stein stein : getAufstellung()) {
	        s.append(stein.toString() + " ");
	    }
	    return s.toString();
	}

}
