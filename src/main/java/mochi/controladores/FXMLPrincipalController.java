package mochi.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mochi.modelo.pojo.Usuario;

public class FXMLPrincipalController implements Initializable {

    private Usuario usuario;


    @FXML
    private Button btnVerCatalogo;

    @FXML
    private Button btnVerClientes;

    @FXML
    private Button btnVerCatalogoUsuarios;

    @FXML
    private Button btnVerCatalogoVentas;

    @FXML
    private Button btnVerCatalogoPromociones;

    @FXML
    private Button btnVerCatalogoProovedores;

    @FXML
    private Button btnHacerVenta;

    private AnchorPane panelContenido;
    @FXML
    private VBox menuLateral;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Puedes cargar una vista por defecto si quieres
        // cargarVistaEnPanel("/mochi/vista/FXMLHacerVenta.fxml");
    }

    @FXML
    private void btnCerrarSesion(ActionEvent event) {
        System.out.println("Cerrando sesión...");
        // Aquí lógica para cerrar sesión o volver a login
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;

        if (usuario.getTipo() == 2) { // Empleado
            // Ocultar botones que solo debe ver admin
            btnVerCatalogoUsuarios.setVisible(false);
            btnVerCatalogoVentas.setVisible(false);
            btnVerCatalogoPromociones.setVisible(false);
            btnVerCatalogoProovedores.setVisible(false);
        } else if (usuario.getTipo() == 1) { // Admin
            // Mostrar todos los botones
            btnVerCatalogoUsuarios.setVisible(true);
            btnVerCatalogoVentas.setVisible(true);
            btnVerCatalogoPromociones.setVisible(true);
            btnVerCatalogoProovedores.setVisible(true);
        }
    }

    private void cargarVistaEnPanel(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent vista = loader.load();
            panelContenido.getChildren().setAll(vista);

            AnchorPane.setTopAnchor(vista, 0.0);
            AnchorPane.setBottomAnchor(vista, 0.0);
            AnchorPane.setLeftAnchor(vista, 0.0);
            AnchorPane.setRightAnchor(vista, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se pudo cargar la vista: " + rutaFXML);
        }
    }

    @FXML
    private void btnVerCatalogo(ActionEvent event) {
        cargarVistaEnPanel("/mochi/vista/FXMLCatalogoProductos.fxml");
    }

    @FXML
    private void btnVerClientes(ActionEvent event) {
        cargarVistaEnPanel("/mochi/vista/FXMLCatalogoClientes.fxml");
    }

    @FXML
    private void btnVerCatalogoUsuarios(ActionEvent event) {
        cargarVistaEnPanel("/mochi/vista/FXMLCatalogoUsuarios.fxml");
    }

    @FXML
    private void btnVerCatalogoVentas(ActionEvent event) {
        cargarVistaEnPanel("/mochi/vista/FXMLCatalogoVentas.fxml");
    }

    @FXML
    private void btnVerCatalogoPromociones(ActionEvent event) {
        cargarVistaEnPanel("/mochi/vista/FXMLCatalogoPromociones.fxml");
    }

    @FXML
    private void btnVerCatalogoProovedores(ActionEvent event) {
        cargarVistaEnPanel("/mochi/vista/FXMLCatalogoProveedores.fxml");
    }

    @FXML
    private void btnHacerVenta(ActionEvent event) {
        cargarVistaEnPanel("/mochi/vista/FXMLHacerVenta.fxml");
    }

    @FXML
    private void toggleMenuLateral(ActionEvent event) {
    }
}


