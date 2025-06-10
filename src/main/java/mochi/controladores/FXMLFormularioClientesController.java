package mochi.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mochi.dao.ClienteDAO;
import mochi.modelo.pojo.Cliente;

public class FXMLFormularioClientesController {

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfRFC;

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TextField tfEmail;

    @FXML
    private CheckBox cbFactura;

    private ClienteDAO clienteDAO;

    private Cliente clienteEditar; // Para cargar datos en modo edición

    @FXML
    public void initialize() {
        clienteDAO = new ClienteDAO();
    }

    public void setClienteEditar(Cliente c) {
        this.clienteEditar = c;
        if (c != null) {
            tfNombre.setText(c.getNombre());
            tfRFC.setText(c.getRfc());
            tfDireccion.setText(c.getDireccion());
            tfTelefono.setText(c.getTelefono());
            tfEmail.setText(c.getEmail());
            cbFactura.setSelected(c.isFactura());
        }
    }

    @FXML
    private void guardarCliente(ActionEvent event) {
        String nombre = tfNombre.getText().trim();
        String rfc = tfRFC.getText().trim();
        String direccion = tfDireccion.getText().trim();
        String telefono = tfTelefono.getText().trim();
        String email = tfEmail.getText().trim();
        boolean factura = cbFactura.isSelected();

        if (nombre.isEmpty() || rfc.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, completa todos los campos.");
            alert.showAndWait();
            return;
        }

        if (clienteEditar == null) {
            // Nuevo cliente
            Cliente c = new Cliente();
            c.setNombre(nombre);
            c.setRfc(rfc);
            c.setDireccion(direccion);
            c.setTelefono(telefono);
            c.setEmail(email);
            c.setFactura(factura);

            boolean exito = clienteDAO.agregar(c);
            if (exito) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cliente agregado");
                alert.setHeaderText(null);
                alert.setContentText("Cliente agregado correctamente.");
                alert.showAndWait();
                cerrarVentana();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo agregar el cliente.");
                alert.showAndWait();
            }
        } else {
            // Editar cliente existente
            clienteEditar.setNombre(nombre);
            clienteEditar.setRfc(rfc);
            clienteEditar.setDireccion(direccion);
            clienteEditar.setTelefono(telefono);
            clienteEditar.setEmail(email);
            clienteEditar.setFactura(factura);

            boolean exito = clienteDAO.modificar(clienteEditar);
            if (exito) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cliente modificado");
                alert.setHeaderText(null);
                alert.setContentText("Cliente modificado correctamente.");
                alert.showAndWait();
                cerrarVentana();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo modificar el cliente.");
                alert.showAndWait();
            }
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
}
