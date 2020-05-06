package uvsq.M1.td1.Exo5_2;

import java.time.LocalDate;
import java.util.ArrayList;



import uvsq.M1.td1.Exo5_2.Personnel.PersonnelBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception 
    {
    	DerbyConnexion conn;
    	 JDBSCompositePersonnel  pg ;
    		  ArrayList<InterfacePersonne> personnel ;
			  ArrayList<InterfacePersonne> personnel1 ;
			 Personnel  p1;
			 CompositePersonne perso,perso2;
			CompositePersonne perso1;
			CompositeDAO<CompositePersonne> s;

	

			personnel=new ArrayList<InterfacePersonne>();
			personnel1=new ArrayList<InterfacePersonne>();

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
        System.out.println( "Hello World!" );
     
        conn =new DerbyConnexion();
    	conn.createtable();
    	pg= new JDBSCompositePersonnel();
    	pg.create(perso1);
    	//pg.delete(perso1);
    	CompositePersonne groupe=	pg.read("informatique");
    	groupe.print();
    	pg.update(perso, " ");
    	/*p= new JDBCPersonnel();
    	String requete="UPDATE Personnel SET fonction = ?, "
				+ "date = ? WHERE nom = ?";
    	System.out.println(requete.getClass());
    	Personnel personnel=(new PersonnelBuilder("ouchene", "siham", Fonction.chargeMission).
    			date(LocalDate.parse("1995-01-16")).
    			addNumeroTelephone(new ClasseTele(Telephone.fixePro,"06 89 51 12 53")).build());
    	System.out.println(p.update(personnel, requete));*/
    }
}
