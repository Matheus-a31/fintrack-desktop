package fintrack.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class MainController {

    @FXML private TabPane tabPane;
    @FXML private ListaController telaListaController;
    @FXML private FormController telaFormController;
    @FXML private RelatorioController telaRelatorioController;

    @FXML
    public void initialize() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null && newTab.getText() != null) {
                if (newTab.getText().contains("Transaç")) {
                    if (telaListaController != null) telaListaController.atualizarTabela();
                } else if (newTab.getText().contains("Relatór")) {
                    if (telaRelatorioController != null) telaRelatorioController.atualizarRelatorio();
                }
            }
        });
    }
}
