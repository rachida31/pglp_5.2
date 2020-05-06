package uvsq.M1.td1.Exo5_2;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.*;

import org.junit.Before;


import uvsq.M1.td1.Exo5_2.Personnel.PersonnelBuilder;

public class TestJDBCGroupe {
	private  ArrayList<InterfacePersonne> personnel ;
	private  ArrayList<InterfacePersonne> personnel1 ;
	private Personnel  p1;
	private CompositePersonne perso,perso2;
	private CompositePersonne perso1;
	private CompositeDAO<CompositePersonne> s;

@Before()
public void setUp() {

	this.personnel=new ArrayList<InterfacePersonne>();
	this.personnel1=new ArrayList<InterfacePersonne>();

	p1=(new PersonnelBuilder("aissou", "rachel", Fonction.directeur).
	date(LocalDate.parse("2011-12-01")).
	addNumeroTelephone(new ClasseTele(Telephone.fixePro,"06 89 51 12 53")).build());
	personnel.add(p1);
		personnel.add(new PersonnelBuilder("aissou", "aylan", Fonction.chargeMission).
	date(LocalDate.parse("2014-11-01")).
	addNumeroTelephone(new ClasseTele(Telephone.portable,"06 89 51 12 53")).build());
		personnel.add(new PersonnelBuilder("papi", "silas", Fonction.chargeMission).
	date(LocalDate.parse("2019-07-01")).
	addNumeroTelephone(new ClasseTele(Telephone.fixePerso,"06 89 51 12 53")).build());
		perso=new CompositePersonne(personnel,"comptabilité");
		personnel1.add(new PersonnelBuilder("khaled", "aylan", Fonction.chargeMission).
				date(LocalDate.parse("2014-11-01")).
				addNumeroTelephone(new ClasseTele(Telephone.portable,"06 89 51 12 53")).build());
					personnel1.add(new PersonnelBuilder("mami", "silas", Fonction.chargeMission).
				date(LocalDate.parse("2019-07-01")).
				addNumeroTelephone(new ClasseTele(Telephone.fixePerso,"06 89 51 12 53")).build());
					perso1=new CompositePersonne(personnel1,"informatique");
					
	s= new CompositeDAO<CompositePersonne>();
}

}
