/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package datasource;
import java.util.Properties;

import javax.naming.InitialContext;

public class DAOClient {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		DAO dao = (DAO)context.lookup("DAOImpl/remote");
		System.out.println(dao.zaehleTimer());
	}
}
