/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.webrechner;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
@Stateless(name="webrechner")
@Interceptors({WebserviceInterceptor.class})
public class WebrechnerImpl implements Webrechner {
	
	@WebMethod
	public WSRechenergebnis multiplizieren(int a, int b) {
		return new WSRechenergebnis(a * b);
	}
	
	@WebMethod
	@SchwereFehler({10})
	public WSRechenergebnis dividieren(int a, int b) {
		if (a % b != 0)
			return new WSRechenergebnis(10, "Das geht nicht ohne Rest auf");
		return new WSRechenergebnis(a / b);
	}
	
}
