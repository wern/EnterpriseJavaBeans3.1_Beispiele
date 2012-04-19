/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package beziehungen;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless public class BeziehungsKisteImpl implements BeziehungsKiste {
    @PersistenceContext EntityManager em;
    
    public int erzeugeBestellung() {
        Bestellung bestellung = new Bestellung();
        bestellung.setBestelltAm(new Date());
        bestellung.setPositionen(new HashSet<Bestellposition>());
        Bestellposition pos = new Bestellposition
            ("Glühbirne 60W", 3, new BigDecimal(1.49));
        bestellung.getPositionen().add(pos);
        pos.setKopf(bestellung);
        pos = new Bestellposition
            ("T-Shirt XXL", 1, new BigDecimal(7.50));
        bestellung.getPositionen().add(pos);
        pos.setKopf(bestellung);
        em.persist(bestellung); /* Kaskadierte Persistierung */
        //em.remove(pos); /* Kleines Experiment, s.u. */
        //bestellung.getPositionen().remove(pos); /* Noch ein Experiment */
        return bestellung.getBestellNr();
    }
    
    public int erzeugeRechner() {
        Rechner rechner = new Rechner();
        Tastatur tastatur = new Tastatur();
        rechner.setModell("Pentium III, 1 GHerz");
        tastatur.setSprache("Deutsch");
        rechner.setAngeschlosseneTastatur(tastatur);
        tastatur.setAngeschlossenAn(rechner);
        em.persist(rechner);
        em.persist(tastatur);
        return rechner.getInventarNr();
    }
    
    public int erzeugePerson() {
        Adresse adresse = new Adresse("Am Berg 34", "33178", "Borchen");
        em.persist(adresse);
        Person peter = new Person();
        peter.setName("Mustermann");
        peter.setVorname("Peter");
        peter.setAnschrift(adresse);
        em.persist(peter);
        return peter.getId();
    }

    public String erzeugeFirma() {
        Adresse a1 = new Adresse("Nägelsbachstr. 25b", "91052", "Erlangen");
        em.persist(a1);
        Adresse a2 = new Adresse("Kirchstr. 11", "33178", "Borchen");
        em.persist(a2);
        Firma mathema = new Firma();
        mathema.setHandelsregisterNr("23-F56-199");
        mathema.setName("MATHEMA Software GmbH");
        mathema.setAnschriften(new HashSet<Adresse>());
        mathema.getAnschriften().add(a1);
        mathema.getAnschriften().add(a2);
        em.persist(mathema);
        return mathema.getHandelsregisterNr();
    }
    
    public int erzeugeVersammlung() {
        Firma mathema = em.find(Firma.class, "23-F56-199");
        Versammlung strategieMeeting = new Versammlung
            ("Strategiemeeting", 67, mathema);
        em.persist(strategieMeeting);
        Versammlung hauptVersammlung = new Versammlung
            ("Haujptversammlung", 100, mathema);
        em.persist(hauptVersammlung);
        return hauptVersammlung.getId();
    }

    public int erzeugeMitarbeiter() {
        Mitarbeiter m1 = new Mitarbeiter
            (Mitarbeiter.Bereich.PRODUKTION, 47000);
        Mitarbeiter m2 = new Mitarbeiter
            (Mitarbeiter.Bereich.PRODUKTION, 34000);
        Projekt ejbBuch = new Projekt("EJB-Buch");
        Projekt cuba = new Projekt("CUBA");
        m1.getProjekte().add(ejbBuch);
        ejbBuch.getTeilnehmer().add(m1);
        m2.getProjekte().add(ejbBuch);
        ejbBuch.getTeilnehmer().add(m2);
        m2.getProjekte().add(cuba);
        cuba.getTeilnehmer().add(m2);
        em.persist(m1);
        em.persist(m2);
        em.persist(ejbBuch);
        em.persist(cuba);
        return m2.getPersonalNr();
    }

    public String erzeugeKonferenz() {
        Firma mathema = em.find(Firma.class, "23-F56-199");
        Konferenz jax = new Konferenz("JAX");
        jax.getSponsoren().add(mathema);
        em.persist(mathema);
        em.persist(jax);
        return jax.getName();
    }
    
}
