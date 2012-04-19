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

import javax.persistence.*;

@Entity public class Person implements Serializable {
    @Id @GeneratedValue private int id;
    @OneToOne(optional=false, cascade={CascadeType.REMOVE})
    private Adresse anschrift;
    private String name;
    private String vorname;
    
    public Adresse getAnschrift() { return anschrift; }
    public void setAnschrift(Adresse p) { anschrift = p; }
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String p) { name = p; }
    public String getVorname() { return vorname; }
    public void setVorname(String p) { vorname = p; }
}
