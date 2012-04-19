/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package aspekte;

import java.lang.reflect.Method;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/** Interceptor-Klasse für die Protokollierung von EJB-Methodenaufrufen.
 * Die Steuerung des Detailgrads des Protokolls erfolgt über
 * AufrufProtokoll-Annotationen an den zu protokollierenden Methoden
 * Wird keine Annotation angegeben, erfolgt auch keine Protokollierung
 * des Aufrufs. Default ist ansonsten {@link aspekte.AufrufProtokollTyp#ALLES}.
 */
public class ProtokollAspekt {

    protected void protokolliereMethode(StringBuffer protokoll, Object bean, Method methode, AufrufProtokollTyp typ) {
        if (typ != AufrufProtokollTyp.NICHTS) {
            protokoll.append(bean.getClass().getSimpleName() + ".");
            protokoll.append(methode.getName());
        }
    }

    protected void protokolliereAufrufparameter(StringBuffer protokoll, Object[] params, AufrufProtokollTyp typ) {
        if (typ == AufrufProtokollTyp.ALLES) {
            protokoll.append("(");
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof String)
                    protokoll.append("\"" + params[i] + "\"");
                else
                    protokoll.append(params[i]);
                if (i+1 < params.length)
                    protokoll.append(", ");
            }
            protokoll.append(")");
        }
        else
            protokoll.append("()");
    }

    protected void protokolliereErgebnis(StringBuffer protokoll, Object wert, AufrufProtokollTyp typ) {
        if (typ == AufrufProtokollTyp.ALLES)
            protokoll.append(" = " + wert);
        schreibeProtokoll(protokoll);
    }

    protected void schreibeProtokoll(StringBuffer protokoll) {
        System.out.println(protokoll.toString());
    }
    
    @AroundInvoke public Object protokolliere(InvocationContext ic) throws Exception {
        StringBuffer protokoll = new StringBuffer(); /* Protokoll initialisieren */
        AufrufProtokoll prot =          /* Detailgrad abfragen */
            ic.getMethod().getAnnotation(AufrufProtokoll.class);
        AufrufProtokollTyp ptyp = (prot == null) ? AufrufProtokollTyp.NICHTS : prot.value();
        protokolliereMethode(protokoll, ic.getTarget(), ic.getMethod(), ptyp);
        protokolliereAufrufparameter(protokoll, ic.getParameters(), ptyp);
        try {
            Object ergebnis = ic.proceed();
            protokolliereErgebnis(protokoll, ergebnis, ptyp);
            return ergebnis;
        }
        catch(Exception x) {
            /* Fehler in Kernmethode, Exception protokollieren */
            protokolliereErgebnis(protokoll, x, ptyp);
            throw x;
        }
    }
}
