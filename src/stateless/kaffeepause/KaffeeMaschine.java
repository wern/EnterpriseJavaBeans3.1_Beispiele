package stateless.kaffeepause;

import java.util.concurrent.Future;

import javax.ejb.Asynchronous;
import javax.ejb.Remote;

@Remote
public interface KaffeeMaschine {


	public Future<Kaffee> kocheKaffee();

}