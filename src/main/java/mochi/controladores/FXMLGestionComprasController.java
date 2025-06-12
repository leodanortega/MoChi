package mochi.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mochi.dao.ProductoDAO;
import mochi.modelo.pojo.Producto;

import java.util.List;
import java.util.stream.Collectors;

public class FXMLGestionComprasController {

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
        List<Producto> lista = productoDAO.listarProductosConDetalleCompra();
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

    @FXML
    public void regresarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) tablaProductos.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void registrarEntradas(ActionEvent event) {
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null) {
            mostrarAlerta("Selección requerida", "Por favor, selecciona un producto de la tabla.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFormularioRegistrarEntradasProducto.fxml"));
            Parent root = loader.load();

            // Pasar el producto seleccionado al controlador del formulario
            mochi.controladores.FXMLFormularioRegistrarEntradasProductoController controller = loader.getController();
            controller.inicializarProducto(productoSeleccionado);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar Entradas de Producto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarProductos(); // Recarga la tabla tras registrar entradas
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el formulario de entradas.");
        }
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

