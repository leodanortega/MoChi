package mochi.modelo.pojo.reportes;

public class ClienteProducto {
    private int idCliente;
    private String nombreCliente;
    private int idProducto;
    private String nombreProducto;

    public ClienteProducto() {
    }

    public ClienteProducto(int idCliente, String nombreCliente, int idProducto, String nombreProducto) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}
