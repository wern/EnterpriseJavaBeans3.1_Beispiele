/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/
package kochrezepte.jsf.classic.model;

import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import kochrezepte.jsf.classic.stateless.zinsrechner.Zinsrechner;

@SessionScoped
@ManagedBean(name="zinsrechner")
public class ZinsrechnerBean {

	@EJB
	private Zinsrechner zinsrechner;

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

	public String berechneSparsumme() {
		sparsumme = zinsrechner.berechneSparsumme(anlagebetrag, laufzeit,
				zinssatzInProzent / 100.0);
		return null;
	}

}
