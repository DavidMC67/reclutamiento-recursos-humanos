package main.java.com.recursos.humanos.dto.response;

public class RolDTOResponse {

    private final int idRol;
    private final String nombreRol;

    public RolDTOResponse(int idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    public int getIdRol() {
        return idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    @Override
    public String toString() {
        return nombreRol;
    }
}
