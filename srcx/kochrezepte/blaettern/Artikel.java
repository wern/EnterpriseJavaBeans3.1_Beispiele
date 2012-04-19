/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.blaettern;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.validation.constraints.Size;

@Entity public class Artikel implements Serializable {
	@Id @GeneratedValue private int artikelnr;
	@Size(min=5, max=30) private String bezeichnung;
	@Size(min=5, max=4000) private String beschreibung;
	private BigDecimal einzelpreis;
	private int bestand;

	public String getBezeichnung() { return bezeichnung; }
	public void setBezeichnung(String bezeichnung) { this.bezeichnung = bezeichnung; }
	public String getBeschreibung() { return beschreibung; }
	public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }
	public BigDecimal getEinzelpreis() { return einzelpreis; }
	public void setEinzelpreis(BigDecimal einzelpreis) { this.einzelpreis = einzelpreis; }
	public int getArtikelnr() { return artikelnr; }
	public void setArtikelnr(int artikelnr) { this.artikelnr = artikelnr; }
	public int getBestand() { return bestand; }
	public void setBestand(int bestand) { this.bestand = bestand; }
	
	public Artikel(String bezeichnung, BigDecimal einzelpreis, int bestand) {
		this.bezeichnung = bezeichnung;
		this.beschreibung = bezeichnung;
		this.einzelpreis = einzelpreis;
		this.bestand = bestand;
	}

	public Artikel() {}
	
	public String toString() {
		return artikelnr + "/" + bezeichnung + "/" + einzelpreis + "/" + bestand;
	}
	
}
