/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package einstein.server.oeffentlich;
import java.util.List;
import javax.ejb.Remote;


/** Interface der Einstein-Portal-EJB mit den Grundfunktionen zur
 * Benutzerregistration und Übersicht über die anwesenden Spieler.
 */
@Remote public interface EinsteinPortal {
	void registrieren(String spielerName, String passwort) throws VerwaltungsException;
	void kuendigen(String spielerName, String passwort) throws VerwaltungsException;
	
	List<Spieler> anwesendeSpieler();
	List<Spieler> bestenListe();
	
}
