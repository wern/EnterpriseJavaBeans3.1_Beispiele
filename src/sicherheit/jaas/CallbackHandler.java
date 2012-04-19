/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package sicherheit.jaas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class CallbackHandler implements
		javax.security.auth.callback.CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (Callback callback : callbacks) {
			handle(callback);
		}

	}

	private void handle(Callback callback) throws IOException,
			UnsupportedCallbackException {
		if (callback instanceof NameCallback) {
			System.out.print(((NameCallback) callback).getPrompt());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			String login = in.readLine();
			((NameCallback) callback).setName(login);
		} else if (callback instanceof PasswordCallback) {
			System.out.print(((PasswordCallback) callback).getPrompt());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			String passwort = in.readLine();
			((PasswordCallback) callback).setPassword(passwort.toCharArray());
		} else if (callback instanceof TextOutputCallback) {
			System.out.println(((TextOutputCallback) callback).getMessage());
		} else {
			throw new UnsupportedCallbackException(callback);
		}

	}

}
