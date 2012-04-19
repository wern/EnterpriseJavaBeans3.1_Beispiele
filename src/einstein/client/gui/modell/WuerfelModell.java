package einstein.client.gui.modell;

import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStatusException;

/**
 * Das Modell fuer einen Wuerfel haelt den zugehoerigen Spieler sowie die
 * Augenzahl und bietet Funktionalitaet zum Abfragen des Spielers und
 * zum Wuerfeln.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class WuerfelModell {
	
	protected WuerfelViewInterface viewInterface = null;
	protected SpielbrettModell spielbrettModell = null;
	protected boolean playerA = false;
	protected int count = 1;
	protected String spielerName = "Kein Spieler";

	public WuerfelModell(boolean playerA, WuerfelViewInterface viewInterface, SpielbrettModell spielbrettModell) {
		this.playerA = playerA;
		this.viewInterface = viewInterface;
		this.spielbrettModell = spielbrettModell;
	}
	
	public boolean isPlayerA() {
		return this.playerA;
	}
	
	public void setSpielerName(String name) {
		spielerName = name;
	}
	
	public String getSpielerName() {
		return this.spielerName;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return this.count;
	}
	
	/**
	 * Liefert true, wenn der Wuerfel vom falschen Benutzer verwendet wird.
	 * 
	 * @return true, wenn falscher Benutzer
	 */
	protected boolean isFalscherBenutzer() {
		if (isPlayerA()) {
			return (! spielbrettModell.getUserA().equals(LoginModell.getBenutzerName()));
		} else {
			return (! spielbrettModell.getUserB().equals(LoginModell.getBenutzerName()));
		}
	}
	
	/**
	 * Wenn der Wuerfel vom zugeordneten Benutzer bedient wird,
	 * dann wird gewuerfelt (ueber einen Zugriff auf die entfernte EJB).
	 * Andernfalls wird eine entsprechende Meldung an den Benutzer initiiert.
	 */
	public void wuerfle() {
		if (isFalscherBenutzer()) {	
			viewInterface.falscherWuerfel();
			return;
		}
		
		try {
			int count = EinsteinSitzungsSynchronisierer.wuerfeln();
			this.setCount(count);
		} catch (SpielException e) { 
			viewInterface.falschesSpiel(e);
		} catch (SpielStatusException e) {
			viewInterface.falscherStatus(e);
		}
	}

}
