package main.java.com.recursos.humanos.service;

import main.java.com.recursos.humanos.dto.request.AspiranteDTORequest;
import main.java.com.recursos.humanos.dto.response.AspiranteDTOResponse;
import main.java.com.recursos.humanos.exception.AspiranteException;
import main.java.com.recursos.humanos.exception.ValidacionException;
import main.java.com.recursos.humanos.model.Aspirante;
import main.java.com.recursos.humanos.repository.AspiranteRepository;
import main.java.com.recursos.humanos.util.Validador;
import java.util.List;
import java.util.stream.Collectors;

public class AspiranteService {

    private static final int EDAD_MINIMA = 16;
    private static final int EDAD_MAXIMA = 99;
    private static final String PATRON_TELEFONO = "\\d{8,15}";
    private static final int SIN_ID = 0;

    private final AspiranteRepository aspiranteRepository;

    public AspiranteService(AspiranteRepository aspiranteRepository) {
        this.aspiranteRepository = aspiranteRepository;
    }

    public List<AspiranteDTOResponse> listar() {
        return aspiranteRepository.listar().stream()
                .map(this::aDto)
                .collect(Collectors.toList());
    }

    public void agregar(AspiranteDTORequest request) {
        Aspirante aspirante = construir(SIN_ID, request);
        verificarCorreoDisponible(aspirante);
        aspiranteRepository.guardar(aspirante);
    }

    public void actualizar(int idAspirante, AspiranteDTORequest request) {
        Aspirante aspirante = construir(idAspirante, request);
        verificarCorreoDisponible(aspirante);
        if (!aspiranteRepository.actualizar(aspirante)) {
            throw new AspiranteException("No se encontró el aspirante que desea actualizar");
        }
    }

    public void eliminar(int idAspirante) {
        if (!aspiranteRepository.eliminar(idAspirante)) {
            throw new AspiranteException("No se encontró el aspirante que desea eliminar");
        }
    }

    private void verificarCorreoDisponible(Aspirante aspirante) {
        if (aspiranteRepository.existeCorreo(aspirante.getCorreoAspirante(), aspirante.getIdAspirante())) {
            throw new AspiranteException("Ya existe un aspirante con ese correo");
        }
    }

    private Aspirante construir(int idAspirante, AspiranteDTORequest request) {
        if (Validador.esVacio(request.getNombre()) || Validador.esVacio(request.getApellido())
                || Validador.esVacio(request.getCorreo()) || Validador.esVacio(request.getTelefono())
                || Validador.esVacio(request.getDireccion()) || Validador.esVacio(request.getEdad())) {
            throw new ValidacionException("Complete todos los campos");
        }
        if (!Validador.esCorreoValido(request.getCorreo())) {
            throw new ValidacionException("El correo no es válido");
        }
        if (!request.getTelefono().trim().matches(PATRON_TELEFONO)) {
            throw new ValidacionException("El teléfono debe tener entre 8 y 15 dígitos");
        }
        int edad = convertirEdad(request.getEdad());
        return new Aspirante(idAspirante, request.getNombre().trim(), request.getApellido().trim(),
                request.getCorreo().trim().toLowerCase(), request.getTelefono().trim(),
                request.getDireccion().trim(), edad);
    }

    private int convertirEdad(String texto) {
        int edad;
        try {
            edad = Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException("La edad debe ser un número entero");
        }
        if (edad < EDAD_MINIMA || edad > EDAD_MAXIMA) {
            throw new ValidacionException("La edad debe estar entre " + EDAD_MINIMA + " y " + EDAD_MAXIMA);
        }
        return edad;
    }

    private AspiranteDTOResponse aDto(Aspirante aspirante) {
        return new AspiranteDTOResponse(aspirante.getIdAspirante(), aspirante.getNombreAspirante(),
                aspirante.getApellidoAspirante(), aspirante.getCorreoAspirante(), aspirante.getTelefonoAspirante(),
                aspirante.getDireccionAspirante(), aspirante.getEdadAspirante());
    }
}
