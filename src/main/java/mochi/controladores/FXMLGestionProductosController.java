package mochi.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mochi.dao.ProductoDAO;
import mochi.modelo.pojo.Producto;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FXMLGestionProductosController {

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colPresentacion;

    @FXML
    private TableColumn<Producto, Double> colCosto;

    @FXML
    private TableColumn<Producto, Integer> colCantidadActual;

    @FXML
    private TableColumn<Producto, Integer> colCantidadMinima;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    private ObservableList<Producto> productos;

    private ProductoDAO productoDAO;

    @FXML
    public void initialize() {
        productoDAO = new ProductoDAO();

        configurarTabla();
        cargarProductos();
        configurarBusqueda();

    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        colPresentacion.setCellValueFactory(new PropertyValueFactory<>("Presentacion"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("Costo"));
        colCantidadActual.setCellValueFactory(new PropertyValueFactory<>("CantidadActual"));
        colCantidadMinima.setCellValueFactory(new PropertyValueFactory<>("CantidadMinima"));
    }

    private void cargarProductos() {
        List<Producto> lista = new ProductoDAO().listar();
        productos = FXCollections.observableArrayList(lista);
        tablaProductos.setItems(productos);
    }

    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                tablaProductos.setItems(productos);
            } else {
                String filtro = newValue.toLowerCase();
                List<Producto> filtrados = productos.stream()
                        .filter(p -> p.getNombre().toLowerCase().contains(filtro))
                        .collect(Collectors.toList());
                tablaProductos.setItems(FXCollections.observableArrayList(filtrados));
            }
        });
    }

    public void regresarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) tablaProductos.getScene().getWindow();
        stage.close();
        // Aquí abres la ventana principal, si la tienes
    }

    public void agregarProducto(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioProductos.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Agregar Producto");
            stage.initModality(Modality.APPLICATION_MODAL);

            // No se pasa producto para agregar
            stage.showAndWait();

            // Refrescar tabla al cerrar ventana
            cargarProductos();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error al abrir el formulario de producto.");
        }
    }

    public void modificarProducto(ActionEvent actionEvent) {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un producto para modificar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioProductos.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Modificar Producto");
            stage.initModality(Modality.APPLICATION_MODAL);

            // Pasar el producto seleccionado al controlador del formulario
            FXMLFormularioProductosController controlador = loader.getController();
            controlador.inicializarFormulario(seleccionado);

            stage.showAndWait();

            // Refrescar tabla al cerrar ventana
            cargarProductos();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error al abrir el formulario de provseedor.");
        }
    }

    public void eliminarProducto(ActionEvent actionEvent) {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un producto para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de eliminar el producto seleccionado?");
        Optional<ButtonType> result = confirmacion.showAndWait();

        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            boolean eliminado = productoDAO.eliminar(seleccionado.getIdProducto());
            if (eliminado) {
                mostrarAlerta("Producto eliminado correctamente.");
                cargarProductos();
            } else {
                mostrarAlerta("No se pudo eliminar el producto.");
            }
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atención");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

