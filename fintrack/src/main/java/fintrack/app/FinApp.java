package fintrack.app;

import fintrack.dao.Conexao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class FinApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Conexao.criarTabelas();
        
        URL fxmlUrl = getClass().getResource("/views/main.fxml");
        if (fxmlUrl == null) {
            System.err.println("Não foi possível encontrar main.fxml");
            System.exit(1);
        }
        
        Parent root = FXMLLoader.load(fxmlUrl);
        Scene scene = new Scene(root, 900, 650);
        
        URL cssUrl = getClass().getResource("/styles/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        try {
            javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("/images/app-icon.png"));
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Não foi possível carregar o ícone da aplicação: " + e.getMessage());
        }

        primaryStage.setTitle("FinTrack - Controle Financeiro");
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
