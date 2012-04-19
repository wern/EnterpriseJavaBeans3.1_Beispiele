/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/
package sicherheit.authentifizierung;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.security.auth.login.LoginContext;

import com.sun.security.auth.callback.TextCallbackHandler;

public class ClientMitDialog {

	@SuppressWarnings("unchecked")
	void authentifiziereViaJNDI() throws Exception {
		Properties properties = new Properties();

		System.out.println("Uebertrage Daten ueber den Namensdienst\n---");
		System.out.print("login>");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String login = in.readLine();
		System.out.print("passwort>");
		String passwort = in.readLine();

		properties.put(Context.SECURITY_PRINCIPAL, login);
		properties.put(Context.SECURITY_CREDENTIALS, passwort);

		// Hierfuer wird eine andere Factory benötigt
		properties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.security.jndi.JndiLoginInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "jnp://localhost:1099");

		Tor tor = (Tor) new InitialContext(properties).lookup("Tor/remote");
		tor.klopfen();
		System.out.println("Die Ausgabe steht im Server-Log.");
	}

	void authentifiziereViaJAAS() throws Exception {
		System.out.println("Login via JAAS\n---");
		LoginContext lc = new LoginContext("client-login",
				new TextCallbackHandler());
		lc.login();
		Tor tor = (Tor) new InitialContext().lookup("Tor/remote");
		tor.klopfen();
		System.out.println("Die Ausgabe steht im Server-Log.");
		lc.logout();
	}

	void ohneAuthentifizierung() throws Exception {
		System.out
				.println("Es wird keine Authentifizierung durchgefuehrt!\n---");
		Tor tor = (Tor) new InitialContext().lookup("Tor/remote");
		tor.klopfen();
		System.out.println("Die Ausgabe steht im Server-Log.");
	}

	public static void main(String[] args) throws Exception {
		System.out
				.print("Bitte Art der Authentifizierung waehlen:\n1 - JNDI\n2 - JAAS\n>");
		String auswahl = new BufferedReader(new InputStreamReader(System.in))
				.readLine();
		System.out
				.println("---\nUnterstuetzte Benutzer:\nJan (angestellter), Passwort: werner\nHanser (praktikant), Passwort: ejb3\n--");

		ClientMitDialog client = new ClientMitDialog();
		if (auswahl.indexOf('1') != -1) {
			client.authentifiziereViaJNDI();
		} else if (auswahl.indexOf('2') != -1) {
			client.authentifiziereViaJAAS();
		} else {
			client.ohneAuthentifizierung();
		}
	}
}
