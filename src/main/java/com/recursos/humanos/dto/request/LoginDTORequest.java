package main.java.com.recursos.humanos.dto.request;

public class LoginDTORequest {

    private final String correo;
    private final String contrasena;

    public LoginDTORequest(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }
}
