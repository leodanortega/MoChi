package mochi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MoChi extends Application {

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent vista = FXMLLoader.load(getClass().getResource("/vista/FXMLIniciarSesion.fxml"));
            Scene escenaInicioSesion = new Scene(vista);

            primaryStage.setScene(escenaInicioSesion);
            primaryStage.setTitle("Inicio de Sesión");
            primaryStage.show();

        } catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Error: " + ioe.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}