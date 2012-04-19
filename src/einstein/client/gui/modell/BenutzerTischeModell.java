package einstein.client.gui.modell;

import java.util.List;

import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.Spieler;

/**
 * Das Modell fuer die Benutzer und Tische verwaltet die kombinierte Liste 
 * der Benutzer und Tische und unterstuetzt das Anlegen von Tischen sowie 
 * das Zuschauen und Spielen an Tischen.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class BenutzerTischeModell {
	
	protected BenutzerTischeViewInterface viewInterface = null;
	
	public BenutzerTischeModell(BenutzerTischeViewInterface viewInterface) {
		this.viewInterface = viewInterface;
	}
	
	/**
	 * Liefert die Liste aller anwesenden Benutzer.
	 * Eine Spieler-Instanz enthaelt ebenfalls eine evtl. TischNr,
	 * an welcher der Benutzer Zuschauer oder aktiver Spieler ist.
	 * 
	 * @return Liste anwesender Benutzer
	 */
	public List<Spieler> getAnwesendeSpieler() {
		return EinsteinSitzungsSynchronisierer.getAnwesendeSpieler();
	}
	
	/**
	 * Setzt den aktuellen Benutzer als Zuschauer an den
	 * angegebenen Tisch.
	 * 
	 * @param tableNo
	 */
	public void waehleTischAlsZuschauer(int tableNo) {
		verlasseAktuellenTisch();
		
		try {
			EinsteinSitzungsSynchronisierer.zuschauen(tableNo);
		} catch (SpielException e) {
			this.viewInterface.falschesSpiel(e);
		}
	}
	
	/**
	 * Setzt die aktuelle Benutzerin als Spielerin an den 
	 * angegebenen Tisch.
	 * 
	 * @param tableNo
	 */
	public void waehleTischAlsSpieler(int tableNo) {
		verlasseAktuellenTisch();
		
		try {
			EinsteinSitzungsSynchronisierer.einsteigen(tableNo);
		} catch (SpielException e) {
			this.viewInterface.falschesSpiel(e);
		}
	}
	
	/**
	 * Eroeffnet einen neuen Tisch und setzt den aktuellen Benutzer
	 * als Spieler an diesen.
	 * 
	 * @return TischNr. des neuen Tisches
	 */
	public Integer eroeffneNeuenTisch() {
		verlasseAktuellenTisch();
		
		try {
			boolean mitspieler = viewInterface.sucheMitspieler();
			return new Integer(EinsteinSitzungsSynchronisierer.eroeffneTisch(!mitspieler));
		} catch (SpielException ex) {
			this.viewInterface.falschesSpiel(ex);
		}	
		
		return null;
	}
	
	/**
	 * Die aktuelle Benutzerin verlaesst ihren aktuellen Tisch,
	 * sollte sie an einem sitzen.
	 */
	protected void verlasseAktuellenTisch() {
		try {
			EinsteinSitzungsSynchronisierer.aktuellenTischVerlassen();
		} catch (SpielException ex) {}
	}

}
