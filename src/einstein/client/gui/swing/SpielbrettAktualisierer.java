package einstein.client.gui.swing;

import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import einstein.server.oeffentlich.Partie;
import einstein.server.oeffentlich.SpielStand;
import einstein.server.oeffentlich.SpielStatus;
import einstein.server.oeffentlich.SpielStatusException;
import einstein.server.oeffentlich.Stein;
import einstein.server.oeffentlich.Zug;

/**
 * Beschreibt die Aktualisierung des Spielbretts.
 * Wenn ein nachfolgener Zug des Spiels verfuegbar ist, so wird
 * dieser aktualisiert, andernfalls die gesamte Partie.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class SpielbrettAktualisierer {
	protected Spielbrett brett = null;

	public SpielbrettAktualisierer(Spielbrett brett) {
		this.brett = brett;
	}
	
	/**
	 * Aktualisiert das Spielbrett abhaengig vom Status des Servers.
	 * 
	 * @throws SpielStatusException
	 */
	public void update() throws SpielStatusException {
		
		SpielStand stand = brett.getModell().updateSpielStand();	
		if (stand != null) {
			updateStatus();
			updateZug(stand.getLetzterZug());
			updateWuerfel(stand, brett.getModell().isComputerGegner(), false);
		} else {
			vollesUpdate();
		}
		
		brett.validate();
		brett.repaint();	
		updateDragging();
	}
	
	/**
	 * Ermittelt das Startfeld und setzt den Zug.
	 * 
	 * @param zug
	 */
	protected void updateZug(Zug zug) {
		Feld feld = null;
		for (Enumeration e = brett.getFelder().elements(); e.hasMoreElements();) {
			feld = ((Feld)e.nextElement());
			if (feld.getSteinId() == zug.getSteinId()) {
				setzeZug(feld, zug);
				break;
			}
		}
	}
	
	/**
	 * Loescht den gezogenen Stein vom uebergebenen Feld (Startfeld) 
	 * und setzt ihn auf das Zielfeld.
	 * 
	 * @param feld
	 * @param zug
	 */
	protected void setzeZug(Feld feld, Zug zug) {
		int id = feld.getSteinId();
		int nr = feld.getNr();
		ImageIcon icon = feld.getIcon();
		feld.loescheStein();
		feld = brett.getFelder().get(zug.getZiel().toString());
		if (feld != null) {
			feld.addStein(icon, nr, id);
		}
	}
	
	protected void updateStatus() {
		brett.setStatusText(brett.getModell().getUebersetztenStatus());
	}
	
	/**
	 * Falls waehrend des Updates gerade gedragged wird,
	 * wird hierdurch das bewegte Icon dargestellt.
	 */
	protected void updateDragging() {
		for (Enumeration e = brett.getFelder().elements(); e.hasMoreElements();) {
			((Feld)e.nextElement()).update();
		}
	}
	
	/**
	 * Zeigt das Spielbrett als leeren Tisch an.
	 */
	protected void leererTisch() {
		brett.updateTischNr(Spielbrett.leereTischNummer);
		brett.setStatusText(Spielbrett.leererTischUeberschrift);
		brett.setBereit(false);
		brett.setVerlassen(false);
		brett.getWuerfelA().setCount(0);
		brett.getWuerfelB().setCount(0);
		brett.getWuerfelA().setSpielerName(brett.getModell().getUserA());
		brett.getWuerfelB().setSpielerName(brett.getModell().getUserB());
		
		JPanel feld = null;
		for (int i = 0; i < brett.getSpielbrettPanel().getComponentCount(); i++) {
			feld = (JPanel)brett.getSpielbrettPanel().getComponent(i);
			if (feld instanceof Feld) {
				((Feld)feld).loescheStein();
			}
		}
		
		brett.validate();
		brett.repaint();
	}
	
	/**
	 * Aktualisiert nicht nur den Status, den letzten Zug und die Wuerfel, 
	 * sondern auch die Steuerung (Buttons), alle Spielfelder und ueber das
	 * SpielbrettModell die Benutzernamen und ob es eine Partie gegen den
	 * Computer ist.
	 * 
	 * @throws SpielStatusException
	 */
	protected void vollesUpdate() throws SpielStatusException {
		Partie partie = brett.getModell().update();
		if (partie == null) {
			this.leererTisch();
			return;
		}
		brett.updateTischNr(brett.getModell().getTischNr().toString());
		updateButtons(partie);	
		updateFelder(partie);
		updateStatus();
		updateWuerfel(partie.getStand(), partie.isComputerGegner(), true);
	}
	
	/**
	 * Aktualisiert die Steuerung (Buttons).
	 * 
	 * @param partie
	 */
	protected void updateButtons(Partie partie) {
		if (!brett.getVerlassen()) {
			brett.setVerlassen(true);
		}
		
		boolean bereit = false;
		if (!brett.getModell().spielerGesucht(partie)) {
			if (!brett.getModell().benutzerIstKeinSpieler()) {
				if (brett.getModell().benutzerIstNochNichtBereit(partie.getStatus())) {
					bereit = true;
				} 
			}
		}	
		if (bereit) {
			brett.setBereit(true);
		} else {
			brett.setBereit(false);
		}
	}
	
	/**
	 * Aktualisiert die Spielfelder.
	 * 
	 * @param partie
	 */
	protected void updateFelder(Partie partie) {
		for (Enumeration e = brett.getFelder().elements(); e.hasMoreElements();) {
			((Feld)e.nextElement()).removeAll();
		}
		Feld currentField = null;
		for (Stein stein : partie.getAufstellung()) {
	        currentField = brett.getFelder().get(stein.position.toString());
	        if (currentField != null) {
	        	ImageIcon icon = brett.getSteinIcon(stein.spielerA, new Integer(stein.nr));
	        	currentField.addStein(icon, stein.nr, stein.id);
	        }        
	    }
	}
	
	/**
	 * Aktualisiert die Wuerfel, d.h. die Augenzahl bzw. ob ein Wuerfel
	 * leer dargestellt wird und ggf. die Namen der Spieler (bei einem
	 * Update der gesamten Partie).
	 * 
	 * @param stand
	 * @param isComputerGegner
	 */
	protected void updateWuerfel(SpielStand stand, boolean isComputerGegner, boolean vollesUpdate) {
		if (stand.getStatus().equals(SpielStatus.A_AM_ZUG)) {
			brett.getWuerfelA().setCount(stand.getZuletztGewuerfelt());
		} else if (isComputerGegner && (!stand.getStatus().equals(SpielStatus.KEIN_SPIEL))) {
			brett.getWuerfelB().setCount(stand.getZuletztGewuerfelt());
		}	
			
		if (stand.getStatus().equals(SpielStatus.A_WUERFELT)) {
			brett.getWuerfelA().setCount(0);
		} else if (stand.getStatus().equals(SpielStatus.B_AM_ZUG)) {
			brett.getWuerfelB().setCount(stand.getZuletztGewuerfelt());	
		} else if (stand.getStatus().equals(SpielStatus.B_WUERFELT)) {
			brett.getWuerfelB().setCount(0);
		} else if (stand.getStatus().equals(SpielStatus.KEIN_SPIEL) || stand.getStatus().equals(SpielStatus.A_BEREIT) 
				   || stand.getStatus().equals(SpielStatus.B_BEREIT)) {
			brett.getWuerfelA().setCount(0);
			brett.getWuerfelB().setCount(0);
		} 
		if (vollesUpdate) {
			brett.getWuerfelA().setSpielerName(brett.getModell().getUserA());
			brett.getWuerfelB().setSpielerName(brett.getModell().getUserB());
		}
	}

}
