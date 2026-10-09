package main.java.com.recursos.humanos.dto.response;

public class LoginDTOResponse {

    private final String nombre;
    private final String apellido;
    private final String nombreRol;

    public LoginDTOResponse(String nombre, String apellido, String nombreRol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreRol = nombreRol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombreRol() {
        return nombreRol;
    }
}
