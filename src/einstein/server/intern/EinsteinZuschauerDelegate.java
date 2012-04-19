package einstein.server.intern;

import einstein.server.intern.entities.SpielerEntity;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStand;
import einstein.server.oeffentlich.SpielStatus;
import einstein.server.oeffentlich.Zug;

/**  Enthält die Logik für die Teilnahme eines Spielers an einem Spieltisch
 * als Zuschauer. Alle Funktionen, die sich auf die aktive Teilnahme beziehen,
 * wie z.B. {@link #wuerfeln()} werfen bei Aufruf eine
 * {@link einstein.server.oeffentlich.SpielException}.
 * 
 * @author jlessner
 */
public class EinsteinZuschauerDelegate extends EinsteinDelegate {

	public EinsteinZuschauerDelegate(int pTischNr, String spielerName, EinsteinDAO dao)
		throws SpielException {
		super(pTischNr, spielerName, dao);
        SpielerEntity zuschauer = findeSpieler();
		zuschauer.setZuschauerAn(tisch);
	}

	public boolean verlassen() throws SpielException {
        SpielerEntity zuschauer = findeSpieler();
        zuschauer.setZuschauerAn(null);
        return dao.loescheLeerenTisch(tisch, spielerName);
	}

	public int wuerfeln() throws SpielException {
		throw new SpielException("Du bist nur Zuschauer");
	}

	public SpielStand ziehen(Zug pZug) throws SpielException {
		throw new SpielException("Du bist nur Zuschauer");
	}

	public SpielStatus bereit() throws SpielException {
		throw new SpielException("Du bist nur Zuschauer");
	}

}
