package einstein.server.intern.entities;

import java.io.Serializable;

import javax.persistence.*;

import einstein.server.oeffentlich.Spieler;

/**
 * Datenbankrepräsentation eines registrierten Spielers. Die bidirektionalen Auslegung
 * der Beziehungen {@link #spieltAnA} und {@link #spieltAnB} dient lediglich der
 * optimierten Abfrage von Spielern in der Spielerübersicht des Einstein-Portals.
 * Ohne Rückrichtung von SpielerEntity zu TischEntity, ließe sich über JPA kein
 * Join aufbauen, um die Spieler und ihre Zuordnung zu Tischen mit einer einzigen
 * DB-Abfrage zu ermitteln. Das ginge zwar mit einer SQL-Abfrage, die aber hier
 * vermieden werden soll.
 * 
 * @author jlessner
 *
 */
@Table(name="SPIELER")
@Entity public class SpielerEntity implements Serializable {
	@Id private String name;
	private String passwort;
	private boolean anwesend;
	private int partienGespielt;
	private int partienGewonnen;

	@ManyToOne private TischEntity zuschauerAn;
    @OneToOne(mappedBy="spielerA") private TischEntity spieltAnA;
    @OneToOne(mappedBy="spielerB") private TischEntity spieltAnB;

	public SpielerEntity() {}
	public SpielerEntity(String name, String passwort) {
		this.name = name;
		this.passwort = passwort;
	}
	
    public String getName() { return name; }
	public String getPasswort() { return passwort; }
	public int getPartienGespielt() { return partienGespielt; }
	public int getPartienGewonnen() { return partienGewonnen; }
	public TischEntity getZuschauerAn() { return zuschauerAn; }

    public Integer spielt() {
        if (spieltAnA != null)
            return spieltAnA.getNr();
        if (spieltAnB != null)
            return spieltAnB.getNr();
        return null;
    }

	public void setPasswort(String pPasswort) { passwort = pPasswort; }
	public void setPartienGespielt(int p) { partienGespielt = p; }
	public void setPartienGewonnen(int p) { partienGewonnen = p; }
	public void setZuschauerAn(TischEntity pZuschauerAn) { zuschauerAn = pZuschauerAn; }
	public boolean isAnwesend() { return anwesend; }
	public void setAnwesend(boolean p) { anwesend = p; }
    public void setSpieltAnA(TischEntity p) { spieltAnA = p; }
    public void setSpieltAnB(TischEntity p) { spieltAnB = p; }

    public Spieler kopieren() {
        Spieler spieler = new Spieler();
        spieler.anwesend = anwesend;
        spieler.name = name;
        spieler.partienGespielt = partienGespielt;
        spieler.partienGewonnen = partienGewonnen;
        spieler.zuschauerAn = zuschauerAn == null ? null : zuschauerAn.getNr();
        spieler.spielerAn = spielt();
        return spieler;
    }
    
}
