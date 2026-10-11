package fintrack.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
    private static final String URL = "jdbc:sqlite:fintrack.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void criarTabelas() {
        String sql = "CREATE TABLE IF NOT EXISTS transacoes (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " descricao VARCHAR(100) NOT NULL,\n"
                + " valor DECIMAL(10,2) NOT NULL,\n"
                + " tipo VARCHAR(10) NOT NULL,\n"
                + " date DATE NOT NULL\n"
                + ");";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela transacoes: " + e.getMessage());
        }
    }
}
