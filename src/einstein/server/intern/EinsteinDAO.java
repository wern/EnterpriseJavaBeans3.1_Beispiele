package einstein.server.intern;

import java.util.List;

import javax.ejb.Local;

import einstein.server.intern.entities.SpielerEntity;
import einstein.server.intern.entities.SteinEntity;
import einstein.server.intern.entities.TischEntity;
import einstein.server.oeffentlich.Position;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.Spieler;

/** Persistenz-Funktionen f�r das Einstein-Spiel, nach dem Data-Access-Object-Muster
 * in eine separate EJB ausgelagert. Das Interface ist relativ umfangreich und wurde
 * nur deshalb nicht noch einmal untergliedert, weil die meisten Funktionen nur wenige
 * Zeilen Code ben�tigen und die Implementierungsklasse daher immer noch recht
 * �bersichtlich ist. Die Funktionen f�r die reine Spielerverwaltung lie�en sich aber
 * beispielsweise sehr sauber rausl�sen. Eine gute �bung :-)<br>
 * Da die Komponente nicht �ffentlich ist, taucht sie nur in einem Package auf, das
 * dem Anwender bereit deutlich macht, dass es sich hier um ein ausschlie�lich intern
 * genutztes Interface handelt (in diesem Fall das Package einstein.server.internal).
 * Au�erdem wird hier bewusst ein lokales Interface verwendet, so dass zur�ckgegebene
 * Entities im Zustand "managed" verbleiben und die aufrufenden Komponenten bei Bedarf
 * gefahrlos �ber die Relationen der Entities traversieren k�nnen. Manipulationen der
 * Entities gelangen dadurch au�erdem ohne explizites Zur�ckschreiben in die Datenbank.
 * 
 * @author less02
 *
 */
@Local public interface EinsteinDAO {
	
	public TischEntity findeTisch(int tischNr);
	
	public TischEntity tischAufrischen(TischEntity tisch);
	
	public TischEntity neuerTisch(boolean computerGegner);
	
    public boolean loescheLeerenTisch(TischEntity tisch, String spieler);

	public void speichereAufstellung(TischEntity tisch, List<SteinEntity> aufstellung);
	
	public List<SteinEntity> aktuelleAufstellung(TischEntity tisch);

	public SteinEntity findeStein(int steinId);
    
    public boolean steinPasstZuWurf(SteinEntity stein, TischEntity tisch, boolean spielerA);

    public boolean schlageStein(Position pos, TischEntity tisch);

    boolean hatSteine(boolean spielerA, TischEntity tisch);

    /***************** Spielerverwaltung *****************/

    public SpielerEntity spielerAnlegen(String name, String passwort);

    public SpielerEntity findeSpieler(String spielerName);

	public boolean spielerLoeschen(String name, String passwort);

    public List<Spieler> findeAnwesendeSpieler();

    public List<Spieler> findeBesteSpieler();
    
    /***************** Und noch etwas Spezielles **************/

    /**
     * F�hrt die notwendigen Operationen zum beenden einer Einstein-Sitzung
     * aus, d.h. Entfernen des Spielers vom aktuellen Tisch, sofern der sich
     * abmeldende Spieler an einem Tisch sitzt, und Zur�cksetzen des Anwesenheitsflags
     * des Spielers. Diese Funktion ist schon ein wenig mehr als reine Persistenz-
     * logik, muss aber au�erhalb der EinsteinSitzung-Bean implementiert werden,
     * da sie auch aus Lifecycle-Callbacks mit unsicherem Transaktionskontext heraus
     * aufgerufen wird. Die Auslagerung als eigene Methode in die DAO-Bean stellt
     * das Vorhandensein eines Kontexts sicher.
     */
    public void sitzungBeenden(EinsteinDelegate tischDelegate, String spielerName)
        throws SpielException;

}
