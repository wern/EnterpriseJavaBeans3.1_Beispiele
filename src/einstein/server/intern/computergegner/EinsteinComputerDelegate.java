package einstein.server.intern.computergegner;

import einstein.server.intern.EinsteinDAO;
import einstein.server.intern.EinsteinSpielerDelegate;
import einstein.server.oeffentlich.Partie;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStatus;
import einstein.server.oeffentlich.SpielStatusException;
import einstein.server.oeffentlich.Zug;

/**
 * Ableitung des {@link einstein.server.intern.EinsteinSpielerDelegate} zur
 * Realisierung des Computerspielers. Die Klasse wird von der Message-Driven
 * Bean {@link einstein.server.intern.computergegner.ComputerGegner} aus
 * instanziiert, die anschließend nacheinander die Funktionen {@link #wuerfeln()}
 * und {@link #ziehen(int)} in zwei separaten Transaktionen aufruft. Auf diese
 * Weise kriegen die anderen Spieler auch bei einer langwierigen Berechnung
 * des nächsten Zuges zunächst mit, dass der Computer gewürfelt hat und in
 * einem zweiten Schritt über den besten Zug nachdenkt. Der vorimplementierte
 * Monte-Carlo-Algorithmus (siehe Klasse {@link einstein.server.intern.computergegner.MonteCarlo}
 * braucht zwar nur wenige Millisekunden, aber das muss ja nicht so bleiben,
 * wenn sich jemand ein besseres Verfahren ausdenkt.
 * 
 * @author less02
 *
 */
public class EinsteinComputerDelegate extends EinsteinSpielerDelegate {

    public EinsteinComputerDelegate(int tischNr, EinsteinDAO dao)
        throws SpielException {
        super(tischNr, null, dao);
    }
    
    @Override
    protected void initialisieren() {}

    @Override
    public int wuerfeln() throws SpielException, SpielStatusException {
        Partie partie;
        do {
            partie = partieAbfragen();
            try { Thread.sleep(10); }
            catch(InterruptedException ix) {}
        } while(partie.getStatus().spielLaeuft() &&
                partie.getStatus() != SpielStatus.B_WUERFELT);
        if (partie.getStatus() == SpielStatus.B_WUERFELT)
        	return super.wuerfeln();
        else
        	return 0;
    }
    
    public void ziehen(int wuerfelWurf) throws SpielException, SpielStatusException {
        MonteCarlo berater = new MonteCarlo();
        Partie partie = partieAbfragen();
        if (partie.getStatus() == SpielStatus.B_AM_ZUG) {
            Zug zug = berater.naechsterZug(partie.getAufstellung(), false, wuerfelWurf);
            ziehen(zug);
        }
    }

}
