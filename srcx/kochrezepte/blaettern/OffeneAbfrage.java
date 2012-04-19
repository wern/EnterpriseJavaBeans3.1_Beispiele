/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.blaettern;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface OffeneAbfrage {

	public List<Artikel> sucheAlleArtikel(int maximaleAnzahl);

	public List<Artikel> naechste(int maximaleAnzahl);

	public void schliessen();

}
