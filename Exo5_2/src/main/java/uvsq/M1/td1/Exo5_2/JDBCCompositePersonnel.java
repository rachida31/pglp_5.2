package uvsq.M1.td1.Exo5_2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *la classe  JDBCCompositePersonnel qui implemente DAO.
 *@author rachida.
 **/
public class JDBCCompositePersonnel implements DAO<CompositePersonne> {
	/**
	 * chaine de connexion.
	 */
	private static String dburl = DerbyConnexion.dburl;
	/**
	 * Rajoute un groupe a la table Groupe de la base.
	 * @param groupe : l'objet CompositePersonne a enregistrer.
	 * @throws Exception une exception si y a un problemme
	 * avec l'execution de la sql.
	 */
	@Override
	public final void create(final CompositePersonne groupe) throws
	Exception {
		try (Connection conn = DriverManager.getConnection(dburl)) {
			PreparedStatement prepare = conn.
					prepareStatement("INSERT INTO "
					+ "Groupe (NomGoup)" + "VALUES (?)");
			prepare.setString(1, groupe.getNomGroupe());
			int result = prepare.executeUpdate();
			assert result == 1; // Nombre de tuples affectés = 1
			ArrayList<InterfacePersonne> listePersonnel = groupe.
					getPersonnel();
			for (InterfacePersonne personnel : listePersonnel) {
				prepare = conn.prepareStatement("INSERT "
						+ "INTO Approprier "
						+ "(Name,nomPerso) "
						+ "VALUES (?, ?)");
				prepare.setString(1, groupe.getNomGroupe());
				prepare.setString(2, ((Personnel)
						personnel).getNom());
				prepare.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Modifier le groupe on ajoutant des personnels ou supprimer.
	 * @param groupe : le groupe avec les nouvelles informations à
	 * modifier.
	 * @param nom : le nom de groupe pour le quel on applique
	 * les modification.
	 * @throws Exception une exception si y a un problemme
	 * avec l'execution de la sql.
	 */
	@Override
	public final void update(final CompositePersonne groupe, final String
			nom) throws Exception {
		try (Connection conn = DriverManager.getConnection(dburl)) {
			ArrayList<InterfacePersonne> personnel = groupe.
					getPersonnel();
			PreparedStatement prepare = conn.
					prepareStatement("DELETE FROM "
					+ "Approprier " + "WHERE Name = ? ");
			prepare.setString(1, nom);
			int result1 = prepare.executeUpdate();
			assert result1 == 1;
			for (InterfacePersonne p : personnel) {
				prepare = conn.prepareStatement("INSERT "
						+ "INTO Approprier "
						+ "(Name,nomPerso) "
						+ "VALUES (?, ?)");
				prepare.setString(1, groupe.getNomGroupe());
				prepare.setString(2, ((Personnel) p).getNom());
				prepare.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Suppression de groupe de la table Groupe de la base.
	 * @param groupe : le groupe a supprimer.
	 * @throws Exception : une exception si y a un problemme
	 * avec l'execution de la sql.
	 */
	@Override
	public final void delete(final CompositePersonne
			groupe) throws Exception {
		try (Connection conn = DriverManager.getConnection(dburl)) {
			PreparedStatement prepare = conn.
					prepareStatement("DELETE FROM "
					+ "Groupe " + "WHERE NomGoup = ?");
			prepare.setString(1, groupe.getNomGroupe());
			int result = prepare.executeUpdate();
			assert result == 1;
			ArrayList<InterfacePersonne> listePersonnel = groupe.
					getPersonnel();
			for (InterfacePersonne personnel : listePersonnel) {
				prepare = conn.prepareStatement("DELETE "
						+ "FROM Approprier "
						+ "WHERE Name = ? "
						+ "and nomPerso = ? ");
				prepare.setString(1, groupe.getNomGroupe());
				prepare.setString(2, ((Personnel)
						personnel).getNom());
				int result1 = prepare.executeUpdate();
				assert result1 == 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Recherche un groupe dans la table Groupe de la base appartir
	 * de son nom.
	 * @param nom : le nom de groupe rechercher.
	 * @throws InExistTupleException : une exception si le tuple
	 * rechercher n'existe pas .
	 * @return personnel : le groupe recherecher.
	 * @throws FileNotFoundException .
	 * @throws ClassNotFoundException .
	 * @throws IOException .
	 */
	@Override
	public final CompositePersonne read(final String nom)
	throws FileNotFoundException, ClassNotFoundException, IOException {
		CompositePersonne groupe = null;
		try (Connection conn = DriverManager.getConnection(dburl)) {
			PreparedStatement prepare = conn
					.prepareStatement("SELECT * FROM "
					+ "Approprier WHERE Name = ?");
			prepare.setString(1, nom);
			ResultSet result = prepare.executeQuery();

			ArrayList<InterfacePersonne> listePersonnel = new
					ArrayList<InterfacePersonne>();
			JDBCPersonnel personnel = new JDBCPersonnel();
			while (result.next()) {
				if (personnel.read(result
					.getString("nomPerso")) != null) {
					listePersonnel.add(personnel
							.read(result.
							getString("nomPerso")));
				} else {
					System.out.println(
							"cette personne \" "
							+ result.getString(""
							+ "nomPerso") + " "
							+ "\" n\'existe "
							+ "pas dans "
							+ "la base!");
				}
			}
			groupe = new CompositePersonne(listePersonnel, nom);

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return groupe;
	}

}
