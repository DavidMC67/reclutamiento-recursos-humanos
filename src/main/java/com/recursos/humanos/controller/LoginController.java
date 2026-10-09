package main.java.com.recursos.humanos.controller;

import main.java.com.recursos.humanos.dto.request.LoginDTORequest;
import main.java.com.recursos.humanos.dto.response.LoginDTOResponse;
import main.java.com.recursos.humanos.exception.AuthException;
import main.java.com.recursos.humanos.exception.PersistenciaException;
import main.java.com.recursos.humanos.exception.ValidacionException;
import main.java.com.recursos.humanos.service.AuthService;
import main.java.com.recursos.humanos.util.Alertas;
import main.java.com.recursos.humanos.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasena;

    private final AuthService authService;
    private final SceneManager sceneManager;

    public LoginController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void iniciarSesion() {
        try {
            LoginDTOResponse usuario = authService.login(new LoginDTORequest(txtCorreo.getText(), txtContrasena.getText()));
            Alertas.informacion("Inicio de sesión", "Bienvenido " + usuario.getNombre() + ", ingresó como " + usuario.getNombreRol());
            sceneManager.mostrarInventario(usuario);
        } catch (ValidacionException e) {
            Alertas.advertencia("Datos incompletos", e.getMessage());
        } catch (AuthException e) {
            Alertas.error("Acceso denegado", e.getMessage());
        } catch (PersistenciaException e) {
            Alertas.error("Error de base de datos", e.getMessage());
        }
    }

    @FXML
    private void irRegistro() {
        sceneManager.mostrarRegistro();
    }
}
