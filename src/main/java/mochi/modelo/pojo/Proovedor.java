package mochi.modelo.pojo;

public class Proovedor {

    private int idProovedor;
    private String nombre;
    private String rfc;
    private String direccion;
    private String telefono;
    private String email;

    public Proovedor() {
    }

    public Proovedor(int idProovedor, String nombre, String rfc, String direccion, String telefono, String email) {
        this.idProovedor = idProovedor;
        this.nombre = nombre;
        this.rfc = rfc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public int getIdProovedor() {
        return idProovedor;
    }

    public void setIdProovedor(int idProovedor) {
        this.idProovedor = idProovedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    @Override
    public String toString() {
        return nombre; // Así se verá el nombre en el ComboBox
    }
}
