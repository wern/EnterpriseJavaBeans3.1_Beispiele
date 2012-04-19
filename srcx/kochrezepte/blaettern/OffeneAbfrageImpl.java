/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.blaettern;

import java.sql.*;
import java.util.*;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

@Stateful(name="offeneabfrage")
@TransactionManagement(TransactionManagementType.BEAN)
public class OffeneAbfrageImpl implements OffeneAbfrage {

	@Resource UserTransaction transaktion;
	private Connection verbindung;
	private ResultSet resultset;

	public List<Artikel> sucheAlleArtikel(int maximaleAnzahl) {
        try {
        	transaktion.begin();
            DataSource ds = (DataSource) new InitialContext().lookup("java:/DefaultDS");
            verbindung = ds.getConnection();
            Statement stmt = verbindung.createStatement();
            resultset = stmt.executeQuery("SELECT * FROM Artikel");
            List<Artikel> ergebnis = naechste(maximaleAnzahl);
            return ergebnis;
        }
        catch (Exception x) {
            throw new EJBException(x);
        }
	}

	public List<Artikel> naechste(int maximaleAnzahl) {
		List<Artikel> ergebnis = new ArrayList<Artikel>();
		for (int i = 0; i < maximaleAnzahl; i++) {
			Artikel a = artikelAuslesen();
			if (a != null)
				ergebnis.add(a);
		}
		return ergebnis;
	}
	
	private Artikel artikelAuslesen() {
		try {
			if (resultset == null)
				return null;
			if (!resultset.next()) {
				schliessen();
				return null;
			}
			Artikel ergebnis = new Artikel();
			ergebnis.setArtikelnr(resultset.getInt("artikelnr"));
			ergebnis.setBeschreibung(resultset.getString("beschreibung"));
			ergebnis.setBestand(resultset.getInt("bestand"));
			ergebnis.setBezeichnung(resultset.getString("bezeichnung"));
			ergebnis.setEinzelpreis(resultset.getBigDecimal("einzelpreis"));
			return ergebnis;
		}
		catch(SQLException sqlx) {
			throw new EJBException(sqlx);
		}
	}

	@Remove
	@PreDestroy
	public void schliessen() {
        try {
        	if (resultset != null) {
        		resultset.close();
        		resultset = null;
        	}
        	if (verbindung != null) {
        		verbindung.close();
            	transaktion.commit();
        		verbindung = null;
        	}
        }
        catch (Exception x) {
        	throw new EJBException(x);
        }
	}
	
}
