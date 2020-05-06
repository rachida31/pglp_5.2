package uvsq.M1.td1.Exo5_2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBSCompositePersonnel  implements DAO<CompositePersonne>
{
	private static String dburl = DerbyConnexion.dburl;

	@Override
	public void create(CompositePersonne groupe) throws Exception {
		// TODO Auto-generated method stub
		try (Connection conn = DriverManager.getConnection(dburl)) {
			PreparedStatement prepare = conn.prepareStatement(
					"INSERT INTO Groupe (NomGoup)" +
					"VALUES (?)");
			prepare.setString(1, groupe.getNomGroupe());
			int result = prepare.executeUpdate();
			assert result == 1; // Nombre de tuples affectés = 1
			ArrayList<InterfacePersonne> listePersonnel = groupe.getPersonnel();
					
			for (InterfacePersonne personnel : listePersonnel) {
				System.out.println(groupe.getNomGroupe());
				prepare = conn.prepareStatement(
						"INSERT INTO Approprier "
						+ "VALUES (?, ?)");
				prepare.setString(1, groupe.getNomGroupe());
				prepare.setString(2, ((Personnel) personnel).getNom());
				prepare.addBatch();
			}
			prepare.executeBatch();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(CompositePersonne groupe, String requete) throws Exception {
		// TODO Auto-generated method stub
		try (Connection conn = DriverManager.getConnection(dburl)) {
			ArrayList<InterfacePersonne> personnel = groupe.getPersonnel();
				
			PreparedStatement prepare = conn.prepareStatement(
					"DELETE FROM Approprier "
							+ "WHERE NomGoupe = ? ");
			prepare.setString(1, groupe.getNomGroupe());
			for (InterfacePersonne p : personnel) {
				prepare = conn.prepareStatement(
						"INSERT INTO Approprier "
						+ "VALUES (?, ?)");
				prepare.setString(1, groupe.getNomGroupe());
				prepare.setString(2, ((Personnel) p).getNom());
				prepare.addBatch();
			}
			prepare.executeBatch();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Maj " + groupe);
	}

	@Override
	public void delete(CompositePersonne groupe) throws Exception {
		// TODO Auto-generated method stub
		try (Connection conn = DriverManager.getConnection(dburl)) {
		PreparedStatement prepare = conn.prepareStatement(
				"DELETE FROM Groupe "
				+ "WHERE NomGoup = ?");
		prepare.setString(1, groupe.getNomGroupe());
		int result = prepare.executeUpdate();
		assert result == 1;
		System.out.println("Suppression " + groupe);
		
		ArrayList<InterfacePersonne> listePersonnel = groupe.getPersonnel();
		
		for (InterfacePersonne personnel : listePersonnel) {
			System.out.println(groupe.getNomGroupe());
			 prepare = conn.prepareStatement(
						"DELETE FROM Approprier "
						+ "WHERE NomGoupe = ? "
					 	+ "and nomPerso = ? ");
			 prepare.setString(1, groupe.getNomGroupe());
			 prepare.setString(2, ((Personnel) personnel).getNom());
}

		int result1 = prepare.executeUpdate();
		assert result1 ==listePersonnel.size() ;
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
		
	}

	@Override
	public CompositePersonne read(String s) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		try (Connection conn = DriverManager.getConnection(dburl)) {
			System.out.println("Recherche " + s);
			PreparedStatement prepare = conn.prepareStatement(
					"SELECT * FROM Approprier WHERE NomGoupe = ?");
			prepare.setString(1, s);
			ArrayList<InterfacePersonne> listePersonnel =new ArrayList<InterfacePersonne>();

			ResultSet result = prepare.executeQuery();
			  System.out.println("voilaaaaaaaaaaa 000000000   "+result.getFetchSize());

			JDBCPersonnel personnel = new JDBCPersonnel();
			while (result.next()){
				  System.out.println("voilaaaaaaaaaaa 11111111");

				listePersonnel.add(personnel.read(result.getString("nomPerso")));
			}
			CompositePersonne groupe = new CompositePersonne(listePersonnel,s);
			return groupe;		
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
			}

}
