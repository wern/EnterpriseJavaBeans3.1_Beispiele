/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/
package kochrezepte.jsf.cdi.stateful.zinsrechner;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named("zinsrechner")
@Stateful
public class ZinsrechnerStatefulImpl {

	private int anlagebetrag;
	private int laufzeit;
	private int zinssatzInProzent;
	private int sparsumme;

	public int getAnlagebetrag() {
		return anlagebetrag;
	}

	public void setAnlagebetrag(int anlagebetrag) {
		this.anlagebetrag = anlagebetrag;
	}

	public int getLaufzeit() {
		return laufzeit;
	}

	public void setLaufzeit(int laufzeit) {
		this.laufzeit = laufzeit;
	}

	public int getZinssatzInProzent() {
		return zinssatzInProzent;
	}

	public void setZinssatzInProzent(int zinssatzInProzent) {
		this.zinssatzInProzent = zinssatzInProzent;
	}

	public int getSparsumme() {
		return sparsumme;
	}

	public void berechneSparsumme() {
		sparsumme = (int) (anlagebetrag * Math.pow(1 + zinssatzInProzent/100.0, laufzeit));
	}
}
