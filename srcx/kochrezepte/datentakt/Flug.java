/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.datentakt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQuery(name = "Flug.nachTyp", query = "select f from Flug f where f.typ=:typ")
@Entity
public class Flug implements Serializable {

	public static enum Typ {
		LANDUNG, START;
	}

	@Id
	@GeneratedValue
	private int id;
	@Enumerated(EnumType.STRING)
	private Typ typ;
	private String flugnummer;
	@Temporal(TemporalType.TIMESTAMP)
	private Date zeit;

	private Flug() {
	}

	Flug(Typ typ, String flugnummer, Date zeit) {
		this.typ = typ;
		this.flugnummer = flugnummer;
		this.zeit = zeit;
	}

	public int getId() {
		return id;
	}

	public Typ getTyp() {
		return typ;
	}

	public String getFlugnummer() {
		return flugnummer;
	}

	public Date getZeit() {
		return zeit;
	}

	@Override
	public String toString() {
		return "Flug [flugnummer=" + flugnummer + ", typ=" + typ + ", zeit="
				+ zeit + "]";
	}

}
