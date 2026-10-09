package main.java.com.recursos.humanos.util;

import main.java.com.recursos.humanos.dto.response.LoginDTOResponse;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SceneManager {

    private static final String RUTA_VISTAS = "/main/resources/view/";

    private final Stage stage;
    private Callback<Class<?>, Object> controllerFactory;
    private LoginDTOResponse usuarioActual;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void setControllerFactory(Callback<Class<?>, Object> controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public LoginDTOResponse getUsuarioActual() {
        return usuarioActual;
    }

    public void mostrarLogin() {
        usuarioActual = null;
        cargar("login-view.fxml", "Recursos Humanos - Iniciar sesión");
    }

    public void mostrarRegistro() {
        cargar("registro-view.fxml", "Recursos Humanos - Registro");
    }

    public void mostrarInventario(LoginDTOResponse usuario) {
        usuarioActual = usuario;
        cargar("inventario-view.fxml", "Recursos Humanos - Inventario de aspirantes");
    }

    private void cargar(String archivo, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(RUTA_VISTAS + archivo));
            loader.setControllerFactory(controllerFactory);
            Parent raiz = loader.load();
            stage.setScene(new Scene(raiz));
            stage.setTitle(titulo);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            Alertas.error("Error de navegación", "No se pudo cargar la vista " + archivo);
        }
    }
}
