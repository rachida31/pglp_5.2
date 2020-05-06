package uvsq.M1.td1.Exo5_2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.sql.*;
//import org.apache.derby.iapi.sql.ResultSet;
//import org.apache.derby.shared.common.error.StandardException;
/**
 *implemantation de la classe DerbyConnexion 
 *pour creation de la base de donnee et les tables.
 * @author rachida ouchene.
 * 
 */
public class DerbyConnexion {
	/**
	 * chaine de connexion.
	 */
	public static String dburl="jdbc:derby:DBDerby;create=true" ;
   // private static final String DB__DRIVER ="org.apache.derby.jdbc.EmbeddedDriver";

    /**
	 * nom d'utilisateur pour ce connecter a la base de donnee DBDerby.
	 */
	private static final String user = "Rachida";
	/**
	 * Le mot de passe pour ce connecter a la base de donnee DBDerby.
	 */
	private static final String pwd = "Ouchene";
	  
	/**
	* Constructor DerbyConnexion qu'inisialise @see nomGroupe a une chaine vide
	* et @see personnel cree une new ArrayList.
	 *
	 * */
	
	
	public DerbyConnexion()
	{
		   Properties connectionProps = new Properties();
	        connectionProps.put("user", user);
	        connectionProps.put("password", pwd); ;
		
	}	
	
	public void  createtable()  
	{
			try {
					Connection conn =DriverManager.getConnection(dburl);
				      //creationde l'objet Statement pour envoyer des instructions .
					Statement state = conn.createStatement();
					 DatabaseMetaData databaseMetadata = conn.getMetaData();
					    ResultSet resultSet = databaseMetadata.getTables(null, null, "PERSONNEL", null);
						  //drop de la table
					    if (resultSet.next()) {
							state.addBatch("DROP TABLE Personnel");
					    }
					     resultSet = databaseMetadata.getTables(null, null, "GROUPE", null);

					    if (resultSet.next()) {
							state.addBatch("DROP TABLE Groupe");
					    }
					     resultSet = databaseMetadata.getTables(null, null, "APPROPRIER", null);

					    if (resultSet.next()) {
							state.addBatch("DROP TABLE Approprier");
					    }


					    	state.addBatch(
							"CREATE TABLE Personnel ("
						         + "nom VARCHAR(255) NOT NULL , "
						         + "prenom VARCHAR(255) NOT NULL , "
						         + "date DATE NOT NULL, "
								 + "fonction VARCHAR(255)NOT NULL, "
						        + "PRIMARY KEY (nom))");
					state.addBatch( "CREATE TABLE Groupe( "
					         + "NomGoup VARCHAR(255) NOT NULL , "
					         + "PRIMARY KEY (NomGoup))");  
					state.addBatch( "CREATE TABLE  Approprier( "
					         + "nomPerso VARCHAR(255) NOT NULL , "
					         + "NomGoupe varchar(255) NOT NULL , "
					         + "PRIMARY KEY (nomPerso,NomGoupe)) "
					         );
					 state.executeBatch();

		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
