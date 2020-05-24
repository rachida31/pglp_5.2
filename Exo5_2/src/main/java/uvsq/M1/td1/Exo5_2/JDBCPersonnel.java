package uvsq.M1.td1.Exo5_2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import uvsq.M1.td1.Exo5_2.Personnel.PersonnelBuilder;
/**
 * la classe JDBCPersonnel qui implemente DAO.
 * @author rachida.
 * */
public class JDBCPersonnel implements DAO<Personnel> {
	/**
	 * chaine de connexion.
	 */
	private static String dburl = DerbyConnexion.dburl;

	/**
	 * Rajoute un personnel a la table Personnel de la base.
	 * @param personnel : l'objet Personnel a enregistrer.
	 * @throws Exception une exception si y a un problemme avec
	 * l'execution de la sql.
	 */
	@Override
	public final void create(final Personnel personnel) throws Exception {
		// TODO Auto-generated method stub
		try (Connection conn = DriverManager.getConnection(dburl)) {
			PreparedStatement prepare = conn.prepareStatement(
					"INSERT INTO Personnel (nom, prenom, "
					+ "date" + ",fonction)" + "VALUES "
							+ "( ?, ? , ? , ? )");
			prepare.setString(1, personnel.getNom());
			prepare.setString(2, personnel.getPrenom());
			prepare.setString(3, personnel.getDate().toString());
			prepare.setString(4, personnel.getFonc().toString());
			int result = prepare.executeUpdate();
			assert result == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Modifier les valeurs la fonctionnalité et date dans la base
	 * de Personnel.
	 * @param personnel : le personnel avec les nouvelles informations
	 * à modifier.
	 * @param nom  : le nom de pesrsonnel pour le quel on
	 * applique les modification.
	 * @throws Exception une exception si y a un problemme avec
	 * l'execution de la sql.
	 */
	@Override
	public final void update(final Personnel personnel, final String nom)
			throws Exception {
		// TODO Auto-generated method stub
		try (Connection conn = DriverManager.getConnection(dburl)) {
			PreparedStatement prepare = conn
					.prepareStatement("UPDATE Personnel "
							+ "SET"
							+ " fonction = ?, "
							+ "date"
							+ " = ? WHERE nom = ?");
			prepare.setString(1, personnel.getFonc().toString());
			prepare.setString(2, personnel.getDate().toString());
			prepare.setString(3, nom);
			int result = prepare.executeUpdate();
			assert result == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Suppression de personnel de la table Personnel de la base.
	 * @param personnel : le personnel a supprimer.
	 * @exception Exception une exception si y a un problemme avec
	 * l'execution de la sql.
	 */
	@Override
	public final void delete(final Personnel personnel) throws Exception {
		// TODO Auto-generated method stub
		try (Connection conn = DriverManager.getConnection(dburl)) {
			PreparedStatement prepare = conn.
					prepareStatement("DELETE "
					+ "FROM " + "personnel "
							+ "" + "WHERE nom = ?");
			prepare.setString(1, personnel.getNom());
			int result = prepare.executeUpdate();
			assert result == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Recherche un personnel dans la table Personnel de la base
	 * appartir de son nom.
	 * @param nom : le nom de personnel rechercher.
	 * @return personnel : le personnel recherecher.
	 * @throws InExistTupleException : une exception si le tuple
	 * rechercher n'existe pas .
	 * @throws FileNotFoundException .
	 * @throws ClassNotFoundException .
	 * @throws IOException .
	 */
	@Override
	public final Personnel read(final String nom) throws
	FileNotFoundException, ClassNotFoundException, IOException {
		Personnel personnel = null;
		try (Connection conn = DriverManager.getConnection(dburl)) {
			PreparedStatement prepare = conn.
					prepareStatement("SELECT * "
					+ "FROM " + "Personnel WHERE nom = ?");
			prepare.setString(1, nom);
			ResultSet result = prepare.executeQuery();
			if (result.next()) {
				personnel = new PersonnelBuilder(
						result.getString("nom"), result
						.getString("prenom")).
						date(LocalDate.parse(result.
						getString("date"))).fonc(result.
						getString("fonction")).build();

				result.close();

			} else {
				throw new InExistTupleException();
			}
		} catch (SQLException | InExistTupleException e) {
			e.printStackTrace();
		}
		return personnel;
	}

}
