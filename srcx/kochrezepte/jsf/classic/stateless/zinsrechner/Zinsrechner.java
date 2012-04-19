/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/
package kochrezepte.jsf.classic.stateless.zinsrechner;

public interface Zinsrechner {

	public abstract int berechneSparsumme(int anlagebetrag, int jahre,
			double zinssatz);

}