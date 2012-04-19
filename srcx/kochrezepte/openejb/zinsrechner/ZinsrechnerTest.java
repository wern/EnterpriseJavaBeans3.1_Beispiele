/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.openejb.zinsrechner;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class ZinsrechnerTest {

	private InitialContext initialContext;

	@Before
	public void setUp() throws Exception {
		Properties properties = new Properties();
		properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");
		properties.setProperty("openejb.deployments.classpath.include",
				".*/classes/");
		properties.setProperty("openejb.deployments.classpath.exclude", ".*");

		initialContext = new InitialContext(properties);
	}

	@Test
	public void testCalculatorViaRemoteInterface() throws Exception {
		Object object = initialContext.lookup("ZinsrechnerImplRemote");

		Assert.assertNotNull(object);
		Assert.assertTrue(object instanceof Zinsrechner);
		Zinsrechner calc = (Zinsrechner) object;
		Assert.assertEquals(2318, calc.berechneSparsumme(2000, 5, 0.03));

	}

}
