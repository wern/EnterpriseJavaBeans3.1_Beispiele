/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/
package singleton.zinsdienst;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.interceptor.Interceptors;

@Startup
@Singleton(name = "Zinssatz")
@Interceptors(value = { LifeCycleMonitor.class })
public class ZinssatzSingletonImpl implements Zinssatz {

	private static final int DEFAULT_ZINS = -1;

	private static final long CACHE_TIMEOUT_IN_MINUTEN = 1;

	private static final Map<Integer, Double> zinssatzCache = new HashMap<Integer, Double>();

	@Resource
	private SessionContext ctx;

	@Lock(READ)
	public double ermittleZinssatz(int anlagebetrag, int jahre) {
		Double zinssatz = zinssatzCache.get(jahre);
		if (zinssatz == null) {
			zinssatz = zinssatzCache.get(DEFAULT_ZINS);
		}

		return zinssatz / 100;
	}

	@PostConstruct
	public void registriereTimer() {
		ctx.getTimerService().createTimer(60000 * CACHE_TIMEOUT_IN_MINUTEN,
		"Timeout zur Cache-Invalidierung.");
		initCache();
	}

	private void initCache() {
		System.out.println("initCache()");

		zinssatzCache.put(DEFAULT_ZINS, 5.5);
		zinssatzCache.put(1, 3.5);
		zinssatzCache.put(2, 3.5);
		zinssatzCache.put(3, 3.5);
		zinssatzCache.put(4, 4.5);
	}

	@Timeout
	@Lock(WRITE)
	public void clearCache(Timer timer) {
		System.out.println("clearCache()");
		zinssatzCache.clear();
		initCache();
	}
}
