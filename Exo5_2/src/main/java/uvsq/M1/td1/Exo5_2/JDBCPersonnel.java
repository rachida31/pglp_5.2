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
public class JDBCPersonnel implements DAO<Personnel> {

	private static String dburl = DerbyConnexion.dburl;

	@Override
	public void create(Personnel personnel) throws Exception {
		// TODO Auto-generated method stub
		try (Connection conn = DriverManager.getConnection(dburl)) {
				PreparedStatement prepare = conn.prepareStatement(
						"INSERT INTO Personnel (nom, prenom, date,fonction)" +
						"VALUES ( ?, ? , ? , ? )");
				prepare.setString(1, personnel.getNom());
				prepare.setString(2, personnel.getPrenom());
				prepare.setString(3, personnel.getDate().toString());
				prepare.setString(4, personnel.getFonc().toString());
				int result = prepare.executeUpdate();
				assert result == 1; 
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	

	@Override
	public void  update(Personnel personnel, String requete) throws Exception {
		// TODO Auto-generated method stub
		try (Connection conn = DriverManager.getConnection(dburl)) {
			PreparedStatement prepare = conn.prepareStatement(
					requete);
					
			prepare.setString(1, personnel.getFonc().toString());
			prepare.setString(2, personnel.getDate().toString());
			prepare.setString(3, personnel.getNom());
			int result = prepare.executeUpdate();
			assert result == 1;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Personnel personnel) throws Exception {
		// TODO Auto-generated method stub
		try (Connection conn = DriverManager.getConnection(dburl)) {
			PreparedStatement prepare = conn.prepareStatement(
					"DELETE FROM personnel "
					+ "WHERE nom = ?");
			prepare.setString(1, personnel.getNom());
			int result = prepare.executeUpdate();
			assert result == 1;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public Personnel read(String s) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		Personnel personnel = null;
		try (Connection conn = DriverManager.getConnection(dburl)) {
			PreparedStatement prepare = conn.prepareStatement(
					"SELECT * FROM Personnel WHERE nom = ?");
			prepare.setString(1, s);
			ResultSet result = prepare.executeQuery();
			if(result.next()) {
				personnel =  new PersonnelBuilder(result.getString("nom"),result.getString("prenom"))
						.date(LocalDate.parse(result.getString("date")))
						.fonc(result.getString("fonction"))
						.build();
				
				result.close();
				
			}
			else
			{
				throw new InExistTupleException();
			}
			}
		catch (SQLException | InExistTupleException e) {
			e.printStackTrace();
		}
		return personnel;	}

}
