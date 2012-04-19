/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/
package sicherheit.authentifizierung;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ClientMitAnmeldung {

	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.security.jndi.JndiLoginInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "jnp://localhost:1099");

		System.out.println("Anmeldung am Server als Jan.");
		properties.put(Context.SECURITY_PRINCIPAL, "Jan");
		properties.put(Context.SECURITY_CREDENTIALS, "werner");

		Tor tor = (Tor) new InitialContext(properties).lookup("Tor/remote");
		tor.klopfen();
		System.out.println("Die Ausgabe steht im Server-Log.");
	}
}
