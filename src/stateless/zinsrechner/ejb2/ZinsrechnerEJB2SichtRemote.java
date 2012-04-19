/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package stateless.zinsrechner.ejb2;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface ZinsrechnerEJB2SichtRemote extends EJBObject{
	public int berechneSparSumme(int anlagebetrag, int jahre, double zinssatz) throws RemoteException;
}
