package mochi.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mochi.dao.ProductoDAO;
import mochi.modelo.pojo.Producto;

import java.util.List;
import java.util.stream.Collectors;

public class FXMLGestionarProductosController {

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, Integer> colId;

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

    @FXML
    public void initialize() {
        configurarTabla();
        cargarProductos();
        configurarBusqueda();

        btnAgregar.setOnAction(e -> agregarProducto());
        btnModificar.setOnAction(e -> modificarProducto());
        btnEliminar.setOnAction(e -> eliminarProducto());
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPresentacion.setCellValueFactory(new PropertyValueFactory<>("presentacion"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        colCantidadActual.setCellValueFactory(new PropertyValueFactory<>("cantidadActual"));
        colCantidadMinima.setCellValueFactory(new PropertyValueFactory<>("cantidadMinima"));
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

    public void agregarProducto() {
        // Aquí puedes abrir un formulario para registrar un nuevo producto
        System.out.println("Agregar producto");
    }

    public void modificarProducto() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            // Aquí puedes abrir un formulario con los datos del producto para modificar
            System.out.println("Modificar producto: " + seleccionado.getNombre());
        } else {
            mostrarAlerta("Debes seleccionar un producto para modificar.");
        }
    }

    public void eliminarProducto() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            boolean exito = new ProductoDAO().eliminar(seleccionado.getIdProducto());
            if (exito) {
                productos.remove(seleccionado);
                tablaProductos.refresh();
            } else {
                mostrarAlerta("Error al eliminar el producto.");
            }
        } else {
            mostrarAlerta("Debes seleccionar un producto para eliminar.");
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

