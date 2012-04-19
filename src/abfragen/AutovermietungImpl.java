/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package abfragen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless public class AutovermietungImpl implements Autovermietung {

    @PersistenceContext EntityManager em;

    public List<String> alleFahrzeuge() {

    	/*
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<String> abfrage = cb.createQuery(String.class);
    	Root<Fahrzeug> fahrzeuge = abfrage.from(Fahrzeug.class);
    	abfrage.select(fahrzeuge.get(Fahrzeug_.kennzeichen));
    	return em.createQuery(abfrage).getResultList();
    	*/
    	
    	/*
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<String> abfrage = cb.createQuery(String.class);
    	Root<Fahrzeug> fahrzeuge = abfrage.from(Fahrzeug.class);
    	abfrage.select(fahrzeuge.<String>get("kennzeichen"));
    	return em.createQuery(abfrage).getResultList();
    	*/
    	
        //return em.createQuery("SELECT f.kennzeichen FROM Fahrzeug f").getResultList();
        //return em.createQuery("SELECT f.kennzeichen FROM Fahrzeug f", String.class).getResultList();
        //return em.createQuery("SELECT f.standort.stadt FROM Fahrzeug f").getResultList();
        List<Fahrzeug> l = em.createQuery("SELECT f FROM Fahrzeug f").getResultList();
        //List<Fahrzeug> l = em.createNativeQuery("SELECT * FROM Fahrzeug", Fahrzeug.class).getResultList();
        //List<Fahrzeug> l = em.createNamedQuery("alleFahrzeuge").getResultList();
        //List<Fahrzeug> l = em.createNamedQuery("alleFahrzeugeSQL").getResultList();

        List<String> result = new ArrayList<String>();
        for (Fahrzeug f: l)
            result.add(f.getKennzeichen());
        return result;

    }
    
    public List<String> alleFahrzeugeIn(String stadt) {
        List<String> result = new ArrayList<String>();
        //List<Fahrzeug> l = em.createQuery("SELECT f FROM Fahrzeug f WHERE f.standort.stadt = '" + stadt + "'").getResultList();
        //List<Fahrzeug> l = em.createQuery("SELECT f FROM Fahrzeug f WHERE f.standort.stadt = :stadt").setParameter("stadt", stadt).getResultList();
        List<Fahrzeug> l = em.createQuery("SELECT f FROM Fahrzeug f WHERE f.standort.stadt = ?0").setParameter(0, stadt).getResultList();
        for (Fahrzeug f: l)
            result.add(f.getKennzeichen());
        return result;
    }
    
    public List<String> stationenMitLeerfahrten() {
        return em.createQuery("SELECT DISTINCT f.von.stadt FROM Fahrt f WHERE f.fahrer IS NULL").getResultList();
    }

    public List<Object[]> anzahlFahrzeugeProStadt() {
        return em.createQuery("SELECT f.standort.stadt, COUNT(f) FROM Fahrzeug f GROUP BY f.standort.stadt").getResultList();
    }

    public List<Object> bauhjahrSpanne() {
        //return em.createQuery("SELECT MAX(f.baujahr), MIN(f.baujahr) FROM Fahrzeug f").getResultList();
        return em.createQuery("SELECT NEW abfragen.BaujahrSpanne(MAX(f.baujahr), MIN(f.baujahr)) FROM Fahrzeug f").getResultList();
    }

    public List<String> ermittleFahrzeuge(String stadt, String kennzeichen, String modell, Integer baujahr) {
        String where = "1=1 ";
        if (stadt != null)
            where += "AND f.standort.stadt = '" + stadt + "' ";
        if (kennzeichen != null)
            where += "AND f.kennzeichen = '" + kennzeichen + "' ";
        if (modell != null)
            where += "AND f.modell = '" + modell + "' ";
        if (baujahr != null)
            where += "AND f.baujahr = " + baujahr;
        return em.
           createQuery("SELECT f.kennzeichen FROM Fahrzeug f WHERE " + where).
           getResultList();
    }

    public List<String> ungenutzteFahrzeuge() {
        return em.
            createQuery("SELECT fz.kennzeichen FROM Fahrzeug fz WHERE fz.fahrten IS EMPTY").
            getResultList();
    }

    public List<String> mehrfachMieter() {
        return em.
            createQuery("SELECT m.name FROM Mieter m WHERE " +
                        "(SELECT COUNT(f) FROM Fahrt f WHERE f.fahrer = m) > 1").
            getResultList();
    }
    
    public List<String> kundenIn(String stadt) {
        List<String> result = new ArrayList<String>();
        List<Mieter> l = em.createQuery(
/*
                "SELECT DISTINCT m FROM Mieter m, Fahrt f WHERE " +
                "f.fahrer = m AND (" +
                "f.von.stadt = '" + stadt + "' OR " +
                "f.nach.stadt = '" + stadt + "')").
*/
                "SELECT DISTINCT m FROM Fahrt f JOIN f.fahrer m WHERE " +
                "f.von.stadt = '" + stadt + "' OR " +
                "f.nach.stadt = '" + stadt + "'").
                getResultList();
        for (Mieter k: l)
            result.add(k.getName());
        return result;
    }

    public List<Object[]> fahrtnrUndFahrername() {
        return em.createQuery(
            "SELECT f.nr, m.name FROM Fahrt f LEFT JOIN f.fahrer m").getResultList();
        /*
        List<Object[]> result = new ArrayList<Object[]>();
        List<Fahrt> l = em.createQuery("SELECT f FROM Fahrt f LEFT JOIN FETCH f.fahrer").getResultList();
        for (Fahrt f: l) {
            Object[] element = new Object[2];
            element[0] = f.getNr();
            if (f.getFahrer() != null)
                element[1] = f.getFahrer().getName();
            result.add(element);
        }
        return result;
        */
    }

    public List<Object[]> fahrtUndFahrername() {
        return em.createNativeQuery(
                "SELECT * FROM FAHRT F LEFT OUTER JOIN MIETER M ON (F.FAHRER_KUNDENNR = M.KUNDENNR)",
                "fahrtUndFahrernameMapping").getResultList();
    }
    
    public int stadtnamenKlein() {
        return em.createQuery("UPDATE Station s SET s.stadt = LOWER(s.stadt)").executeUpdate();
    }
    
    public int loescheFahrtenNach(String stadt) {
        //return em.createQuery("DELETE FROM Fahrt f WHERE f.nach.stadt = '" + stadt + "'").executeUpdate();
        return em.createQuery("DELETE FROM Fahrt f WHERE EXISTS (SELECT s FROM Station s WHERE f.nach = s AND s.stadt = '" + stadt + "')").executeUpdate();
    }
    
    private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    
    private static Date toDate(String dateString) {
        try { return dateFormat.parse(dateString); }
        catch(ParseException px) {
            px.printStackTrace();
            return null;
        }
    }

    public void beispielDatenAnlegen() {
        Station hamburg = new Station("Hamburg"); em.persist(hamburg);
        Station frankfurt = new Station("Frankfurt"); em.persist(frankfurt);
        Station bonn = new Station("Bonn"); em.persist(bonn);
        Station paderborn = new Station("Paderborn"); em.persist(paderborn);
        
        Fahrzeug mercedes = new Fahrzeug("HH-MB 240", "Mercedes 240", 1999);
        mercedes.setStandort(hamburg);
        em.persist(mercedes);
        Fahrzeug golf = new Fahrzeug("HH-GL 5", "VW Golf V", 2000);
        golf.setStandort(hamburg);
        em.persist(golf);
        Fahrzeug passat = new Fahrzeug("PB-PS 100", "VW Passat", 2001);
        passat.setStandort(paderborn);
        em.persist(passat);
        Fahrzeug bmw = new Fahrzeug("HH-BMW 3", "3'er BMW", 1998);
        em.persist(bmw);
        Fahrzeug audi = new Fahrzeug("HH-AU 6", "Audi A6", 2004);
        em.persist(audi);
        
        Mieter meier = new Mieter("Meier, Hans"); em.persist(meier);
        Mieter schumacher = new Mieter("Schumacher, Franz"); em.persist(schumacher);
        Mieter meister = new Mieter("Meister, Peter"); em.persist(meister);
        Fahrt f1 = new Fahrt(toDate("12.11.2006"), bonn, golf, schumacher);
        f1.beenden(toDate("13.11.2006"), frankfurt);
        em.persist(f1);
        Fahrt f2 = new Fahrt(toDate("15.11.2006"), frankfurt, golf, meister);
        f2.beenden(toDate("17.11.2006"), hamburg);
        em.persist(f2);
        Fahrt f3 = new Fahrt(toDate("03.12.2006"), hamburg, passat, meister);
        f3.beenden(toDate("03.12.2006"), paderborn);
        em.persist(f3);
        Fahrt f4 = new Fahrt(toDate("13.12.2006"), bonn, audi, meier);
        em.persist(f4);
        Fahrt f5 = new Fahrt(toDate("14.12.2006"), frankfurt, bmw, null);
        em.persist(f5);
        Fahrt f6 = new Fahrt(toDate("14.12.2006"), frankfurt, golf, schumacher);
        f6.beenden(toDate("14.12.2006"), bonn);
        em.persist(f6);
    }

}
