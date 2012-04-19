/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package sicherheit.jaas;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;

public class InvertiererLoginModule implements LoginModule {
	Subject subject;

	CallbackHandler callback;

	boolean erfolg = false;
	
	Principal callerPrincipal;

	List<Principal> callerRoles;

	// jetzt wird's JBoss-spezifisch !!!
	SimpleGroup jbossPrincipal;

	SimpleGroup jbossRollen;


	public boolean abort() throws LoginException {
		callerPrincipal = null;
		callerRoles = null;

		jbossPrincipal = null;
		jbossRollen = null;

		erfolg = false;

		return true;
	}

	public boolean commit() throws LoginException {
		if (erfolg) {
			// jetzt wird's JBoss-spezifisch !!!
			jbossPrincipal = new SimpleGroup("CallerPrincipal");
			jbossPrincipal.addMember(callerPrincipal);

			jbossRollen = new SimpleGroup("Roles");
			for (Principal rolle : callerRoles) {
				jbossRollen.addMember(rolle);
			}

			subject.getPrincipals().add(jbossPrincipal);
			subject.getPrincipals().add(jbossRollen);
		}
		return true;
	}

	public void initialize(Subject subject, CallbackHandler callback,
			Map<String, ?> sharedState, Map<String, ?> config) {
		this.subject = subject;
		this.callback = callback;
	}

	public boolean login() throws LoginException {
		NameCallback username = new NameCallback("username>");
		PasswordCallback passwort = new PasswordCallback("passwort>", false);

		try {
			callback.handle(new Callback[] { username, passwort });
		} catch (IOException e) {
			throw new LoginException(e.getClass() + ":" + e.getMessage());
		} catch (UnsupportedCallbackException e) {
			throw new LoginException(e.getClass() + ":" + e.getMessage());
		}

		if (username.getName() != null && passwort.getPassword() != null) {
			if (username.getName().length() != passwort.getPassword().length) {
				throw new FailedLoginException("Passwort ist falsch!");
			}
			for (int i = 0; i < username.getName().length(); i++) {
				if (username.getName().charAt(i) != passwort.getPassword()[passwort
						.getPassword().length
						- i]) {
					throw new FailedLoginException("Passwort ist falsch!");
				}
			}
		} else {
			throw new FailedLoginException(
					"Username oder Passwort nicht angegeben!");
		}

		callerPrincipal = new SimplePrincipal(username.getName());
		callerRoles = new ArrayList<Principal>();
		callerRoles.add(new SimplePrincipal("Rolle1"));
		callerRoles.add(new SimplePrincipal("Rolle2"));

		erfolg = true;

		return true;
	}

	public boolean logout() throws LoginException {
		if (erfolg) {
			// jetzt wird's JBoss-spezifisch !!!
			subject.getPrincipals().remove(jbossPrincipal);
			subject.getPrincipals().remove(jbossRollen);
		}

		callerPrincipal = null;
		callerRoles = null;

		jbossPrincipal = null;
		jbossRollen = null;

		return true;
	}

}
