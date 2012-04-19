/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package abfragen;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@SqlResultSetMapping(name="fahrtUndFahrernameMapping",
    entities = { @EntityResult(entityClass = Fahrt.class) },
    columns = { @ColumnResult(name="NAME") })
@Entity public class Fahrt implements Serializable {
    @Id @GeneratedValue @Column(updatable=false)
    private int nr;

    @Temporal(TemporalType.DATE)
    @Column(updatable=false, nullable=false)
    private Date abholung;
    
    @Temporal(TemporalType.DATE) private Date rueckgabe;
    @ManyToOne @JoinColumn(updatable=false, nullable=false)
    private Station von;
    
    @ManyToOne private Station nach;
    
    @ManyToOne @JoinColumn(updatable=false, nullable=false)
    private Fahrzeug mit;

    @ManyToOne @JoinColumn(updatable=false)
    private Mieter fahrer;

    public Fahrt(Date abholung, Station von, Fahrzeug mit, Mieter fahrer) {
        this.abholung = abholung;
        this.von = von;
        this.mit = mit;
        this.fahrer = fahrer;
        mit.addFahrt(this);
    }
    public Fahrt() {}
    public void beenden(Date rueckgabe, Station in) {
        this.rueckgabe = rueckgabe;
        this.nach = in;
    }

    public Date getAbholung() { return abholung; }
    public Mieter getFahrer() { return fahrer; }
    public Fahrzeug getMit() { return mit; }
    public Station getNach() { return nach; }
    public int getNr() { return nr; }
    public Date getRueckgabe() { return rueckgabe; }
    public Station getVon() { return von; }
}
