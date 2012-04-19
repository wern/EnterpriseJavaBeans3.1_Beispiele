package abfragen;

import abfragen.Fahrt;
import abfragen.Station;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated("EclipseLink - Sat Apr 17 21:54:23 CEST 2010")
@StaticMetamodel(Fahrzeug.class)
public class Fahrzeug_ { 

	public static volatile SingularAttribute<Fahrzeug, String> kennzeichen;
	public static volatile SingularAttribute<Fahrzeug, Station> standort;
	public static volatile SingularAttribute<Fahrzeug, Integer> baujahr;
	public static volatile SingularAttribute<Fahrzeug, String> modell;
	public static volatile SetAttribute<Fahrzeug, Fahrt> fahrten;

}