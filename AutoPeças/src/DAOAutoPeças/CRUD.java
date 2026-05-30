package DAOAutoPeças;

import ConAutoPeças.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CRUD {

	
	//metodo que recebe os dados do crud
	public void salvar(String peca,String sn,String car ) {
		String sql = "INSERT INTO pecas(peça, serial, carro) VALUES(?,?,?)";
		
		
		//metodo try para tratamento
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			//preenche os ? na ordem exata
			stmt.setString(1, peca);
			stmt.setString(2, sn);
			stmt.setString(3, car);
			
			stmt.execute();
			
			System.out.println("Salvo com sucesso");
		}catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar no banco: " + e.getMessage());
        }
	}
	
}
