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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mochi.dao.ClienteDAO;
import mochi.modelo.pojo.Cliente;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FXMLGestionClientesController {

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colRfc;

    @FXML
    private TableColumn<Cliente, String> colDireccion;

    @FXML
    private TableColumn<Cliente, String> colTelefono;

    @FXML
    private TableColumn<Cliente, String> colEmail;

    @FXML
    private TextField txtBuscar;

    private ClienteDAO clienteDAO;

    @FXML
    public void initialize() {
        clienteDAO = new ClienteDAO();

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRfc.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        obtenerClientes();

        // Listener para búsqueda en tiempo real
        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                obtenerClientes();
            } else {
                buscarClientes(newVal);
            }
        });
    }

    private void obtenerClientes() {
        List<Cliente> lista = clienteDAO.listar();
        ObservableList<Cliente> observableList = FXCollections.observableArrayList(lista);
        tablaClientes.setItems(observableList);
    }

    private void buscarClientes(String textoBusqueda) {
        List<Cliente> lista = clienteDAO.buscarPorNombre(textoBusqueda);
        ObservableList<Cliente> observableList = FXCollections.observableArrayList(lista);
        tablaClientes.setItems(observableList);
    }

    @FXML
    public void regresarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) tablaClientes.getScene().getWindow();
        stage.close();
        // Aquí abres la ventana principal si la tienes
    }

    @FXML
    public void agregarCliente(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioClientes.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Agregar Cliente");
            stage.initModality(Modality.APPLICATION_MODAL);

            // No se pasa cliente para agregar
            stage.showAndWait();

            // Refrescar tabla al cerrar ventana
            obtenerClientes();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaError("Error al abrir el formulario de cliente.");
        }
    }

    @FXML
    public void modificarCliente(ActionEvent actionEvent) {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlertaWarning("Selecciona un cliente para modificar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioClientes.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Modificar Cliente");
            stage.initModality(Modality.APPLICATION_MODAL);

            // Pasar el cliente seleccionado al controlador del formulario
            FXMLFormularioClientesController controlador = loader.getController();
            controlador.setClienteEditar(seleccionado);

            stage.showAndWait();

            // Refrescar tabla al cerrar ventana
            obtenerClientes();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaError("Error al abrir el formulario de cliente.");
        }
    }

    @FXML
    public void eliminarCliente(ActionEvent actionEvent) {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlertaWarning("Selecciona un cliente para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de eliminar el cliente seleccionado?");
        Optional<javafx.scene.control.ButtonType> result = confirmacion.showAndWait();

        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            boolean eliminado = clienteDAO.eliminar(seleccionado.getIdCliente());
            if (eliminado) {
                mostrarAlertaInfo("Cliente eliminado correctamente.");
                obtenerClientes();
            } else {
                mostrarAlertaError("No se pudo eliminar el cliente.");
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
