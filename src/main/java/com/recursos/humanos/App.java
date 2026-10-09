package main.java.com.recursos.humanos;

import main.java.com.recursos.humanos.config.Injector;
import main.java.com.recursos.humanos.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = new SceneManager(stage);
        Injector injector = new Injector(sceneManager);
        sceneManager.setControllerFactory(injector::crearControlador);
        sceneManager.mostrarLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
