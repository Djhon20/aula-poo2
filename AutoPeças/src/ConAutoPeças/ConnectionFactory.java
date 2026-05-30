package ConAutoPeças;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	/* * CONSTANTE URL: Define o caminho e o protocolo do banco de dados.
     * "jdbc:sqlite:" avisa ao Java que usaremos o driver do SQLite.
     * "banco_autopecas.db" é o nome do arquivo que guardará todas as tabelas.
     * Como não há um caminho absoluto (ex: C:/pasta/), o arquivo será criado 
     * diretamente na raiz do seu projeto do Eclipse.
     */
    private static final String URL = "jdbc:sqlite:banco_autopecas.db";
    
    /*
     * MÉTODO getConnection(): É o método público que qualquer outra classe 
     * (como seu UsuarioDAO ou CRUD) vai chamar quando precisar conversar com o banco.
     * Ele retorna um objeto do tipo 'Connection'.
     */
    public static Connection getConnection() {
        try {
        	/*
             * Class.forName(): Carrega dinamicamente a classe do Driver do SQLite 
             * para dentro da memória da Java Virtual Machine (JVM). Isso garante 
             * que o Java saiba ler a URL "jdbc:sqlite" que definimos acima.
             */
            Class.forName("org.sqlite.JDBC");
            /*
             * DriverManager.getConnection(): É a linha que efetivamente abre o arquivo 
             * do banco de dados. Se o arquivo não existir, o SQLite cria ele na hora.
             * Ele pega essa conexão aberta e a envia de volta para quem chamou o método.
             */
            return DriverManager.getConnection(URL);
            /*
             * Este catch roda se o Eclipse NÃO encontrar o arquivo '.jar' do SQLite 
             * que adicionamos no Classpath. Ele avisa o erro no console e evita que o sistema trave.
             */
        } catch (ClassNotFoundException e) {
            System.err.println("Driver do SQLite não encontrado!");
            e.printStackTrace();
            return null; // retorna null pq  a conexão falhou
            /*
             * Este catch roda se o driver foi encontrado, mas houve um erro de banco de dados
             * (ex: o arquivo do banco está bloqueado por outro programa, ou está corrompido).
             */
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco SQLite!");
            e.printStackTrace();
            return null; //  tbm retorna null pq não achou
        }
    }
}