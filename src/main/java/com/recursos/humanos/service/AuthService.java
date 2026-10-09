package main.java.com.recursos.humanos.service;

import main.java.com.recursos.humanos.dto.request.LoginDTORequest;
import main.java.com.recursos.humanos.dto.request.RegistroDTORequest;
import main.java.com.recursos.humanos.dto.response.LoginDTOResponse;
import main.java.com.recursos.humanos.dto.response.RolDTOResponse;
import main.java.com.recursos.humanos.exception.AuthException;
import main.java.com.recursos.humanos.exception.ValidacionException;
import main.java.com.recursos.humanos.model.Rol;
import main.java.com.recursos.humanos.model.Usuario;
import main.java.com.recursos.humanos.repository.RolRepository;
import main.java.com.recursos.humanos.repository.UsuarioRepository;

import main.java.com.recursos.humanos.util.Validador;
import java.util.List;
import java.util.stream.Collectors;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private static final int LONGITUD_MINIMA_CONTRASENA = 6;
    private static final String CREDENCIALES_INCORRECTAS = "Correo o contraseña incorrectos";

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public AuthService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public List<RolDTOResponse> listarRoles() {
        return rolRepository.listar().stream()
                .map(rol -> new RolDTOResponse(rol.getIdRol(), rol.getNombreRol()))
                .collect(Collectors.toList());
    }

    public void registrar(RegistroDTORequest request) {
        validarRegistro(request);
        String correo = request.getCorreo().trim().toLowerCase();
        if (usuarioRepository.existeCorreo(correo)) {
            throw new AuthException("El correo ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getNombre().trim());
        usuario.setApellidoUsuario(request.getApellido().trim());
        usuario.setCorreoUsuario(correo);
        usuario.setContrasenaUsuario(BCrypt.hashpw(request.getContrasena(), BCrypt.gensalt()));
        usuario.setRol(new Rol(request.getIdRol(), null));
        usuarioRepository.guardar(usuario);
    }

    public LoginDTOResponse login(LoginDTORequest request) {
        if (Validador.esVacio(request.getCorreo()) || Validador.esVacio(request.getContrasena())) {
            throw new ValidacionException("Complete todos los campos");
        }
        Usuario usuario = usuarioRepository.buscarPorCorreo(request.getCorreo().trim().toLowerCase())
                .orElseThrow(() -> new AuthException(CREDENCIALES_INCORRECTAS));
        if (!BCrypt.checkpw(request.getContrasena(), usuario.getContrasenaUsuario())) {
            throw new AuthException(CREDENCIALES_INCORRECTAS);
        }
        return new LoginDTOResponse(usuario.getNombreUsuario(), usuario.getApellidoUsuario(), usuario.getRol().getNombreRol());
    }

    private void validarRegistro(RegistroDTORequest request) {
        if (Validador.esVacio(request.getNombre()) || Validador.esVacio(request.getApellido())
                || Validador.esVacio(request.getCorreo()) || Validador.esVacio(request.getContrasena())) {
            throw new ValidacionException("Complete todos los campos");
        }
        if (request.getIdRol() == null) {
            throw new ValidacionException("Seleccione un rol");
        }
        if (!Validador.esCorreoValido(request.getCorreo())) {
            throw new ValidacionException("El correo no es válido");
        }
        if (request.getContrasena().length() < LONGITUD_MINIMA_CONTRASENA) {
            throw new ValidacionException("La contraseña debe tener al menos " + LONGITUD_MINIMA_CONTRASENA + " caracteres");
        }
    }
}
