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
    }

    @FXML
    private void btnIniciarSesion(ActionEvent event) {
        String username = tfUsuario.getText();
        String password = pfContrasena.getText();

        if (validarCampos(username, password)) {
            Connection loginCon = Conexion.getConexion("empleado").getConnection(); // o rol fijo si tienes uno
            if (loginCon == null) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de conexión", "No se pudo conectar con la base de datos.");
                return;
            }

            Usuario usuarioSesion = validarCredenciales(username, password, loginCon);
            if (usuarioSesion != null) {
                try {
                    loginCon.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String perfilConexion = (usuarioSesion.getTipo() == 1) ? "administrador" : "empleado";
                conexion = Conexion.getConexion(perfilConexion).getConnection();

                if (conexion == null) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo establecer conexión como " + perfilConexion);
                    return;
                }

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

    private Usuario validarCredenciales(String username, String password, Connection con) {
        try {
            Usuario usuarioSesion = InicioSesionDAO.verificarCredenciales(con, username, password);
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

            if (usuarioSesion.getTipo() == 1) {
                cargador = new FXMLLoader(getClass().getResource("/vista/FXMLPrincipalAdmin.fxml"));
            } else if (usuarioSesion.getTipo() == 2) {
                cargador = new FXMLLoader(getClass().getResource("/vista/FXMLPrincipalEmpleado.fxml"));
            } else {
                System.err.println("Tipo de usuario desconocido.");
                return;
            }

            Parent vista = cargador.load();

            Object controlador = cargador.getController();
            if (controlador instanceof FXMLPrincipalAdminController) {
                ((FXMLPrincipalAdminController) controlador).setUsuario(usuarioSesion);
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

