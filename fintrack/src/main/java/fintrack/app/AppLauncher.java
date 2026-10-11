package fintrack.app;

/**
 * Launcher de entrada sem estender javafx.application.Application.
 * Necessário para permitir empacotamento em JAR executável / Uber-JAR
 * e evitar o erro 'JavaFX runtime components are missing'.
 */
public class AppLauncher {
    public static void main(String[] args) {
        FinApp.main(args);
    }
}
