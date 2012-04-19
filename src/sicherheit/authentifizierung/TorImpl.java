/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package sicherheit.authentifizierung;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless(name = "Tor")
public class TorImpl implements Tor {

	@Resource
	SessionContext ctx;

	public void klopfen() {
		Principal aufrufer;
		aufrufer = ctx.getCallerPrincipal();
		System.out.println((aufrufer != null ? aufrufer.getName()
				: "Ein Unbekannter")
				+ " steht vor der Tuer.");
	}
}
