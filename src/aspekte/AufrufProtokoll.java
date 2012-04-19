/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package aspekte;

import java.lang.annotation.*;

/** Annotationstyp für die Angabe des Protokolldetailgrads
  * an einer zu protokollierenden Methode */
@Target({ElementType.METHOD, ElementType.TYPE}) @Retention(RetentionPolicy.RUNTIME)
public @interface AufrufProtokoll {
    AufrufProtokollTyp value() default AufrufProtokollTyp.ALLES;
}
