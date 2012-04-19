/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package beziehungen;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity public class Projekt {
    @Id @GeneratedValue private int id;
    private String name;
    private Date start, ende;
    private Status status;

    @ManyToMany(mappedBy="projekte")
    private Set<Mitarbeiter> teilnehmer;
    
    public static enum Status { Gruen, Gelb, Rot }

    public int getId() { return id; }
    public String getName() { return name; }
    public Date getEnde() { return ende; }
    public Date getStart() { return start; }
    public Status getStatus() { return status; }
    public Set<Mitarbeiter> getTeilnehmer() { return teilnehmer; }

    public void setName(String p) { name = p; }
    public void setEnde(Date p) { ende = p; }
    public void setStart(Date p) { start = p; }
    public void setStatus(Status p) { status = p; }
    public void setTeilnehmer(Set<Mitarbeiter> p) { teilnehmer = p; }
    
    public Projekt() {}
    public Projekt(String name) {
        setName(name);
        setStart(new Date());
        setStatus(Status.Gruen);
        setTeilnehmer(new HashSet<Mitarbeiter>());
    }
}
