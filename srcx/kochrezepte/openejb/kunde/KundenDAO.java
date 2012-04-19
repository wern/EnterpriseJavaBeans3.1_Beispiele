/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.openejb.kunde;

@javax.ejb.Remote
public interface KundenDAO {
	public int erzeugeKunde(String vorname, String nachname);

	public Kunde findeKunde(int id);

	public Kunde aktualisiereKunde(int id, String vorname, String nachname);
}
