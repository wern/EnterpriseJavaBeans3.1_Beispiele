package einstein.client.gui.modell;

import java.util.Enumeration;

import einstein.client.gui.modell.status.StatusUebersetzerFactory;
import einstein.client.gui.modell.status.StatusUebersetzerInterface;
import einstein.server.oeffentlich.Partie;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStand;
import einstein.server.oeffentlich.SpielStatus;
import einstein.server.oeffentlich.SpielStatusException;

/**
 * Das Modell fuer das Spielbrett haelt die Namen der Spieler und
 * die TischNr und bietet Funktionalitaet zum Abfragen und Aendern 
 * des Spielstatus.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class SpielbrettModell {

	protected String userA, userB, status = null;
	protected final static String leererBenutzerName = "<noch offen>";
	protected final static String computerGegnerName = "Computer";
	protected StatusUebersetzerFactory statusFactory = null;
	protected Integer tischNr = null;
	protected SpielbrettViewInterface viewInterface = null;
	protected int letzteZugNr = 0;
	protected boolean isComputerGegner = false;
	
	public SpielbrettModell(SpielbrettViewInterface viewInterface) {
		statusFactory = new StatusUebersetzerFactory();
		this.viewInterface = viewInterface;
	}
	
	/**
	 * Holt den Spielstand vom Server, prueft, ob eine Aktualisierung
	 * des Spielstands anstelle einer Aktualisierung der Partie ausreicht
	 * und setzt ggf. den Status.
	 * 
	 * @return null, wenn eine Aktualisierung des Spielstands nicht ausreicht;
	 * sonst eine aktuelle SpielStand-Instanz.
	 * @throws SpielStatusException
	 */
	public SpielStand updateSpielStand() throws SpielStatusException {
		SpielStand stand, stand_r = null;
		try {
			stand = EinsteinSitzungsSynchronisierer.standAbfragen();
		} catch (SpielException ex) {
			status = null;
			return null;
		}
		
		if ((stand.getStatus() == null) || (stand.getLetzterZug() == null)) {
			return null;
		}		
		if (this.spielVorbei(stand.getStatus())) {
			return null;
		}
		
		stand_r = null;
		if ((stand.getLetzteZugNr() == letzteZugNr + 1) || (stand.getLetzteZugNr() == letzteZugNr)) {
			if (letzteZugNr != 0) {
				stand_r = stand;
				setStatus(stand.getStatus().toString());
			}
		}
		letzteZugNr = stand.getLetzteZugNr();
		return stand_r;
	}
	
	/**
	 * Holt die aktuelle Partie vom Server.
	 * Setzt die Spielernamen, die TischNr., den Spielstatus und
	 * ob gegen den Computer gespielt wird.
	 * 
	 * @return aktuelle Partie
	 * @throws SpielStatusException
	 */
	public Partie update() throws SpielStatusException {
		Partie partie = null;
		try {
			partie = EinsteinSitzungsSynchronisierer.partieAbfragen();
			tischNr = EinsteinSitzungsSynchronisierer.getAktuellerTisch();
		} catch (SpielException ex) {
			userA = ueberpruefeBenutzerName(null);
			userB = ueberpruefeBenutzerName(null);
			status = null;
			tischNr = null;
			return null;
		}
		this.isComputerGegner = partie.isComputerGegner();
		this.setzeSpielerUndStatus(partie);
		return partie;
	}
	
	/**
	 * Setzt die Spielernamen und den Status.
	 * 
	 * @param partie
	 */
	protected void setzeSpielerUndStatus(Partie partie) {
		userA = partie.getSpielerA();
		if (! partie.isComputerGegner()) {
			userB = partie.getSpielerB();
		} else {
			userB = computerGegnerName;
		}
		userA = ueberpruefeBenutzerName(userA);
		userB = ueberpruefeBenutzerName(userB);
		setStatus(partie.getStatus().toString());
	}
	
	/**
	 * Falls der uebergebene Name null ist, wird dieser auf 
	 * SpielbrettModell.leererBenutzerName gesetzt.
	 * 
	 * @param name
	 * @return Benutzername
	 */
	protected String ueberpruefeBenutzerName(String name) {
		if (name == null) {
			name = leererBenutzerName;
		}
		return name;
	}
	
	public String getUserA() {
		return this.userA;
	}
	
	public String getUserB() {
		return this.userB;
	}
	
	public boolean isComputerGegner() {
		return this.isComputerGegner;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getTischNr() {
		return this.tischNr;
	}
	
	/**
	 * Liefert eine fachliche Beschreibung des Status.
	 * Falls keine Uebersetzung gefunden wird, wird die
	 * technische Beschreibung zurueckgegeben.
	 * 
	 * @return Status-Beschreibung
	 */
	public String getUebersetztenStatus() {
		String status = null;
		for (Enumeration e = statusFactory.getUebersetzer().elements(); e.hasMoreElements();) {
			status = ((StatusUebersetzerInterface)e.nextElement()).uebersetzeStatus(this);
			if (status != null) {
				return status;
			}
		}
		return getStatus();
	}
	
	/**
	 * Liefert true, wenn ein weiterer Spieler gesucht wird,
	 * andernfalls false.
	 * 
	 * @param partie
	 * @return true, wenn ein weiterer Spieler gesucht wird, andernfalls false.
	 */
	public boolean spielerGesucht(Partie partie) {
		// im Falle einer Partie gegen den Computer wird kein weiterer Spieler gesucht
		if (!partie.isComputerGegner()) {
			return ( userA.equals(leererBenutzerName) || userB.equals(leererBenutzerName) );
		}
		return false;
	}
	
	/**
	 * Liefert true, wenn der mit diesem Client angemeldete Benutzer kein Spieler ist,
	 * andernfalls false.
	 * 
	 * @return true, wenn der mit diesem Client angemeldete Benutzer kein Spieler ist, andernfalls false.
	 */
	public boolean benutzerIstKeinSpieler() {
		return ((!userA.equals(LoginModell.getBenutzerName())) && (!userB.equals(LoginModell.getBenutzerName())));
	}
	
	/**
	 * Liefert true, wenn der mit diesem Client angemeldete Benutzer Spieler, aber noch nicht bereit, ist.
	 * Andernfalls wird false zurueckgegeben.
	 * 
	 * @param status
	 * @return true, wenn der mit diesem Client angemeldete Benutzer Spieler, aber noch nicht bereit, ist.
	 */
	public boolean benutzerIstNochNichtBereit(SpielStatus status) {
		return ( status.equals(SpielStatus.KEIN_SPIEL) || 
				((userA.equals(LoginModell.getBenutzerName())) && status.equals(SpielStatus.B_BEREIT)) ||
				((userB.equals(LoginModell.getBenutzerName())) && status.equals(SpielStatus.A_BEREIT)) ||
				(spielVorbei(status)) );
	}
	
	/**
	 * Liefert true, wenn die Partie vorbei ist, d.h. einer der Gegner im Status abgebrochen
	 * oder gewonnen.
	 * Andernfalls wird false zurueckgegeben.
	 * 
	 * @param status
	 * @return true, wenn die Partie vorbei ist, d.h. einer der Gegner im Status abgebrochen oder gewonnen.
	 */
	public boolean spielVorbei(SpielStatus status) {
		return ( status.equals(SpielStatus.A_ABGEBROCHEN) ||
				 status.equals(SpielStatus.B_ABGEBROCHEN) ||
				 status.equals(SpielStatus.A_GEWONNEN) ||
				 status.equals(SpielStatus.B_GEWONNEN));
	}
	
	/**
	 * Der aktuelle Benutzer wird als Spieler an den aktuellen Tisch
	 * gesetzt.
	 */
	public void einsteigen() {
		try {
			EinsteinSitzungsSynchronisierer.einsteigen(EinsteinSitzungsSynchronisierer.getAktuellerTisch());
		} catch (SpielException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Hiermit wird fuer die aktuelle Spielerin gemeldet, dass sie bereit ist,
	 * das Spiel zu starten.
	 */
	public void bereit() {
		try {
			EinsteinSitzungsSynchronisierer.bereit();
		} catch (SpielException e) {
			viewInterface.falschesSpiel(e);
		} catch (SpielStatusException e) {
			viewInterface.falscherStatus(e);
		}
	}
	
	/**
	 * Der aktuelle Benutzer verlaesst den aktuellen Tisch.
	 */
	public void verlassen() {
		try {
			EinsteinSitzungsSynchronisierer.aktuellenTischVerlassen();
		} catch (SpielException e) {
			viewInterface.falschesSpiel(e);
		} 
	}

}
