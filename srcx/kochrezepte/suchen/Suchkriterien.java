/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.suchen;

import java.io.Serializable;
import java.math.BigDecimal;

public class Suchkriterien implements Serializable {
	public Integer artikelnr;
	public BigDecimal preisuntergrenze, preisobergrenze;
	public String teilDerBeschreibung;
	
	public String toString() {
		WhereKlausel where = new WhereKlausel();
		if (artikelnr != null)
			where.and("a.artikelnr = " + artikelnr);
		if (preisuntergrenze != null)
			where.and("a.einzelpreis >= " + preisuntergrenze);
		if (preisobergrenze != null)
			where.and("a.einzelpreis <= " + preisobergrenze);
		if (teilDerBeschreibung != null)
			where.and("UPPER(a.beschreibung) LIKE '%" + teilDerBeschreibung.toUpperCase() + "%'");
		return where.toString();
	}
}
