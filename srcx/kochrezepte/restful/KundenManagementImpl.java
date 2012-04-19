package kochrezepte.restful;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Local(KundenManagement.class)
@Stateless
public class KundenManagementImpl implements KundenManagement {

	@PersistenceContext
	private EntityManager em;

	public Kunde erzeuge(String name, int kundenNummer) {
		Kunde kunde = new Kunde(kundenNummer, name);
		em.persist(kunde);
		// Hier muesste eigentlich auch die URL des Kunden gesetzt werden,
		// aus Gruenden der Einfachheit haben wir hier auf diese Details
		// der RESTful-Architektur verzichtet.
		return kunde;
	}

	public Kunde finde(int kundenNummer) {
		return em.find(Kunde.class, kundenNummer);
	}

	public void loesche(int kundenNummer) {
		Kunde kunde = finde(kundenNummer);
		if(kunde!=null){
			em.remove(kunde);
		}
	}

}
