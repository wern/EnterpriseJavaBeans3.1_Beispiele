/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/
package stateless.zinsdienst;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LifeCycleMonitor {
	Log log = LogFactory.getLog(LifeCycleMonitor.class);

	@PostConstruct
	public void beanErzeugt(InvocationContext ic) throws Exception{
		System.out.println("Es wurde eine Bean vom Typ "+ ic.getTarget().getClass().getName()+" erzeugt.");
		ic.proceed();
	}

	@PreDestroy
	public void beanEntfernt(InvocationContext ic) throws Exception{
		System.out.println("Es wird eine Bean vom Typ "+ ic.getTarget().getClass().getName()+" entfernt.");
		ic.proceed();
	}
}
