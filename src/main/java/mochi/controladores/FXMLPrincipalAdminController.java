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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mochi.modelo.pojo.Usuario;

public class FXMLPrincipalAdminController implements Initializable {

    private Usuario usuario;

    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnGestionProveedores;
    @FXML
    private Button btnGestionPedidos;
    @FXML
    private Button btnGestionPromociones;
    @FXML
    private Button btnRegistroCompras;
    @FXML
    private Button btnGestionPersonal;
    @FXML
    private Button btnVerReportes;
     @FXML
    private Button btnGestionVentas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nada que hacer por ahora
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

            // Cerrar la ventana actual
            Stage actual = (Stage) lblNombreUsuario.getScene().getWindow();
            actual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnGestionProveedores(ActionEvent event) {
        abrirNuevaVentana("/vista/FXMLGestionProveedores.fxml", "Gestión de Proveedores");
    }

    @FXML
    private void btnGestionPedidos(ActionEvent event) {
        abrirNuevaVentana("/vista/FXMLGestionPedidos.fxml", "Gestión de Pedidos");
    }

    @FXML
    private void btnGestionPromociones(ActionEvent event) {
        abrirNuevaVentana("/vista/FXMLGestionPromociones.fxml", "Gestión de Promociones");
    }

    @FXML
    private void btnRegistroCompras(ActionEvent event) {
        abrirNuevaVentana("/vista/FXMLGestionCompras.fxml", "Registro de Compras");
    }

    @FXML
    private void btnGestionPersonal(ActionEvent event) {
        abrirNuevaVentana("/vista/FXMLGestionUsuarios.fxml", "Gestión de Personal");
    }

    @FXML
    private void btnVerReportes(ActionEvent event) {
        abrirNuevaVentana("/mochi/vista/FXMLReportes.fxml", "Visualización de Reportes");
    }

    private void abrirNuevaVentana(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se pudo abrir la ventana: " + rutaFXML);
        }
    }

    @FXML
    private void btnGestionVentas(ActionEvent event) {
        abrirNuevaVentana("/vista/FXMLGestionVentas.fxml", "Visualización de Reportes");
    }
}
