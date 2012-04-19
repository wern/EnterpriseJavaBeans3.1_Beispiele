package einstein.client.gui.modell;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import einstein.server.oeffentlich.EinsteinPortal;
import einstein.server.oeffentlich.EinsteinSitzung;
import einstein.server.oeffentlich.Partie;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStand;
import einstein.server.oeffentlich.SpielStatusException;
import einstein.server.oeffentlich.Spieler;
import einstein.server.oeffentlich.VerwaltungsException;
import einstein.server.oeffentlich.Zug;

/**
 * Synchronisiert den Zugriff auf die EJBs.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class EinsteinSitzungsSynchronisierer {

	protected static InitialContext context = null;
	protected static EinsteinSitzung sitzung = null;
	protected static EinsteinPortal portal = null;
	
	/**
	 * Singleton fuer die InitialContext-Instanz.
	 * 
	 * @return InitialContext-Instanz
	 */
	protected static InitialContext getContext() {
		if (context == null) {
			try {
				context = new InitialContext();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return context;
	}
	
	/**
	 * Singleton fuer die EinsteinSitzung-Instanz.
	 * 
	 * @return Einsteinsitzung-Instanz
	 */
	protected static EinsteinSitzung getSitzung() {
		if (sitzung == null) {
			try {
				sitzung = (EinsteinSitzung)getContext().lookup("EinsteinSitzungImpl/remote");
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sitzung;
	}
	
	/**
	 * Singleton fuer die EinsteinPorta-Instanz.
	 * 
	 * @return EinsteinPortal-Instanz
	 */
	protected static EinsteinPortal getPortal() {
		if (portal == null) {
			try {
				portal = (EinsteinPortal)getContext().lookup("EinsteinPortalImpl/remote");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return portal;
	}
	
	synchronized public static void anmelden(String spielerName) throws VerwaltungsException {
		getSitzung().anmelden(spielerName);
	}
	
	synchronized public static void abmelden() {
		getSitzung().abmelden();
	}
	
	synchronized public static Integer getAktuellerTisch() {
		return getSitzung().aktuellerTisch();
	}
	
	synchronized public static void aktuellenTischVerlassen() throws SpielException {
		getSitzung().verlassen();
	}
	
	synchronized public static void zuschauen(int tischNr) throws SpielException {
		getSitzung().zuschauen(tischNr);
	}
	
	synchronized public static int eroeffneTisch(boolean computerGegner) throws SpielException {
		return getSitzung().eroeffneTisch(computerGegner);
	}
	
	synchronized public static void einsteigen(int tischNr) throws SpielException {
		getSitzung().einsteigen(tischNr);
	}
	
	synchronized public static void bereit() throws SpielException, SpielStatusException {
		getSitzung().bereit();
	}
	
	synchronized public static int wuerfeln() throws SpielException, SpielStatusException {
		return getSitzung().wuerfeln();
	}
	
	synchronized public static void ziehen (Zug zug) throws SpielException, SpielStatusException {
		getSitzung().ziehen(zug);
	}
	
	synchronized public static Partie partieAbfragen() throws SpielException, SpielStatusException {
		return getSitzung().partieAbfragen();
	}
	
	synchronized public static SpielStand standAbfragen() throws SpielException, SpielStatusException {
		return getSitzung().standAbfragen();
	}
	
	synchronized public static void registrieren(String spielerName, String passwort) throws VerwaltungsException {
		getPortal().registrieren(spielerName, passwort);
	}
	
	synchronized public static List<Spieler> getAnwesendeSpieler() {
		return getPortal().anwesendeSpieler();
	}

}
