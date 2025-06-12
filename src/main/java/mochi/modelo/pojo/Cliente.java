package mochi.modelo.pojo;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String direccion;
    private String rfc;
    private String telefono;
    private String email;
    private boolean factura;

    public Cliente() {
    }

    public Cliente(int idCliente, String nombre, String direccion, String rfc, String telefono, String email, boolean factura) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.rfc = rfc;
        this.telefono = telefono;
        this.email = email;
        this.factura = factura;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFactura() {
        return factura;
    }

    public void setFactura(boolean factura) {
        this.factura = factura;
    }
    
    @Override
public String toString() {
    return nombre;
}

}
