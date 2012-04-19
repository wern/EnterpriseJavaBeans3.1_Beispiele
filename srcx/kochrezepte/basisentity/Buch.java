/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.basisentity;

import java.text.SimpleDateFormat;

import javax.persistence.Entity;

@Entity
public class Buch extends Basisentity {
	private String titel, autor;
	private int anzahlSeiten;
	
	public Buch(String titel, String autor, int anzahlSeiten) {
		this.titel = titel;
		this.autor = autor;
		this.anzahlSeiten = anzahlSeiten;
	}
	
	public Buch() { }
	
	public String getTitel() { return titel; }
	public void setTitel(String titel) { this.titel = titel; }
	public String getAutor() { return autor; }
	public void setAutor(String autor) { this.autor = autor; }
	public int getAnzahlSeiten() { return anzahlSeiten; }
	public void setAnzahlSeiten(int anzahlSeiten) { this.anzahlSeiten = anzahlSeiten; }

	public String toString() {
		return getId() + ", " + titel + ", " + autor + ", " + anzahlSeiten + " Seiten,\n" + super.toString();
	}
}
