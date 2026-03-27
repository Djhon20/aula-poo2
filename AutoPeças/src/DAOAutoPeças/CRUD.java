package DAOAutoPeças;

import ConAutoPeças.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CRUD {

	
	//metodo que recebe os dados do crud
	public void salvar(String peça,String serial,String carro ) {
		String sql = "INSERT INTO pecas(peça, serial, carro) VALUES(?,?,?)";
		
		
		//metodo try para tratamento
		try (Connection conn = ConnectionFactory.getConexao();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			//preenche os ? na ordem exata
			stmt.setString(1, peça);
			stmt.setString(2, serial);
			stmt.setString(3, carro);
			
			stmt.execute();
			
		}catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar no banco: " + e.getMessage());
        }
	}
	
}
