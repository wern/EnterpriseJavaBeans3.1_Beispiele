/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.webrechner;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class WebserviceInterceptor {

    //Container injiziert an dieser Stelle den EJBContext
    // der aufgerufenen Bean. Der Kontext wird f�r das Zur�ckrolle
    // der Transaktion ben�tigt. EJBContext ist das Basisinterface
    // f�r alle Komponentenkontextklassen, damit der Interceptor
    // f�r jegliche Typen von SessionBeans verwendbar bleibt
	@Resource EJBContext ejbContext;
	
    @AroundInvoke public Object fehleranalyse(InvocationContext ic) throws Exception {
    	try {
    		WSErgebnis ergebnis = (WSErgebnis)ic.proceed();
    		if (!ergebnis.erfolg())
    			aufSchwerenFehlerPruefen(ergebnis, ic);
    		return ergebnis;
    	}
        //Im Applikationscode unbehandelte Exception abfangen und in eine Fehlerr�ckgabe umwandeln
    	catch(Exception x) {
    		Class<?> rueckgabetyp = ic.getMethod().getReturnType();
    		WSErgebnis ergebnis = (WSErgebnis)rueckgabetyp.newInstance();
    		ergebnis.fehlertext = x.getMessage();
    		return ergebnis;
    	}
    }

    //Pr�ft, ob an der aufgerufenen Methode eine Annotation mit
    // als schwerwiegend eingestuften Fehlercodes vorliegt. Wenn
    // das der Fall ist, und das vorliegende Ergebnis einen dieser
    // Codes aufweist, wird f�r alle F�lle eine etwaige laufende
    // Transaktion zur�ckgerollt.
	private void aufSchwerenFehlerPruefen(WSErgebnis ergebnis, InvocationContext ic) {
		SchwereFehler schwereFehler = ic.getMethod().getAnnotation(SchwereFehler.class);
		if (schwereFehler == null)
			return;
		for (int schwererFehlercode: schwereFehler.value()) {
			if (ergebnis.fehlercode == schwererFehlercode) {
				ejbContext.setRollbackOnly();
				return;
			}
		}
	}
}
