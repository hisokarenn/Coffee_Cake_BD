package br.edu.ufam;

import java.io.IOException;

import br.edu.ufam.model.UsuarioModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    private static Scene scene;
    public static UsuarioModel usuarioLogado;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("inicio"), 600, 400);
        stage.setScene(scene);
        stage.setTitle("CoffeeCake - Sistema de Gestão de Cafeterias");
        centerStage(600, 400);
        setMinWindowSize(600, 400);
        setResizable(false);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        setRoot(fxml, 600, 400);
    }

    public static void setRoot(String fxml, double width, double height) throws IOException {
        setResizable(false);
        scene.setRoot(loadFXML(fxml));

        Stage stage = (Stage) scene.getWindow();
        stage.setWidth(width);
        stage.setHeight(height);

        centerStage(width, height);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("controller/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void closeScene() {
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    private static void centerStage(double width, double height) {
        Stage stage = (Stage) scene.getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }

    public static void setMinWindowSize(double width, double height) {
        Stage stage = (Stage) scene.getWindow();
        stage.setMinWidth(width);
        stage.setMinHeight(height);
    }

    public static void setResizable(boolean resizable) {
        Stage stage = (Stage) scene.getWindow();
        stage.setResizable(resizable);
    }

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}
