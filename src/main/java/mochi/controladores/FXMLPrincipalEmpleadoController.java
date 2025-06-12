package mochi.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mochi.modelo.pojo.Usuario;

public class FXMLPrincipalEmpleadoController implements Initializable {

    private Usuario usuario;

    @FXML
    private Label lblNombreUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nada por hacer aquí por ahora
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null && usuario.getUsername() != null) {
            lblNombreUsuario.setText("Usuario: " + usuario.getUsername());
        }
    }

    @FXML
    private void btnCerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLIniciarSesion.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Inicio de Sesión");
            stage.setScene(new Scene(root));
            stage.show();

            // Cierra la ventana actual
            Stage actual = (Stage) lblNombreUsuario.getScene().getWindow();
            actual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnGestionClientes(ActionEvent event) {
        abrirNuevaVentana("/vista/FXMLGestionClientes.fxml", "Gestión de Clientes");
    }

    @FXML
    private void btnRealizarVentas(ActionEvent event) {
        abrirNuevaVentana("/vista/FXMLVenta.fxml", "Realizar Venta");
    }

    @FXML
    private void btnConsultaProductos(ActionEvent event) {
        abrirNuevaVentana("/vista/FXMLConsultaProductos.fxml", "Consulta de Productos y Promociones");
    }

    @FXML
    private void btnGestionProductos(ActionEvent event) {
        abrirNuevaVentana("/vista/FXMLGestionProductos.fxml", "Gestión de Productos");
    }

    /*@FXML
    private void btnGestionPromociones(ActionEvent event) {
        abrirNuevaVentana("mochi/vista/FXMLGestionPromociones.fxml", "Gestión de Promociones");
    }*/

    private void abrirNuevaVentana(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            // Si necesitas pasar el usuario al nuevo controlador:
            // Object controller = loader.getController();
            // if (controller instanceof FXMLTablaClientesController) {
            //     ((FXMLTablaClientesController) controller).setUsuario(usuario);
            // }

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se pudo abrir la ventana: " + rutaFXML);
        }
    }

    public void btnGestionPromociones(ActionEvent actionEvent) {
        abrirNuevaVentana("/vista/FXMLGestionPromociones.fxml", "Gestion de Promociones");
    }
}
