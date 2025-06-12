package mochi.modelo.pojo.reportes;

public class ClienteProductoTotal {
    private String nombreCliente;
    private String nombreProducto;
    private int totalComprado;

    public ClienteProductoTotal() {
    }

    public ClienteProductoTotal(String nombreCliente, String nombreProducto, int totalComprado) {
        this.nombreCliente = nombreCliente;
        this.nombreProducto = nombreProducto;
        this.totalComprado = totalComprado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getTotalComprado() {
        return totalComprado;
    }

    public void setTotalComprado(int totalComprado) {
        this.totalComprado = totalComprado;
    }
}
