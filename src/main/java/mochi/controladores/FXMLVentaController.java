package mochi.controladores;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import mochi.dao.ClienteDAO;
import mochi.dao.DetalleVentaDAO;
import mochi.dao.PromocionDAO;
import mochi.dao.ProductoDAO;
import mochi.dao.VentaDAO;
import mochi.modelo.pojo.Cliente;
import mochi.modelo.pojo.DetalleVenta;
import mochi.modelo.pojo.Producto;
import mochi.modelo.pojo.ProductoVentaDTO;
import mochi.modelo.pojo.Promocion;

public class FXMLVentaController {

    @FXML
    private ComboBox<Cliente> cbCliente;

    @FXML
    private GridPane gridProductos;

    @FXML
    private TableView<ProductoVentaDTO> tableVenta;

    @FXML
    private TableColumn<ProductoVentaDTO, String> colNombre;

    @FXML
    private TableColumn<ProductoVentaDTO, String> colPresentacion;

    @FXML
    private TableColumn<ProductoVentaDTO, Double> colCosto;

    @FXML
    private TableColumn<ProductoVentaDTO, Integer> colCantidadActual;

    @FXML
    private TableColumn<ProductoVentaDTO, Integer> colCantidadEditable;

    @FXML
    private TableColumn<ProductoVentaDTO, Double> colValorModificador;

    @FXML
    private TableColumn<ProductoVentaDTO, Double> colSubtotal;

    @FXML
    private Label lblTotalVenta;

    private ClienteDAO clienteDAO = new ClienteDAO();
    private PromocionDAO promocionDAO = new PromocionDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private VentaDAO ventaDAO = new VentaDAO();
    private DetalleVentaDAO DetalleventaDAO = new DetalleVentaDAO();

    private List<Promocion> promocionesActivasHoy = new ArrayList<>();
    private List<Producto> productos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public void initialize() {
        configurarComboBox();
        configurarColumnasTabla();
        cargarClientes();
        cargarPromociones();
        cargarProductos();

        cbCliente.getSelectionModel().selectedItemProperty().addListener((obs, viejoCliente, nuevoCliente) -> {
            limpiarTablaVenta();
            actualizarTotalVenta();
            if (nuevoCliente != null) {
                mostrarPromocionesCliente(nuevoCliente);
            }
        });

        tableVenta.getItems().addListener((javafx.collections.ListChangeListener<ProductoVentaDTO>) c -> {
            actualizarTotalVenta();
        });
    }

    private void configurarComboBox() {
        cbCliente.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente cliente) {
                return (cliente == null) ? "" : cliente.getNombre();
            }

            @Override
            public Cliente fromString(String string) {
                return null; // No se usa
            }
        });
    }

    private void configurarColumnasTabla() {
        colNombre.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getProducto().getNombre()));

        colPresentacion.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getProducto().getPresentacion()));

        colCosto.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getProducto().getCosto()).asObject());

        colCantidadActual.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getProducto().getCantidadActual()).asObject());

        colCantidadEditable.setCellValueFactory(cellData -> 
            cellData.getValue().cantidadProperty().asObject());

        tableVenta.setEditable(true);
        colCantidadEditable.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        colCantidadEditable.setOnEditCommit(event -> {
            ProductoVentaDTO productoVenta = event.getRowValue();
            int nuevaCantidad = event.getNewValue();

            if (nuevaCantidad < 1) {
                nuevaCantidad = 1;
            } else if (nuevaCantidad > productoVenta.getProducto().getCantidadActual()) {
                nuevaCantidad = productoVenta.getProducto().getCantidadActual();
            }
            productoVenta.setCantidad(nuevaCantidad);
            actualizarTotalVenta();
        });

        colValorModificador.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getValorModificador()).asObject());

        colSubtotal.setCellValueFactory(cellData -> 
            cellData.getValue().subtotalProperty().asObject());
    }

    private void cargarClientes() {
        clientes = clienteDAO.listar();

        Cliente clienteSinCuenta = new Cliente();
        clienteSinCuenta.setIdCliente(8);
        clienteSinCuenta.setNombre("Venta a cliente sin cuenta");

        cbCliente.getItems().clear();
        cbCliente.getItems().add(clienteSinCuenta);

        if (clientes != null && !clientes.isEmpty()) {
            cbCliente.getItems().addAll(clientes);
        }

        cbCliente.getSelectionModel().select(clienteSinCuenta);
    }

    private void cargarPromociones() {
        promocionesActivasHoy = promocionDAO.obtenerPromocionesActivasHoy();

        System.out.println("Promociones activas cargadas: " + promocionesActivasHoy.size());

        for (Promocion promo : promocionesActivasHoy) {
            System.out.println("→ ID Promoción: " + promo.getIdPromocion() +
                               ", Cliente ID: " + promo.getIdCliente() +
                               ", Producto ID: " + promo.getIdProducto() +
                               ", Valor: " + promo.getValorModificador());
        }
    }

    private void cargarProductos() {
        productos = productoDAO.listar();

        gridProductos.getChildren().clear();

        int columnas = 5;
        int fila = 0;
        int columna = 0;

        for (Producto producto : productos) {
            Button btnProducto = new Button(producto.getNombre() + " - " + producto.getPresentacion());
            btnProducto.setWrapText(true);
            btnProducto.setMaxWidth(Double.MAX_VALUE);
            btnProducto.setPrefHeight(60);
            GridPane.setHgrow(btnProducto, Priority.ALWAYS);

            btnProducto.setTooltip(new Tooltip(
                "Presentación: " + producto.getPresentacion() + "\n" +
                "Precio: $" + producto.getCosto()));

            btnProducto.setOnAction(e -> agregarProductoAVenta(producto));

            gridProductos.add(btnProducto, columna, fila);

            columna++;
            if (columna >= columnas) {
                columna = 0;
                fila++;
            }
        }
    }

    private void agregarProductoAVenta(Producto producto) {
        Cliente clienteSeleccionado = cbCliente.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            System.out.println("No hay cliente seleccionado.");
            return;
        }

        ProductoVentaDTO existente = null;
        for (ProductoVentaDTO dto : tableVenta.getItems()) {
            if (dto.getProducto().getIdProducto() == producto.getIdProducto()) {
                existente = dto;
                break;
            }
        }

        if (existente != null) {
            tableVenta.getItems().remove(existente);
        } else {
            double modificador = verificarPromocionParaProducto(producto, clienteSeleccionado);
            ProductoVentaDTO dto = new ProductoVentaDTO(producto, modificador);
            tableVenta.getItems().add(dto);
        }

        actualizarTotalVenta();
    }

    private double verificarPromocionParaProducto(Producto producto, Cliente cliente) {
        for (Promocion promo : promocionesActivasHoy) {
            if (promo.getIdCliente() == cliente.getIdCliente() && 
                promo.getIdProducto() == producto.getIdProducto()) {
                return promo.getValorModificador();
            }
        }
        return 1;
    }

    private void limpiarTablaVenta() {
        tableVenta.getItems().clear();
    }

    private void actualizarTotalVenta() {
        double total = 0;
        for (ProductoVentaDTO item : tableVenta.getItems()) {
            total += item.getSubtotal();
        }
        lblTotalVenta.setText(String.format("Total: $%.2f", total));
    }

    // NUEVO método para mostrar alert con promociones del cliente
    private void mostrarPromocionesCliente(Cliente cliente) {
        List<Promocion> promocionesCliente = new ArrayList<>();
        for (Promocion promo : promocionesActivasHoy) {
            if (promo.getIdCliente() == cliente.getIdCliente()) {
                promocionesCliente.add(promo);
            }
        }

        if (promocionesCliente.isEmpty()) {
            return; // No mostrar nada si no tiene promociones
        }

        StringBuilder mensaje = new StringBuilder();
        mensaje.append(cliente.getNombre()).append(" tiene las siguientes promociones:\n\n");

        for (Promocion promo : promocionesCliente) {
            // Buscar el producto para obtener el nombre
            Producto prod = productos.stream()
                            .filter(p -> p.getIdProducto() == promo.getIdProducto())
                            .findFirst()
                            .orElse(null);

            if (prod != null) {
                // Asumimos que el valor modificador es el multiplicador del precio (ej: 0.8 para 20% descuento)
                // Para obtener el porcentaje de descuento:
                double descuento = (1 - promo.getValorModificador()) * 100;
                mensaje.append(String.format("%s tiene un descuento de %.0f%%\n", prod.getNombre(), descuento));
            }
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Promociones para " + cliente.getNombre());
        alert.setHeaderText(null);
        alert.setContentText(mensaje.toString());
        alert.showAndWait();
    }

    @FXML
    private void btnCompra(ActionEvent event) {
        generarVenta();
    }

    @FXML
    private void btnSalir(ActionEvent event) {
        Stage stage = (Stage) cbCliente.getScene().getWindow();
        stage.close();
    }
    
    private void generarVenta() {
    Cliente clienteSeleccionado = cbCliente.getSelectionModel().getSelectedItem();
    List<ProductoVentaDTO> productosVenta = tableVenta.getItems();

    if (clienteSeleccionado == null) {
        mostrarAlerta(Alert.AlertType.WARNING, "Cliente no seleccionado", "Debes seleccionar un cliente.");
        return;
    }

    if (productosVenta.isEmpty()) {
        mostrarAlerta(Alert.AlertType.WARNING, "Venta vacía", "Agrega productos a la venta antes de continuar.");
        return;
    }

    int idCliente = clienteSeleccionado.getIdCliente();
    int idVenta = ventaDAO.generarVenta(idCliente);

    if (idVenta <= 0) {
        mostrarAlerta(Alert.AlertType.ERROR, "Error al generar venta", "No se pudo crear la venta.");
        return;
    }

    List<DetalleVenta> detalles = new ArrayList<>();
    for (ProductoVentaDTO dto : productosVenta) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setIdProducto(dto.getProducto().getIdProducto());
        detalle.setIdVenta(idVenta);
        detalle.setCantidadProducto(dto.getCantidad());
        detalle.setTotalProducto(dto.getSubtotal());
        detalles.add(detalle);
    }

    DetalleventaDAO.registrarDetalle(detalles);

    mostrarAlerta(Alert.AlertType.INFORMATION, "Venta registrada", "La venta se registró correctamente");

    limpiarTablaVenta();
    actualizarTotalVenta();
}
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(contenido);
    alerta.showAndWait();
}


    
}
