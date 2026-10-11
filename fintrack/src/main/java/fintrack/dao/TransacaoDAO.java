package fintrack.dao;

import fintrack.model.Transacao;
import fintrack.repository.RepositorioGenerico;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO implements RepositorioGenerico<Transacao> {

    private Connection connection;

    public TransacaoDAO() {
        try {
            this.connection = Conexao.conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TransacaoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void adicionar(Transacao transacao) {
        String sql = "INSERT INTO transacoes(descricao, valor, tipo, date) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, transacao.getDescricao());
            pstmt.setDouble(2, transacao.getValor());
            pstmt.setString(3, transacao.isEhReceita() ? "Receita" : "Despesa");
            pstmt.setDate(4, Date.valueOf(transacao.getData()));
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transacao.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar transação: " + e.getMessage());
        }
    }

    @Override
    public void atualizar(Transacao transacao) {
        String sql = "UPDATE transacoes SET descricao = ?, valor = ?, tipo = ?, date = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, transacao.getDescricao());
            pstmt.setDouble(2, transacao.getValor());
            pstmt.setString(3, transacao.isEhReceita() ? "Receita" : "Despesa");
            pstmt.setDate(4, Date.valueOf(transacao.getData()));
            pstmt.setInt(5, transacao.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar transação: " + e.getMessage());
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM transacoes WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao remover transação: " + e.getMessage());
        }
    }

    @Override
    public List<Transacao> listarTodos() {
        String sql = "SELECT * FROM transacoes";
        List<Transacao> transacoes = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transacao t = new Transacao(
                        rs.getString("descricao"),
                        rs.getDouble("valor"),
                        rs.getString("tipo").equals("Receita"),
                        rs.getDate("date").toLocalDate()
                );
                t.setId(rs.getInt("id"));
                transacoes.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar transações: " + e.getMessage());
        }
        return transacoes;
    }

    @Override
    public void adicionarTodos(List<? extends Transacao> entidades) {
        for (Transacao t : entidades) {
            adicionar(t);
        }
    }

    public double calcularSaldoTotal() {
        double saldo = 0;
        List<Transacao> transacoes = listarTodos();
        for (Transacao t : transacoes) {
            if (t.isEhReceita()) {
                saldo += t.getValor();
            } else {
                saldo -= t.getValor();
            }
        }
        return saldo;
    }

    public double calcularTotalReceitas() {
        double total = 0;
        for (Transacao t : listarTodos()) {
            if (t.isEhReceita()) {
                total += t.getValor();
            }
        }
        return total;
    }

    public double calcularTotalDespesas() {
        double total = 0;
        for (Transacao t : listarTodos()) {
            if (!t.isEhReceita()) {
                total += t.getValor();
            }
        }
        return total;
    }
}
