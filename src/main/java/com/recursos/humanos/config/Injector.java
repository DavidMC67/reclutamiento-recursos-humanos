package main.java.com.recursos.humanos.config;

import main.java.com.recursos.humanos.controller.InventarioController;
import main.java.com.recursos.humanos.controller.LoginController;
import main.java.com.recursos.humanos.controller.RegistroController;
import main.java.com.recursos.humanos.repository.AspiranteRepository;
import main.java.com.recursos.humanos.repository.RolRepository;
import main.java.com.recursos.humanos.repository.UsuarioRepository;
import main.java.com.recursos.humanos.service.AspiranteService;
import main.java.com.recursos.humanos.service.AuthService;
import main.java.com.recursos.humanos.util.SceneManager;

public class Injector {

    private final SceneManager sceneManager;
    private final AuthService authService;
    private final AspiranteService aspiranteService;

    public Injector(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.authService = new AuthService(new UsuarioRepository(), new RolRepository());
        this.aspiranteService = new AspiranteService(new AspiranteRepository());
    }

    public Object crearControlador(Class<?> tipo) {
        if (tipo == LoginController.class) {
            return new LoginController(authService, sceneManager);
        }
        if (tipo == RegistroController.class) {
            return new RegistroController(authService, sceneManager);
        }
        if (tipo == InventarioController.class) {
            return new InventarioController(aspiranteService, sceneManager);
        }
        throw new IllegalArgumentException("Controlador no registrado: " + tipo.getName());
    }
}
