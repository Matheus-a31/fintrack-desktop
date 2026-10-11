package fintrack;

import fintrack.dao.TransacaoDAO;
import fintrack.model.Transacao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransacaoDAOTest {

    private Connection connection;
    private TransacaoDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        // Conexão em memória para testes isolados
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        
        // Criar a tabela
        String sql = "CREATE TABLE transacoes ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " descricao VARCHAR(100) NOT NULL,"
                + " valor DECIMAL(10,2) NOT NULL,"
                + " tipo VARCHAR(10) NOT NULL,"
                + " date DATE NOT NULL"
                + ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
        
        // Inicializa o DAO com a conexão de teste
        dao = new TransacaoDAO(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    void testAdicionarEListar() {
        Transacao t = new Transacao("Salário", 5000.0, true, LocalDate.now());
        dao.adicionar(t);

        List<Transacao> transacoes = dao.listarTodos();
        assertEquals(1, transacoes.size());
        assertEquals("Salário", transacoes.get(0).getDescricao());
        assertEquals(5000.0, transacoes.get(0).getValor());
        assertTrue(transacoes.get(0).getId() > 0);
    }

    @Test
    void testRemover() {
        Transacao t = new Transacao("Conta de Luz", 150.0, false, LocalDate.now());
        dao.adicionar(t);
        
        List<Transacao> antes = dao.listarTodos();
        assertEquals(1, antes.size());
        
        int idInserido = antes.get(0).getId();
        dao.remover(idInserido);
        
        List<Transacao> depois = dao.listarTodos();
        assertEquals(0, depois.size());
    }

    @Test
    void testAtualizar() {
        Transacao t = new Transacao("Conta Antiga", 100.0, false, LocalDate.now());
        dao.adicionar(t);

        t.setDescricao("Conta Atualizada");
        t.setValor(120.0);
        t.setEhReceita(true);
        dao.atualizar(t);

        List<Transacao> lista = dao.listarTodos();
        assertEquals(1, lista.size());
        assertEquals("Conta Atualizada", lista.get(0).getDescricao());
        assertEquals(120.0, lista.get(0).getValor());
        assertTrue(lista.get(0).isEhReceita());
    }

    @Test
    void testCalcularSaldoTotal() {
        dao.adicionar(new Transacao("Salário", 5000.0, true, LocalDate.now()));
        dao.adicionar(new Transacao("Aluguel", 1500.0, false, LocalDate.now()));
        dao.adicionar(new Transacao("Mercado", 500.0, false, LocalDate.now()));
        
        double saldo = dao.calcularSaldoTotal();
        assertEquals(3000.0, saldo);
    }
}
