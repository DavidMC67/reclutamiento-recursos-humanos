package main.java.com.recursos.humanos.model;

public class Aspirante {

    private int idAspirante;
    private String nombreAspirante;
    private String apellidoAspirante;
    private String correoAspirante;
    private String telefonoAspirante;
    private String direccionAspirante;
    private int edadAspirante;

    public Aspirante() {
    }

    public Aspirante(int idAspirante, String nombreAspirante, String apellidoAspirante, String correoAspirante,
            String telefonoAspirante, String direccionAspirante, int edadAspirante) {
        this.idAspirante = idAspirante;
        this.nombreAspirante = nombreAspirante;
        this.apellidoAspirante = apellidoAspirante;
        this.correoAspirante = correoAspirante;
        this.telefonoAspirante = telefonoAspirante;
        this.direccionAspirante = direccionAspirante;
        this.edadAspirante = edadAspirante;
    }

    public int getIdAspirante() {
        return idAspirante;
    }

    public void setIdAspirante(int idAspirante) {
        this.idAspirante = idAspirante;
    }

    public String getNombreAspirante() {
        return nombreAspirante;
    }

    public void setNombreAspirante(String nombreAspirante) {
        this.nombreAspirante = nombreAspirante;
    }

    public String getApellidoAspirante() {
        return apellidoAspirante;
    }

    public void setApellidoAspirante(String apellidoAspirante) {
        this.apellidoAspirante = apellidoAspirante;
    }

    public String getCorreoAspirante() {
        return correoAspirante;
    }

    public void setCorreoAspirante(String correoAspirante) {
        this.correoAspirante = correoAspirante;
    }

    public String getTelefonoAspirante() {
        return telefonoAspirante;
    }

    public void setTelefonoAspirante(String telefonoAspirante) {
        this.telefonoAspirante = telefonoAspirante;
    }

    public String getDireccionAspirante() {
        return direccionAspirante;
    }

    public void setDireccionAspirante(String direccionAspirante) {
        this.direccionAspirante = direccionAspirante;
    }

    public int getEdadAspirante() {
        return edadAspirante;
    }

    public void setEdadAspirante(int edadAspirante) {
        this.edadAspirante = edadAspirante;
    }
}
