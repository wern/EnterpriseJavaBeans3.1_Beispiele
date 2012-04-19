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
import java.util.Date;

import javax.ejb.EJBContext;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;

@MappedSuperclass
public class Basisentity implements java.io.Serializable {
	@Id @GeneratedValue
	private long id;
	@Version
	private int versionsnr;
	private String angelegtVon;
	@Temporal(TemporalType.TIMESTAMP)
	private Date angelegtAm;
	@Temporal(TemporalType.TIMESTAMP) 
	private Date zuletztGeaendertAm;

	public long getId() { return id; }
	public Date getAngelegtAm() { return angelegtAm; }
	public String getAngelegtVon() { return angelegtVon; }
	public Date getZuletztGeaendertAm() { return zuletztGeaendertAm; }
	public int getVersionsnr() { return versionsnr; }

	@PrePersist public void erzeugungsattributeSetzen() {
		angelegtAm = new Date();
		angelegtVon = ermittleBenutzername();
	}

	@PreUpdate public void aenderungsattributeSetzen() {
		zuletztGeaendertAm = new Date();
		ermittleBenutzername();
	}

	private String ermittleBenutzername() {
		try {
			EJBContext kontext = (EJBContext)new InitialContext().lookup("java:comp/EJBContext");
			return kontext.getCallerPrincipal().getName();
		}
		catch(NamingException nx) {
			throw new EJBException("Basisentity findet keinen Kontext:", nx);
		}
	}


	public static final SimpleDateFormat datumsformat = new SimpleDateFormat("dd.MM.yy, HH:mm:ss.SSS");
	
	public String toString() {
		return angelegtVon + " / " +
			(angelegtAm == null ? "-" : datumsformat.format(angelegtAm)) + " / " +
			(zuletztGeaendertAm == null ? "-" : datumsformat.format(zuletztGeaendertAm));
	}

}
