package fintrack.controller;

import fintrack.dao.TransacaoDAO;
import fintrack.utils.Formatador;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RelatorioController {
    @FXML private Label lblSaldo;
    @FXML private Label lblTotalReceitas;
    @FXML private Label lblTotalDespesas;
    @FXML private Label lblTotalTransacoes;

    private TransacaoDAO dao;

    @FXML
    public void initialize() {
        dao = new TransacaoDAO();
        atualizarRelatorio();
    }

    public void atualizarRelatorio() {
        double receitas = dao.calcularTotalReceitas();
        double despesas = dao.calcularTotalDespesas();
        double saldo = dao.calcularSaldoTotal();
        int totalTransacoes = dao.listarTodos().size();

        if (lblTotalReceitas != null) {
            lblTotalReceitas.setText(Formatador.formatarMoeda(receitas));
        }
        if (lblTotalDespesas != null) {
            lblTotalDespesas.setText(Formatador.formatarMoeda(despesas));
        }
        if (lblSaldo != null) {
            lblSaldo.setText(Formatador.formatarMoeda(saldo));
            if (saldo >= 0) {
                lblSaldo.setStyle("-fx-text-fill: #15803D; -fx-font-weight: bold;");
            } else {
                lblSaldo.setStyle("-fx-text-fill: #B91C1C; -fx-font-weight: bold;");
            }
        }
        if (lblTotalTransacoes != null) {
            lblTotalTransacoes.setText(String.valueOf(totalTransacoes));
        }
    }
}
