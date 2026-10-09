package main.java.com.recursos.humanos.dto.response;

public class AspiranteDTOResponse {

    private final int id;
    private final String nombre;
    private final String apellido;
    private final String correo;
    private final String telefono;
    private final String direccion;
    private final int edad;

    public AspiranteDTOResponse(int id, String nombre, String apellido, String correo, String telefono, String direccion, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.edad = edad;
    }

    public int getId() {
        return id;
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

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getEdad() {
        return edad;
    }
}
