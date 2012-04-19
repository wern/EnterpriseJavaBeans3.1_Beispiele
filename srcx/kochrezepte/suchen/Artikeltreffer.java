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

public class Artikeltreffer implements Serializable {
	public int artikelnr;
	public String bezeichnung;
	public BigDecimal einzelpreis;
	
	public Artikeltreffer(int artikelnr, String bezeichnung, BigDecimal einzelpreis) {
		this.artikelnr = artikelnr;
		this.bezeichnung = bezeichnung;
		this.einzelpreis = einzelpreis;
	}

	public String toString() {
		return artikelnr + "/" + bezeichnung + "/" + einzelpreis;
	}
}
