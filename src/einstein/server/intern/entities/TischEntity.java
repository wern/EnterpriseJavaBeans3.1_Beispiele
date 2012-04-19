package einstein.server.intern.entities;
import java.io.Serializable;

import javax.persistence.*;

import einstein.server.oeffentlich.SpielStatus;

/**
 * Datenbankrepräsentation eines Spieltischs. Die Spieler sind über eine
 * bidrektionale Beziehung assoziiert (Begründung siehe {@link SpielerEntity}.
 * Um die Beziehungen möglichst fehlerfrei in der Applikation nutzen zu können,
 * pflegen die Funktionen {@link #setSpielerA(SpielerEntity)} und {@link
 * #setSpielerA(SpielerEntity)} beide Seiten der Beziehungen. Das ist etwas
 * trickreich, damit es auch null-safe ist.<br>
 * Bei einer Computerpartie ist das Flag computerGegner gesetzt und spielerA
 * referenziert den Spieler. Bei einer normalen Partie referenziert spielerA
 * den ersten und spielerB den zweiten Spieler.
 * 
 * @author jlessner
 */
@Table(name="TISCH")
@Entity public class TischEntity implements Serializable {
	@Id private int nr;
    boolean computerGegner;
	@OneToOne private SpielerEntity spielerA;
    @OneToOne private SpielerEntity spielerB;
	
    @OneToOne private SteinEntity letzterGezogenerStein;
	private int letzteZugNr;
	private int letzterWuerfelwurf;
	@Column(nullable=false) @Enumerated(EnumType.STRING) private SpielStatus status;
	
	public TischEntity() {}
	public TischEntity(int nr, boolean computerGegner) {
		this.nr = nr;
        this.computerGegner = computerGegner;
	}
	
	public int getNr() { return nr; }
    public boolean isComputerGegner() { return computerGegner; }
	public SteinEntity getLetzterGezogenerStein() { return letzterGezogenerStein; }
	public int getLetzterWuerfelwurf() { return letzterWuerfelwurf; }
	public int getLetzteZugNr() { return letzteZugNr; }
	public SpielerEntity getSpielerA() { return spielerA; }
	public SpielerEntity getSpielerB() { return spielerB; }
	public SpielStatus getStatus() { return status; }

	public void setLetzterGezogenerStein(SteinEntity p) { letzterGezogenerStein = p; }
	public void setLetzterWuerfelwurf(int p) { letzterWuerfelwurf = p; }
	public void setLetzteZugNr(int pLetzteZugNr) { letzteZugNr = pLetzteZugNr; }
	public void setStatus(SpielStatus p) { status = p; }

    public boolean belegt() {
        return (spielerA != null && (spielerB != null || computerGegner));
    }
    
    public void setSpielerA(SpielerEntity p) {
        if (spielerA != null)
            spielerA.setSpieltAnA(null);
        spielerA = p;
        if (p != null)
            p.setSpieltAnA(this);
    }
    
    public void setSpielerB(SpielerEntity p) {
        if (spielerB != null)
            spielerB.setSpieltAnB(null);
        spielerB = p;
        if (p != null)
            p.setSpieltAnB(this);
    }
}
