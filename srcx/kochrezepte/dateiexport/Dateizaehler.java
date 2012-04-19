/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.dateiexport;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Dateizaehler {
	public static final int ID = 0;
	
	@Id int id = ID;
	long zaehlerstand;
	
	public long getZaehlerstand() { return zaehlerstand; }
	public void setZaehlerstand(long zaehlerstand) { this.zaehlerstand = zaehlerstand; }
	public int getId() { return id; }
}
