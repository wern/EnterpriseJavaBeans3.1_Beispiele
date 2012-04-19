/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.openejb.kunde;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class KundenDAOTest {

	private static InitialContext initialContext;

	private KundenDAO dao;

	@BeforeClass
	public static void setUp() throws Exception {
		Properties properties = new Properties();
		properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");
		properties.setProperty("openejb.deployments.classpath.include",
				".*/Beispiele/classes/");
		properties.setProperty("openejb.deployments.classpath.exclude", ".*");

		initialContext = new InitialContext(properties);
	}

	@Before
	public void doLookup() throws Exception {
		dao = (KundenDAO) initialContext.lookup("KundenDAOImplRemote");
	}

	@Test
	public void erzeugeKunde() {
		Assert.assertTrue("Es wurde keine ID erzeugt.", dao.erzeugeKunde(
				"Werner", "Eberling") > 0);
	}

	@Test
	public void findeKunde() {
		int id = dao.erzeugeKunde("Werner", "Eberling");
		Kunde kunde = dao.findeKunde(id);
		Assert.assertNotNull("Keinen Kunden gefunden!", kunde);
	}
}
