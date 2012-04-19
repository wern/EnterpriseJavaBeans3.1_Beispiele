package einstein.server.oeffentlich;

import java.io.Serializable;

/**
 * Gibt den aktuellen Spielstand einer laufenden Partie wieder, um optimierte
 * Aktualisierungen an die Spieler und Zuschauer weiterzuleiten. Ist der Status
 * A_WUERFELT, gibt zuletztGewuerfelt den letzten Würfelwurf von Spiele B und
 * letzterZug den letzten Zug von Spieler B an. letzteZugNr ist die Nummer des
 * letzten Zugs von Spieler B. Ist der Status A_AM_ZUG, gibt zuletztGewurfelt
 * den grade aktuellen Würfelwurf von Spieler A an. letzterZug und letzteZugNr
 * sind noch unverändert. Hat Spieler A seinen Zug, gemacht, ist der Folgestatus
 * im Normalfall B_WUERFELT und es gilt das oben stehende entsprechend umgekehrt.
 * Alternativ kann die Partie einen Endstatus erreicht haben - dann ergeben sich
 * keine weiteren Änderungen mehr. Es muss auf den Beginn einer neuen Partie
 * gewartet werden.
 * 
 * @author jlessner
 */
public class SpielStand implements Serializable {
	private int zuletztGewuerfelt;
    private int letzteZugNr;
	private Zug letzterZug;
	private SpielStatus status;

	public SpielStand() {}
	
	public SpielStand(int zuletztGewuerfelt, int letzteZugNr,
                      Zug letzterZug, SpielStatus status) {
		this.zuletztGewuerfelt = zuletztGewuerfelt;
        this.letzteZugNr = letzteZugNr;
		this.letzterZug = letzterZug;
		this.status = status;
	}
	
	public Zug getLetzterZug() { return letzterZug; }
	public SpielStatus getStatus() { return status; }
	public int getZuletztGewuerfelt() { return zuletztGewuerfelt; }
    public int getLetzteZugNr() { return letzteZugNr; }
    
    public String toString() {
        return status + "/" + zuletztGewuerfelt + "/" + letzteZugNr + ":" + letzterZug;
    }
	
}
