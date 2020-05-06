package uvsq.M1.td1.Exo5_2;

import java.time.LocalDate;

import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.*;
import uvsq.M1.td1.Exo5_2.Personnel.PersonnelBuilder;

public class TestJDBCPersonnel {

	
	private Personnel  personnel,personnel2;
	private Personnel  personnel1;
	DerbyConnexion conn;
	JDBCPersonnel p ;
@Before()
public void setUp() {

	personnel=(new PersonnelBuilder("ouchene", "siham", Fonction.directeur).
date(LocalDate.parse("2011-12-01")).
addNumeroTelephone(new ClasseTele(Telephone.fixePro,"06 89 51 12 53")).build());
	personnel1=(new PersonnelBuilder("aissou", "rachel", Fonction.directeur).
			date(LocalDate.parse("2011-12-01")).
			addNumeroTelephone(new ClasseTele(Telephone.fixePro,"06 89 51 12 53")).build());
	conn =new DerbyConnexion();
	conn.createtable();
	p= new JDBCPersonnel();
	
}
	
@Test(expected=InExistTupleException.class)
public void testCreation() throws Exception
{
	p.create(personnel);
	personnel2=p.read("ouchene");
	/*assertTrue(personnel2.getPrenom().equals("siham"));
	assertTrue(personnel2.getFonc().equals(Fonction.directeur));
	assertTrue(personnel2.getDate().compareTo(LocalDate.parse("2011-12-01"))==0);*/
}

@Test
public void testUpdate() throws Exception
{
	String requete="UPDATE Personnel SET fonction = ?, "
							+ "date = ? WHERE nom = ?";
	personnel=(new PersonnelBuilder("ouchene", "siham", Fonction.chargeMission).
			date(LocalDate.parse("1995-01-16")).
			addNumeroTelephone(new ClasseTele(Telephone.fixePro,"06 89 51 12 53")).build());
	p.update(personnel, requete);
	personnel2=p.read("ouchene");
	assertTrue(personnel2.getPrenom().equals("siham"));
	assertFalse(personnel2.getFonc().equals(Fonction.directeur));
	assertTrue(personnel2.getFonc().equals(Fonction.chargeMission));
	//assertTrue(personnel2.getDate().compareTo(LocalDate.parse("1995-01-16"))==0);
	//assertFalse(personnel2.getDate().compareTo(LocalDate.parse("2011-12-01"))==0);

}


}
