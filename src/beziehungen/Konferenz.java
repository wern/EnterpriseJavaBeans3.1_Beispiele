/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package beziehungen;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity public class Konferenz implements Serializable {
    @Id private String name;
    private Date von, bis;
    @ManyToMany private Set<Firma>sponsoren;

    /** Angabe einer zweiten N:M-Beziehung zur selben
        Entity-Klasse wie oben. Hier muss mit @JoinTable
        gearbeitet werden, sonst kommt Unsinn heraus */
    @ManyToMany
    @JoinTable(name="KONFERENZ_AUSSTELLER")
    private Set<Firma>aussteller;
    
    public String getName() { return name; }
    public Date getVon() { return von; }
    public Date getBis() { return bis; }
    public Set<Firma> getSponsoren() { return sponsoren; }
    public Set<Firma> getAussteller() { return aussteller; }

    public void setBis(Date p) { bis = p; }
    public void setVon(Date p) { von = p; }
    public void setSponsoren(Set<Firma> p) { sponsoren = p; }
    public void setAussteller(Set<Firma> p) { aussteller = p; }
    
    public Konferenz() {}
    public Konferenz(String name) {
        this.name = name;
        setVon(new Date());
        sponsoren = new HashSet<Firma>();
    }
}
