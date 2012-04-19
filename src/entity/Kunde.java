/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@SequenceGenerator(name="KUNDE_SEQ", initialValue=5000, allocationSize=100)
@Entity public class Kunde implements Serializable {
   private int id;
   private String vorname;
   private String nachname;

   @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="KUNDE_SEQ") public int getId() { return id; }
   public String getVorname() { return vorname; }
   public String getNachname() { return nachname; }

   public void setId(int pId) { id = pId; }
   public void setVorname(String pVorname) { vorname = pVorname; }
   public void setNachname(String pNachname) { nachname = pNachname; }
}
