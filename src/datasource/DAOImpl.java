/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

@javax.ejb.Stateless
public class DAOImpl implements DAO {

    public int zaehleTimer() throws DAOException {
        int anzahlTimer = 0;
        Connection con = null;
        Statement stmt = null;
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("java:/DefaultDS");
            con = ds.getConnection();
            stmt = con.createStatement();
            ResultSet ergebnisse = stmt.executeQuery("select * from timers");
            while (ergebnisse.next())
                anzahlTimer++;
        } catch (Exception x) {
            x.printStackTrace();
            throw new DAOException(x);
        } finally {
            try { if (stmt != null) stmt.close(); }
            catch (SQLException sqlx) { /* Ignorieren */ }
            try { if (con != null) con.close(); }
            catch (SQLException sqlx) { /* Ignorieren */ }
        }
        return anzahlTimer;
    }
}
