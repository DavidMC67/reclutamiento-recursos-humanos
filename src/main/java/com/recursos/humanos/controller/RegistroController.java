package main.java.com.recursos.humanos.controller;

import main.java.com.recursos.humanos.dto.request.RegistroDTORequest;
import main.java.com.recursos.humanos.dto.response.RolDTOResponse;
import main.java.com.recursos.humanos.exception.AuthException;
import main.java.com.recursos.humanos.exception.PersistenciaException;
import main.java.com.recursos.humanos.exception.ValidacionException;
import main.java.com.recursos.humanos.service.AuthService;
import main.java.com.recursos.humanos.util.Alertas;
import main.java.com.recursos.humanos.util.SceneManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController implements Initializable {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private ComboBox<RolDTOResponse> cmbRol;

    private final AuthService authService;
    private final SceneManager sceneManager;

    public RegistroController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cmbRol.setItems(FXCollections.observableArrayList(authService.listarRoles()));
            cmbRol.getSelectionModel().selectFirst();
        } catch (PersistenciaException e) {
            Alertas.error("Error de base de datos", e.getMessage());
        }
    }

    @FXML
    private void registrar() {
        RolDTOResponse rol = cmbRol.getValue();
        Integer idRol = rol == null ? null : rol.getIdRol();
        try {
            authService.registrar(new RegistroDTORequest(txtNombre.getText(), txtApellido.getText(),
                    txtCorreo.getText(), txtContrasena.getText(), idRol));
            Alertas.informacion("Registro", "Usuario registrado correctamente");
            sceneManager.mostrarLogin();
        } catch (ValidacionException e) {
            Alertas.advertencia("Datos inválidos", e.getMessage());
        } catch (AuthException e) {
            Alertas.error("No se pudo registrar", e.getMessage());
        } catch (PersistenciaException e) {
            Alertas.error("Error de base de datos", e.getMessage());
        }
    }

    @FXML
    private void volver() {
        sceneManager.mostrarLogin();
    }
}
