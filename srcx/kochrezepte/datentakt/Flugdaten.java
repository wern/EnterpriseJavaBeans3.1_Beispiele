/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.datentakt;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Flugdaten implements Serializable {
	private final List<Flug> fluege;
	private final Date stand;

	Flugdaten(List<Flug> fluege, Date stand) {
		this.fluege = fluege;
		this.stand = stand;
	}

	public List<Flug> getFluege() {
		return fluege;
	}

	public Date getStand() {
		return stand;
	}

}
