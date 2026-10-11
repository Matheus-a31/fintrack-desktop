package fintrack.controller;

import fintrack.dao.TransacaoDAO;
import fintrack.model.Transacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.Optional;

public class ListaController {
    @FXML private TableView<Transacao> tableView;
    @FXML private TableColumn<Transacao, LocalDate> colData;
    @FXML private TableColumn<Transacao, String> colDescricao;
    @FXML private TableColumn<Transacao, Double> colValor;
    @FXML private TableColumn<Transacao, String> colTipo;

    private TransacaoDAO dao;
    private ObservableList<Transacao> transacoes;

    @FXML
    public void initialize() {
        dao = new TransacaoDAO();
        transacoes = FXCollections.observableArrayList();
        
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colTipo.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().obterTipoTransacao()));

        tableView.setItems(transacoes);
        
        // Permite duplo clique na linha para abrir a edição
        tableView.setRowFactory(tv -> {
            TableRow<Transacao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    editarTransacao();
                }
            });
            return row;
        });

        atualizarTabela();
    }

    public void atualizarTabela() {
        transacoes.setAll(dao.listarTodos());
    }

    @FXML
    public void editarTransacao() {
        Transacao selecionada = tableView.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione uma transação na lista para editar.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Editar Transação");
        dialog.setHeaderText("Editar Transação #" + selecionada.getId());

        try {
            java.net.URL cssUrl = getClass().getResource("/styles/style.css");
            if (cssUrl != null) {
                dialog.getDialogPane().getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception ignored) {}

        TextField txtDescricao = new TextField(selecionada.getDescricao());
        TextField txtValor = new TextField(String.valueOf(selecionada.getValor()));
        DatePicker dpData = new DatePicker(selecionada.getData());
        CheckBox chkReceita = new CheckBox("Marcar como Receita (Entrada)");
        chkReceita.setSelected(selecionada.isEhReceita());

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new javafx.geometry.Insets(20, 20, 10, 20));

        grid.add(new Label("Descrição:"), 0, 0);
        grid.add(txtDescricao, 1, 0);
        grid.add(new Label("Valor (R$):"), 0, 1);
        grid.add(txtValor, 1, 1);
        grid.add(new Label("Data:"), 0, 2);
        grid.add(dpData, 1, 2);
        grid.add(new Label("Tipo:"), 0, 3);
        grid.add(chkReceita, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button btnOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        if (btnOk != null) {
            btnOk.setText("Salvar Alterações");
            btnOk.getStyleClass().add("btn-primary");
        }
        Button btnCancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        if (btnCancel != null) {
            btnCancel.setText("Cancelar");
        }

        java.util.Optional<ButtonType> resultado = dialog.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                String novaDesc = txtDescricao.getText() != null ? txtDescricao.getText().trim() : "";
                double novoValor = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
                LocalDate novaData = dpData.getValue();
                boolean novaReceita = chkReceita.isSelected();

                if (novaDesc.isEmpty() || novaData == null) {
                    Alert alertErro = new Alert(Alert.AlertType.ERROR);
                    alertErro.setTitle("Erro");
                    alertErro.setHeaderText(null);
                    alertErro.setContentText("A descrição e a data não podem ficar vazias.");
                    alertErro.show();
                    return;
                }

                selecionada.setDescricao(novaDesc);
                selecionada.setValor(novoValor);
                selecionada.setData(novaData);
                selecionada.setEhReceita(novaReceita);

                dao.atualizar(selecionada);
                atualizarTabela();
            } catch (NumberFormatException e) {
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Erro");
                alertErro.setHeaderText(null);
                alertErro.setContentText("Valor numérico inválido.");
                alertErro.show();
            }
        }
    }

    @FXML
    public void removerTransacao() {
        Transacao selecionada = tableView.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            dao.remover(selecionada.getId());
            atualizarTabela();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecione uma transação para remover.");
            alert.show();
        }
    }
}
