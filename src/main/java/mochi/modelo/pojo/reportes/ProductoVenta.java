
package mochi.modelo.pojo.reportes;

public class ProductoVenta {
    private int idProducto;
    private String nombreProducto;
    private int totalVendido;

    public ProductoVenta() {
    }

    public ProductoVenta(int idProducto, String nombreProducto, int totalVendido) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.totalVendido = totalVendido;
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

    public int getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(int totalVendido) {
        this.totalVendido = totalVendido;
    }
}

