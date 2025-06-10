package mochi.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mochi.dao.InicioSesionDAO;
import mochi.modelo.pojo.Usuario;
import mochi.conexionbd.Conexion;
import mochi.util.Utilidad;

public class FXMLIniciarSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfContrasena;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;
    private Usuario usuario;
    private Connection conexion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = Conexion.getConexion().getConnection();

        if (conexion == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error de conexión",
                    "No se pudo establecer conexión con la base de datos.");
            System.err.println("️ ERROR: conexión es null. Verifica configuración y driver.");
        } else {
            System.out.println(" Conexión a la base de datos establecida correctamente.");
        }
    }

    @FXML
    private void btnIniciarSesion(ActionEvent event) {
        String username = tfUsuario.getText();
        String password = pfContrasena.getText();

        if (validarCampos(username, password)) {
            Usuario usuarioSesion = validarCredenciales(username, password);
            if (usuarioSesion != null) {
                irPantallaPrincipal(usuarioSesion);
            }
        }
    }

    private boolean validarCampos(String username, String password) {
        lbErrorPassword.setText("");
        lbErrorUsuario.setText("");
        boolean camposValidos = true;

        if (username.isEmpty()) {
            lbErrorUsuario.setText("Usuario requerido");
            camposValidos = false;
        }
        if (password.isEmpty()) {
            lbErrorPassword.setText("Contraseña requerida");
            camposValidos = false;
        }

        return camposValidos;
    }

    private Usuario validarCredenciales(String username, String password) {
        try {
            Usuario usuarioSesion = InicioSesionDAO.verificarCredenciales(conexion, username, password);
            if (usuarioSesion != null) {
                Utilidad.mostrarAlertaSimple(
                        Alert.AlertType.INFORMATION,
                        "Credenciales correctas",
                        String.format("Bienvenido(a) %s", usuarioSesion.toString()));
                return usuarioSesion;
            } else {
                Utilidad.mostrarAlertaSimple(
                        Alert.AlertType.WARNING,
                        "Credenciales incorrectas",
                        "El usuario y/o contraseña no coinciden. Inténtelo de nuevo.");
                return null;
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.ERROR,
                    "Problema de conexión",
                    ex.getMessage());
            return null;
        }
    }


    private void irPantallaPrincipal(Usuario usuarioSesion) {
        try {
            Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader cargador;

            // Determina qué vista cargar según el tipo de usuario
            if (usuarioSesion.getTipo() == 1) { // Administrador
                cargador = new FXMLLoader(getClass().getResource("/vista/FXMLPrincipal.fxml"));
            } else if (usuarioSesion.getTipo() == 2) { // Empleado
                cargador = new FXMLLoader(getClass().getResource("/vista/FXMLPrincipalEmpleado.fxml"));
            } else {
                System.err.println("Tipo de usuario desconocido.");
                return;
            }

            Parent vista = cargador.load();

            // Asigna el usuario al controlador correspondiente
            Object controlador = cargador.getController();
            if (controlador instanceof FXMLPrincipalController) {
                ((FXMLPrincipalController) controlador).setUsuario(usuarioSesion);
            } else if (controlador instanceof FXMLPrincipalEmpleadoController) {
                ((FXMLPrincipalEmpleadoController) controlador).setUsuario(usuarioSesion);
            }

            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Pantalla Principal");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error al cargar la pantalla principal.");
        }
    }


}
