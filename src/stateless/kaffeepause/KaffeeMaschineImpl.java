package stateless.kaffeepause;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;


@Stateless(name="KaffeeMaschine")
public class KaffeeMaschineImpl implements KaffeeMaschine {

	@Asynchronous
	public Future<Kaffee> kocheKaffee() {
		try {
			// Nur zur Veranschaulichung bitte
			// NICHT in produktivem Code verwenden!
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new AsyncResult<Kaffee>(new Kaffee(10));
	}

}
