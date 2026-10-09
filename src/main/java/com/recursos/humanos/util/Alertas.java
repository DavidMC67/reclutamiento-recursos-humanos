package main.java.com.recursos.humanos.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alertas {

    private Alertas() {
    }

    public static void informacion(String titulo, String mensaje) {
        crear(Alert.AlertType.INFORMATION, titulo, mensaje).showAndWait();
    }

    public static void advertencia(String titulo, String mensaje) {
        crear(Alert.AlertType.WARNING, titulo, mensaje).showAndWait();
    }

    public static void error(String titulo, String mensaje) {
        crear(Alert.AlertType.ERROR, titulo, mensaje).showAndWait();
    }

    public static boolean confirmar(String titulo, String mensaje) {
        return crear(Alert.AlertType.CONFIRMATION, titulo, mensaje)
                .showAndWait()
                .filter(boton -> boton == ButtonType.OK)
                .isPresent();
    }

    private static Alert crear(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        return alerta;
    }
}
