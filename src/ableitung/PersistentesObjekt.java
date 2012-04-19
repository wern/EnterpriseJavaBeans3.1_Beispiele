/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package ableitung;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Beispiel f�r eine Basisklasse, die als Mapped-Superclass
 * Annotiert wird. Sie enth�lt eine ID-Definition, die unabh�ngig
 * von der fachlichen Bedeutung der abgeleiteten Entity-Klassen
 * applikationsweit einheitlich eingesetzt werden k�nnte.
 */
@MappedSuperclass
public abstract class PersistentesObjekt implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(updatable=false) protected int nr;
}
