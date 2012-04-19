/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.webrechner;

public class WSRechenergebnis extends WSErgebnis {
	public int wert;
	
	public WSRechenergebnis(int wert) { this.wert = wert; }
	public WSRechenergebnis() { this.fehlercode = 99; }

	public WSRechenergebnis(int fehlercode, String fehlertext) {
		super(fehlercode, fehlertext);
	}
	
	public String toString() {
		return (erfolg()) ? Integer.toString(wert) : super.toString();
	}
}
