package fintrack.controller;

import fintrack.dao.TransacaoDAO;
import fintrack.model.Transacao;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class FormController {
    @FXML private TextField txtDescricao;
    @FXML private TextArea txtNotas;
    @FXML private TextField txtValor;
    @FXML private DatePicker dpData;
    @FXML private CheckBox chkReceita;
    @FXML private Label lblMensagem;

    private TransacaoDAO dao;

    @FXML
    public void initialize() {
        dao = new TransacaoDAO();
    }

    @FXML
    public void salvarTransacao() {
        try {
            String descricao = txtDescricao.getText();
            String notas = txtNotas.getText();
            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
            boolean ehReceita = chkReceita.isSelected();
            LocalDate data = dpData.getValue();
            
            if (descricao == null || descricao.isEmpty() || data == null) {
                lblMensagem.setText("Erro: Preencha a descrição e a data.");
                lblMensagem.setStyle("-fx-text-fill: red;");
                return;
            }

            // Opcionalmente concatenando a nota na descrição
            String descricaoCompleta = notas.isEmpty() ? descricao : descricao + " | Notas: " + notas;

            Transacao t = new Transacao(descricaoCompleta, valor, ehReceita, data);
            dao.adicionar(t);
            
            lblMensagem.setText("Sucesso: Transação salva!");
            lblMensagem.setStyle("-fx-text-fill: green;");
            limparCampos();
        } catch (NumberFormatException e) {
            lblMensagem.setText("Erro: Valor inválido.");
            lblMensagem.setStyle("-fx-text-fill: red;");
        }
    }

    private void limparCampos() {
        txtDescricao.clear();
        txtNotas.clear();
        txtValor.clear();
        dpData.setValue(null);
        chkReceita.setSelected(false);
    }
}
