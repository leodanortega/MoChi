package mochi.controladores;

import javafx.fxml.Initializable;
import mochi.modelo.pojo.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLPrincipalEmpleadoController implements Initializable {

    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        // Aquí puedes hacer lógica específica si el empleado tiene un nombre o permisos específicos.
    }

}