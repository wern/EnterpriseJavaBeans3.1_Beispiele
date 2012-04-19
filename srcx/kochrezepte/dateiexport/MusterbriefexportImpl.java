/**********************************************************
 * Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
 * Das EJB-Praxisbuch fuer Ein- und Umsteiger
 * Von Werner Eberling und Jan Lessner
 * Hanser Fachbuchverlag Muenchen, 2011
 * http://www.hanser.de/buch.asp?isbn=3-446-42259-5
 * Feedback an ejb3buch@werner-eberling.de
 **********************************************************/
package kochrezepte.dateiexport;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;

@Stateful(name="briefexport")
public class MusterbriefexportImpl implements Musterbriefexport, SessionSynchronization {

	public static final String TEMPORAERE_EXTENSION = ".tmp";
	public static final String ENDGUELTIGE_EXTENSION = ".txt";
	
	@Resource String exportpfad = ".";
	@EJB Dateinummernproduzent dateinummernproduzent;
	
	List<File> exportdateien = new ArrayList<File>();
	
	public void exportiereMusterbrief(Kunde empfaenger, String musterbrieftext) throws IOException {
		long dateinummer = dateinummernproduzent.naechsteDateinummer();
		File exportdatei = new File("brief-" + dateinummer + TEMPORAERE_EXTENSION);
		exportdatei.createNewFile();
		exportdateien.add(exportdatei);
		PrintStream ps = new PrintStream(exportdatei);
		String personalisierterBrieftext = personalisiereBrief(empfaenger, musterbrieftext);
		ps.println(personalisierterBrieftext);
		ps.close();
	}

	private String personalisiereBrief(Kunde empfaenger, String brief) {
		return brief.replace("$(name)", empfaenger.getName()).
			replace("$(vorname)", empfaenger.getVorname()).
			replace("$(kundennummer)", Long.toString(empfaenger.getKundennummer()));
	}

	public void afterBegin() throws EJBException, RemoteException {
		exportdateien.clear();
	}

	public void beforeCompletion() throws EJBException, RemoteException {
		for (int d = 0; d < exportdateien.size(); d++) {
			File exportdatei = exportdateien.get(d);
			File umbenannteDatei = new File(exportdatei.getName().replace(TEMPORAERE_EXTENSION, ENDGUELTIGE_EXTENSION));
			exportdatei.renameTo(umbenannteDatei);
			exportdateien.set(d, umbenannteDatei);
		}
	}

	public void afterCompletion(boolean commit) throws EJBException, RemoteException {
		if (!commit) {
			for (File exportdatei: exportdateien)
				exportdatei.delete();
		}
	}

}
