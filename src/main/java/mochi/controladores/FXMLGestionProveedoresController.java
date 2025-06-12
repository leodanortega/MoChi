package mochi.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mochi.dao.ProovedorDAO;
import mochi.modelo.pojo.Proovedor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FXMLGestionProveedoresController {

    @FXML
    private TableView<Proovedor> tablaProveedores;

    @FXML
    private TableColumn<Proovedor, String> colNombre;

    @FXML
    private TableColumn<Proovedor, String> colRfc;

    @FXML
    private TableColumn<Proovedor, String> colDireccion;

    @FXML
    private TableColumn<Proovedor, String> colTelefono;

    @FXML
    private TableColumn<Proovedor, String> colEmail;

    private ProovedorDAO proovedorDAO;

    @FXML
    public void initialize() {
        proovedorDAO = new ProovedorDAO();

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRfc.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        obtenerProovedores();
    }

    private void obtenerProovedores() {
        List<Proovedor> lista = proovedorDAO.listar();
        ObservableList<Proovedor> observableList = FXCollections.observableArrayList(lista);
        tablaProveedores.setItems(observableList);
    }

    public void regresarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) tablaProveedores.getScene().getWindow();
        stage.close();

    }

    public void agregarProveedor(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioProovedores.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Agregar Proveedor");
            stage.initModality(Modality.APPLICATION_MODAL);


            stage.showAndWait();


            obtenerProovedores();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaError("Error al abrir el formulario de proveedor.");
        }
    }

    public void modificarProveedor(ActionEvent actionEvent) {
        Proovedor seleccionado = tablaProveedores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlertaWarning("Selecciona un proveedor para modificar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioProovedores.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Modificar Proveedor");
            stage.initModality(Modality.APPLICATION_MODAL);

            // Pasar el proveedor seleccionado al controlador del formulario
            FXMLFormularioProovedoresController controlador = loader.getController();
            controlador.setProovedorEditar(seleccionado);

            stage.showAndWait();

            // Refrescar tabla al cerrar ventana
            obtenerProovedores();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaError("Error al abrir el formulario de proveedor.");
        }
    }

    public void eliminarProveedor(ActionEvent actionEvent) {
        Proovedor seleccionado = tablaProveedores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlertaWarning("Selecciona un proveedor para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de eliminar el proveedor seleccionado?");
        Optional<javafx.scene.control.ButtonType> result = confirmacion.showAndWait();

        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            boolean eliminado = proovedorDAO.eliminar(seleccionado.getIdProovedor());
            if (eliminado) {
                mostrarAlertaInfo("Proveedor eliminado correctamente.");
                obtenerProovedores();
            } else {
                mostrarAlertaError("No se pudo eliminar el proveedor.");
            }
        }
    }

    private void mostrarAlertaInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaWarning(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

