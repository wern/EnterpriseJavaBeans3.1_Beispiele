/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.dateiexport;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Kunde implements java.io.Serializable {
	@Id @GeneratedValue private long kundennummer;
	private String name;
	private String vorname;
	private boolean exportNotwendig;
	
	public long getKundennummer() { return kundennummer; }
	public String getName() { return name; }
	public String getVorname() { return vorname; }
	public boolean isExportNotwendig() { return exportNotwendig; }
	
	public void setKundennummer(long kundennummer) { this.kundennummer = kundennummer; }
	public void setName(String name) { this.name = name; }
	public void setVorname(String vorname) { this.vorname = vorname; }
	public void setExportNotwendig(boolean exportNotwendig) { this.exportNotwendig = exportNotwendig; }
	
}
