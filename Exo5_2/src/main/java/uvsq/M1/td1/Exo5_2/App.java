package uvsq.M1.td1.Exo5_2;

import org.apache.derby.shared.common.error.StandardException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws StandardException
    {
        System.out.println( "Hello World!" );
       int t[]; 
        DerbyConnexion con = new  DerbyConnexion();
      t= con.createtable();
      System.out.println("ooooooooooooooooooohhhhhhhhhhhhh  "+t);
    }
}
