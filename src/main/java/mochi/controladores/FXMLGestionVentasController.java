package mochi.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mochi.dao.ClienteDAO;
import mochi.dao.DetalleVentaDAO;
import mochi.dao.ProductoDAO;
import mochi.dao.VentaDAO;
import mochi.modelo.pojo.Cliente;
import mochi.modelo.pojo.DetalleVenta;
import mochi.modelo.pojo.Producto;
import mochi.modelo.pojo.Venta;

public class FXMLGestionVentasController implements Initializable {

    // Tabla Ventas
    @FXML private TableView<Venta> tablaVentas;
    @FXML private TableColumn<Venta, Number> colIdVenta;
    @FXML private TableColumn<Venta, String> colFecha;
    @FXML private TableColumn<Venta, String> colCliente;
    @FXML private TableColumn<Venta, Number> colTotal;
    @FXML private Label lblTotalVentas;

    // Tabla Detalles
    @FXML private TableView<DetalleVenta> tabladetallesventa;
    @FXML private TableColumn<DetalleVenta, Number> colIdVentad;
    @FXML private TableColumn<DetalleVenta, String> colCliented;
    @FXML private TableColumn<DetalleVenta, String> colProducto;
    @FXML private TableColumn<DetalleVenta, Number> colCantidad;
    @FXML private TableColumn<DetalleVenta, Number> colPrecio;
    @FXML private TableColumn<DetalleVenta, Number> colSubTotal;
    @FXML private Label lblTotalDetalle;

    // Filtros
    @FXML private DatePicker fechaInicioPicker;
    @FXML private DatePicker fechaFinPicker;
    @FXML private TextField campoBusquedaNombre;

    @FXML private Button btnRegresar;

    private List<Venta> listaVentas = new ArrayList<>();
    private List<Cliente> listaClientes = new ArrayList<>();
    private List<Producto> listaProductos = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1) Cargar datos maestros
        listaClientes  = new ClienteDAO().listar();
        listaProductos = new ProductoDAO().listar();

        // 2) Configurar columnas de tablaVentas
        colIdVenta.setCellValueFactory(v -> 
            new SimpleIntegerProperty(v.getValue().getIdVenta()));
        colFecha.setCellValueFactory(v -> 
            new SimpleStringProperty(v.getValue().getFecha().toString()));
        colCliente.setCellValueFactory(v -> {
            int idC = v.getValue().getIdCliente();
            String nom = "Desconocido";
            for (Cliente c : listaClientes) {
                if (c.getIdCliente() == idC) { 
                    nom = c.getNombre(); 
                    break; 
                }
            }
            return new SimpleStringProperty(nom);
        });
        colTotal.setCellValueFactory(v -> 
            new SimpleDoubleProperty(v.getValue().getTotal()));

        // 3) Configurar columnas de tabladetallesventa
        colIdVentad.setCellValueFactory(d -> 
            new SimpleIntegerProperty(d.getValue().getIdVenta()));
        colCliented.setCellValueFactory(d -> {
            int vid = d.getValue().getIdVenta();
            String nom = "Desconocido";
            for (Venta v : listaVentas) {
                if (v.getIdVenta() == vid) {
                    for (Cliente c : listaClientes) {
                        if (c.getIdCliente() == v.getIdCliente()) {
                            nom = c.getNombre();
                            break;
                        }
                    }
                    break;
                }
            }
            return new SimpleStringProperty(nom);
        });
        colProducto.setCellValueFactory(d -> {
            int pid = d.getValue().getIdProducto();
            String prod = "Desconocido";
            for (Producto p : listaProductos) {
                if (p.getIdProducto() == pid) { 
                    prod = p.getNombre(); 
                    break; 
                }
            }
            return new SimpleStringProperty(prod);
        });
        colCantidad.setCellValueFactory(d -> 
            new SimpleIntegerProperty(d.getValue().getCantidadProducto()));
        colPrecio.setCellValueFactory(d -> {
            double unidad = d.getValue().getTotalProducto() 
                            / d.getValue().getCantidadProducto();
            return new SimpleDoubleProperty(unidad);
        });
        colSubTotal.setCellValueFactory(d -> 
            new SimpleDoubleProperty(d.getValue().getTotalProducto()));

        // 4) Cargar ventas iniciales
        cargarVentas();

        // 5) Listeners de filtro
        campoBusquedaNombre.textProperty().addListener((o,a,b) -> filtrarVentas());
        fechaInicioPicker.valueProperty().addListener((o,a,b) -> filtrarVentas());
        fechaFinPicker.valueProperty().addListener((o,a,b) -> filtrarVentas());

        // 6) Doble clic para cargar detalles
        tablaVentas.setOnMouseClicked(evt -> {
            if (evt.getClickCount() == 2 
                && !tablaVentas.getSelectionModel().isEmpty()) {
                Venta sel = tablaVentas.getSelectionModel()
                                     .getSelectedItem();
                cargarDetalleVenta(sel);
            }
        });
    }

    private void cargarVentas() {
        listaVentas = new VentaDAO().obtenerTodasLasVentas();
        tablaVentas.getItems().setAll(listaVentas);
        actualizarTotalVentas();
    }

    private void filtrarVentas() {
        String txt = campoBusquedaNombre.getText()
                                        .toLowerCase()
                                        .trim();
        LocalDate ini = fechaInicioPicker.getValue();
        LocalDate fin = fechaFinPicker.getValue();

        List<Venta> filtro = new ArrayList<>();
        for (Venta v : listaVentas) {
            // filtro por nombre cliente
            String nom = "Desconocido";
            for (Cliente c : listaClientes) {
                if (c.getIdCliente() == v.getIdCliente()) {
                    nom = c.getNombre();
                    break;
                }
            }
            boolean okNom = nom.toLowerCase().contains(txt);
            boolean okIni = ini == null || !v.getFecha().isBefore(ini);
            boolean okFin = fin == null || !v.getFecha().isAfter(fin);
            if (okNom && okIni && okFin) {
                filtro.add(v);
            }
        }
        tablaVentas.getItems().setAll(filtro);
        actualizarTotalVentas();
    }

    private void actualizarTotalVentas() {
        double total = 0;
        for (Venta v : tablaVentas.getItems()) {
            total += v.getTotal();
        }
        lblTotalVentas.setText(
            String.format("Total Ventas: $%.2f", total)
        );
    }

    @FXML
    private void btnRegresar(ActionEvent event) {
        // tu lógica de regreso...
    }

    private void cargarDetalleVenta(Venta venta) {
        List<DetalleVenta> detalles = 
            new DetalleVentaDAO()
                 .obtenerDetallePorVenta(venta.getIdVenta());
        tabladetallesventa.getItems().setAll(detalles);
        actualizarTotalDetalle();
    }

    private void actualizarTotalDetalle() {
        double total = 0;
        for (DetalleVenta d : 
             tabladetallesventa.getItems()) {
            total += d.getTotalProducto();
        }
        lblTotalDetalle.setText(
            String.format("Total Detalle: $%.2f", total)
        );
    }
}
