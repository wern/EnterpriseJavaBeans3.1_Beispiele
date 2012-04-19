package einstein.client.gui.modell;

import einstein.server.oeffentlich.VerwaltungsException;

/**
 * Das Modell haelt den Benutzernamen und den Anwendungstitel und 
 * bietet Funktionalitaet zum An- und Abmelden sowie Starten und 
 * Stoppen des Aktualisierungsprozesses.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class LoginModell {
	
	protected static String benutzerName = null;
	protected String anwendungsTitel = "EinStein";
	protected EinSteinViewInterface view = null;
	protected boolean run = true;
	protected static LoginModell modell = null;
	
	public LoginModell() {}
	
	/**
	 * Singleton fuer die LoginModell-Instanz.
	 * 
	 * @return LoginModell-Instanz
	 */
	public static LoginModell getLoginModell() {
		if (modell == null) {
			modell = new LoginModell();
		}
		return modell;
	}
	
	public static String getBenutzerName() {
		return benutzerName;
	}
	
	public static void setBenutzerName(String name) {
		benutzerName = name;
	}
	
	public void setViewInterface(EinSteinViewInterface view) {
		this.view = view;
	}
	
	public String getAnwendungsTitel() {
		return anwendungsTitel;
	}
	
	public void abmelden() throws InterruptedException {
		this.stoppeAktualisierungsProzess();
		// um sicher zu sein, dass der Aktualisierungsprozess beendet wurde
		// (denn nach einem Abmelden, ist die Sitzung beendet und dann kann nicht
		//  mehr aktualisiert werden)
		Thread.sleep(1000);
		EinsteinSitzungsSynchronisierer.abmelden();
	}
	
	public Integer getAktuellerTisch() {
		return EinsteinSitzungsSynchronisierer.getAktuellerTisch();
	}
	
	public void login(String spielerName) throws Exception {
		try { 
	    	EinsteinSitzungsSynchronisierer.registrieren(spielerName, spielerName);
	    } catch(VerwaltungsException vx) {}
	    
	    EinsteinSitzungsSynchronisierer.anmelden(spielerName);
	    setBenutzerName(spielerName);
	}
	
	public void stoppeAktualisierungsProzess() {
		run = false;
	}
	
	public void starteAktualisierungsProzess() {
		run = true;
		new Thread((new Runnable() {
	            public void run() {
	            	while (run) {
	            		try {
	                		view.update();
	                		Thread.sleep(1000);
	                	} catch (Exception ex) {
	                		ex.printStackTrace();
	                		run = false;
	                	} 
	            	}
	            }
		})).start();
	}
    
}
