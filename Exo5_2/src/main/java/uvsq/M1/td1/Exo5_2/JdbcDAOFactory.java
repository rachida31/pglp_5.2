package uvsq.M1.td1.Exo5_2;

/**
 * implemantation de la classe DaoFactory pour factory
 * les deux objets.
 * JDBCPersonnel et JDBCCompositePersonne .
 * @author rachida ouchene.
 */
public class JdbcDAOFactory extends Factory {

	/**
	 * Retourne Un objet JDBCPersonnel .
	 * @return JDBCPersonnel.
	 */
	public final DAO<Personnel> getPersonnelDAO() {
		return new JDBCPersonnel();
	}

	/**
	 * Retourne Un objet JDBCCompositePersonnel.
	 * @return JDBCCompositePersonnel.
	 */
	public final DAO<CompositePersonne> getCompositePersonnel() {
		return new JDBCCompositePersonnel();

	}
}
