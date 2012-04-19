/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package stateless.zinsdienstOhneSchnittstelle;

import java.util.Hashtable;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.interceptor.Interceptors;

@Stateless(name = "Zinssatz")
@Interceptors(value = { LifeCycleMonitor.class })
public class ZinssatzImpl {

	static int DEFAULT_ZINS = -1;

	static long CACHE_TIMEOUT_IN_MINUTEN = 1;

	@Resource
	SessionContext ctx;

	static final Map<Integer, Double> zinssatzCache = new Hashtable<Integer, Double>();

	public double ermittleZinssatz(int anlagebetrag, int jahre) {
		if (zinssatzCache.isEmpty()) {
			initCache();
		}
		Double zinssatz = zinssatzCache.get(jahre);
		if (zinssatz == null) {
			zinssatz = zinssatzCache.get(DEFAULT_ZINS);
		}

		return zinssatz / 100;
	}

	private void initCache() {
		System.out.println("initCache()");

		zinssatzCache.put(DEFAULT_ZINS, 5.5);
		zinssatzCache.put(1, 3.5);
		zinssatzCache.put(2, 3.5);
		zinssatzCache.put(3, 3.5);
		zinssatzCache.put(4, 4.5);

		ctx.getTimerService().createTimer(60000 * CACHE_TIMEOUT_IN_MINUTEN,
				"Timeout zur Cache-Invalidierung.");
	}

	@Timeout
	public void clearCache(Timer timer) {
		System.out.println("clearCache()");
		zinssatzCache.clear();
	}
}
