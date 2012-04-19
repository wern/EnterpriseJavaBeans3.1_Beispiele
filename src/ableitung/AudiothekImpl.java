/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package ableitung;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** Implementierung einer Audiothek zur Anlage und Suche von
    Tonträgern. Die Suche erfolgt vollständig generisch */
@Stateless public class AudiothekImpl implements Audiothek {
    @PersistenceContext EntityManager em;
    
    public TonTraeger finde(int id) {
        return em.find(TonTraeger.class, id);
    }
    
    public TonTraeger anlegen(TonTraeger tonTraeger) {
        em.persist(tonTraeger);
        return tonTraeger;
    }
}
