package abfragen;

import abfragen.Fahrzeug;
import abfragen.Mieter;
import abfragen.Station;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated("EclipseLink - Sat Apr 17 21:54:23 CEST 2010")
@StaticMetamodel(Fahrt.class)
public class Fahrt_ { 

	public static volatile SingularAttribute<Fahrt, Mieter> fahrer;
	public static volatile SingularAttribute<Fahrt, Integer> nr;
	public static volatile SingularAttribute<Fahrt, Station> von;
	public static volatile SingularAttribute<Fahrt, Date> abholung;
	public static volatile SingularAttribute<Fahrt, Fahrzeug> mit;
	public static volatile SingularAttribute<Fahrt, Date> rueckgabe;
	public static volatile SingularAttribute<Fahrt, Station> nach;

}