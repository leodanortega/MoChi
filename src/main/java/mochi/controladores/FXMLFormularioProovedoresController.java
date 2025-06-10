package mochi.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mochi.dao.ProovedorDAO;
import mochi.modelo.pojo.Proovedor;

public class FXMLFormularioProovedoresController {

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

    private ProovedorDAO proovedorDAO;

    private Proovedor proovedorEditar; // si quieres cargar para editar

    @FXML
    public void initialize() {
        proovedorDAO = new ProovedorDAO();
    }

    public void setProovedorEditar(Proovedor p) {
        this.proovedorEditar = p;
        if (p != null) {
            tfNombre.setText(p.getNombre());
            tfRFC.setText(p.getRfc());
            tfDireccion.setText(p.getDireccion());
            tfTelefono.setText(p.getTelefono());
            tfEmail.setText(p.getEmail());
        }
    }

    @FXML
    private void guardarProovedor(ActionEvent event) {
        String nombre = tfNombre.getText().trim();
        String rfc = tfRFC.getText().trim();
        String direccion = tfDireccion.getText().trim();
        String telefono = tfTelefono.getText().trim();
        String email = tfEmail.getText().trim();

        if (nombre.isEmpty() || rfc.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, completa todos los campos.");
            alert.showAndWait();
            return;
        }

        if (proovedorEditar == null) {
            // Nuevo proveedor
            Proovedor p = new Proovedor();
            p.setNombre(nombre);
            p.setRfc(rfc);
            p.setDireccion(direccion);
            p.setTelefono(telefono);
            p.setEmail(email);

            boolean exito = proovedorDAO.agregar(p);
            if (exito) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Proveedor agregado");
                alert.setHeaderText(null);
                alert.setContentText("Proveedor agregado correctamente.");
                alert.showAndWait();
                cerrarVentana();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo agregar el proveedor.");
                alert.showAndWait();
            }
        } else {
            // Editar proveedor existente
            proovedorEditar.setNombre(nombre);
            proovedorEditar.setRfc(rfc);
            proovedorEditar.setDireccion(direccion);
            proovedorEditar.setTelefono(telefono);
            proovedorEditar.setEmail(email);

            boolean exito = proovedorDAO.modificar(proovedorEditar);
            if (exito) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Proveedor modificado");
                alert.setHeaderText(null);
                alert.setContentText("Proveedor modificado correctamente.");
                alert.showAndWait();
                cerrarVentana();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo modificar el proveedor.");
                alert.showAndWait();
            }
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
}
