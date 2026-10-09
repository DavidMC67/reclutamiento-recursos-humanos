package main.java.com.recursos.humanos.dto.request;

public class RegistroDTORequest {

    private final String nombre;
    private final String apellido;
    private final String correo;
    private final String contrasena;
    private final Integer idRol;

    public RegistroDTORequest(String nombre, String apellido, String correo, String contrasena, Integer idRol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Integer getIdRol() {
        return idRol;
    }
}
