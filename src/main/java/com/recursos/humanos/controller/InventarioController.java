package main.java.com.recursos.humanos.controller;

import main.java.com.recursos.humanos.dto.request.AspiranteDTORequest;
import main.java.com.recursos.humanos.dto.response.AspiranteDTOResponse;
import main.java.com.recursos.humanos.exception.AspiranteException;
import main.java.com.recursos.humanos.exception.PersistenciaException;
import main.java.com.recursos.humanos.exception.ValidacionException;
import main.java.com.recursos.humanos.service.AspiranteService;
import main.java.com.recursos.humanos.util.Alertas;
import main.java.com.recursos.humanos.util.SceneManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventarioController implements Initializable {

    @FXML
    private TableView<AspiranteDTOResponse> tblAspirantes;

    @FXML
    private TableColumn<AspiranteDTOResponse, Integer> colId;

    @FXML
    private TableColumn<AspiranteDTOResponse, String> colNombre;

    @FXML
    private TableColumn<AspiranteDTOResponse, String> colApellido;

    @FXML
    private TableColumn<AspiranteDTOResponse, String> colCorreo;

    @FXML
    private TableColumn<AspiranteDTOResponse, String> colTelefono;

    @FXML
    private TableColumn<AspiranteDTOResponse, String> colDireccion;

    @FXML
    private TableColumn<AspiranteDTOResponse, Integer> colEdad;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEdad;

    @FXML
    private Label lblUsuario;

    private final AspiranteService aspiranteService;
    private final SceneManager sceneManager;
    private final ObservableList<AspiranteDTOResponse> aspirantes = FXCollections.observableArrayList();

    public InventarioController(AspiranteService aspiranteService, SceneManager sceneManager) {
        this.aspiranteService = aspiranteService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        tblAspirantes.setItems(aspirantes);
        tblAspirantes.getSelectionModel().selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> mostrarEnFormulario(seleccionado));
        lblUsuario.setText(descripcionUsuario());
        try {
            refrescarTabla();
        } catch (PersistenciaException e) {
            Alertas.error("Error de base de datos", e.getMessage());
        }
    }

    @FXML
    private void agregar() {
        ejecutar(() -> aspiranteService.agregar(leerFormulario()), "Aspirante agregado correctamente");
    }

    @FXML
    private void actualizar() {
        AspiranteDTOResponse seleccionado = tblAspirantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alertas.advertencia("Sin selección", "Seleccione un aspirante de la tabla");
            return;
        }
        ejecutar(() -> aspiranteService.actualizar(seleccionado.getId(), leerFormulario()), "Aspirante actualizado correctamente");
    }

    @FXML
    private void eliminar() {
        AspiranteDTOResponse seleccionado = tblAspirantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alertas.advertencia("Sin selección", "Seleccione un aspirante de la tabla");
            return;
        }
        if (Alertas.confirmar("Eliminar aspirante", "¿Desea eliminar a " + seleccionado.getNombre() + " " + seleccionado.getApellido() + "?")) {
            ejecutar(() -> aspiranteService.eliminar(seleccionado.getId()), "Aspirante eliminado correctamente");
        }
    }

    @FXML
    private void limpiar() {
        limpiarFormulario();
    }

    @FXML
    private void cerrarSesion() {
        sceneManager.mostrarLogin();
    }

    private void ejecutar(Runnable accion, String mensajeExito) {
        try {
            accion.run();
            refrescarTabla();
            limpiarFormulario();
            Alertas.informacion("Aspirantes", mensajeExito);
        } catch (ValidacionException e) {
            Alertas.advertencia("Datos inválidos", e.getMessage());
        } catch (AspiranteException e) {
            Alertas.error("Operación no realizada", e.getMessage());
        } catch (PersistenciaException e) {
            Alertas.error("Error de base de datos", e.getMessage());
        }
    }

    private void refrescarTabla() {
        aspirantes.setAll(aspiranteService.listar());
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
    }

    private AspiranteDTORequest leerFormulario() {
        return new AspiranteDTORequest(txtNombre.getText(), txtApellido.getText(), txtCorreo.getText(),
                txtTelefono.getText(), txtDireccion.getText(), txtEdad.getText());
    }

    private void mostrarEnFormulario(AspiranteDTOResponse aspirante) {
        if (aspirante == null) {
            return;
        }
        txtNombre.setText(aspirante.getNombre());
        txtApellido.setText(aspirante.getApellido());
        txtCorreo.setText(aspirante.getCorreo());
        txtTelefono.setText(aspirante.getTelefono());
        txtDireccion.setText(aspirante.getDireccion());
        txtEdad.setText(String.valueOf(aspirante.getEdad()));
    }

    private void limpiarFormulario() {
        tblAspirantes.getSelectionModel().clearSelection();
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtEdad.clear();
    }

    private String descripcionUsuario() {
        if (sceneManager.getUsuarioActual() == null) {
            return "";
        }
        return sceneManager.getUsuarioActual().getNombre() + " " + sceneManager.getUsuarioActual().getApellido()
                + " - " + sceneManager.getUsuarioActual().getNombreRol();
    }
}
