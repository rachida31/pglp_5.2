package uvsq.M1.td1.Exo5_2;
/**
 *la classe  InExistTupleException qui se léve si le tuple n'existe pas.
 *@author rachida.
 **/
public class InExistTupleException extends Exception {
/**.
* contrôleur de la version.
*/
private static final long serialVersionUID = 1L;
/**
*le constrecteur.
**/
public InExistTupleException() {
super("le tupe n\\'exite pas !");
}
}
