package uvsq.M1.td1.Exo5_2;

/**
 * implemantation de la classe abstract Factory.
 * @author rachida ouchene.
 */
public abstract class Factory {
	/**
	 * Retourne Un objet DAO<Personnel>.
	 * @return un Personnel.
	 */
	public abstract DAO<Personnel> getPersonnelDAO();
	/**
	 * Retourne Un objet DAO<CompositePersonne>.
	 * @return un groupe.
	 */
	public abstract DAO<CompositePersonne> getCompositePersonnel();

}
