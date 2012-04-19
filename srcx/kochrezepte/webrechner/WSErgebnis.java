/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.webrechner;

public class WSErgebnis implements java.io.Serializable {
	public static final int ERFOLG = 0;
	public static final int SYSTEMFEHLER = 99;
	
	public int fehlercode;
	public String fehlertext;

	public WSErgebnis() { }
	
	public WSErgebnis(String fehlertext) {
		this(SYSTEMFEHLER, fehlertext);
	}

	public WSErgebnis(int fehlercode, String fehlertext) {
		this.fehlercode = fehlercode;
		this.fehlertext = fehlertext;
	}

	public boolean erfolg() { return fehlercode == ERFOLG; }
	
	public String toString() { return "Fehler " + fehlercode + ": " + fehlertext; }
}
