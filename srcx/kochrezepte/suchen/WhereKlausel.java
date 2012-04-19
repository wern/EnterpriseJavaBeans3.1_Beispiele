/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.suchen;

public class WhereKlausel {
	private String ausdruck;
	
	public WhereKlausel(String teilausdruck) { ausdruck = teilausdruck; }
	public WhereKlausel() { this(""); }

	public WhereKlausel and(String teilausdruck) { return erweitern(teilausdruck, "AND"); }
	public WhereKlausel or(String teilausdruck) { return erweitern(teilausdruck, "OR"); }
	
	public WhereKlausel erweitern(String teilausdruck, String operator) {
		ausdruck = (ausdruck.isEmpty()) ? teilausdruck : ausdruck + " " + operator + " " + teilausdruck;
		return this;
	}

	public String toString() {
		return ausdruck.isEmpty() ? ausdruck : " WHERE " + ausdruck;
	}
}
