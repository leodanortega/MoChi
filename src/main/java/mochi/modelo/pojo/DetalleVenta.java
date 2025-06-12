package mochi.modelo.pojo;

public class DetalleVenta {

    private int idVenta;
    private int idProducto;
    private int cantidadProducto;
    private double totalProducto;

    public DetalleVenta() {
    }

    public DetalleVenta(int idVenta, int idProducto, int cantidadProducto, double totalProducto) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidadProducto = cantidadProducto;
        this.totalProducto = totalProducto;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public double getTotalProducto() {
        return totalProducto;
    }

    public void setTotalProducto(double totalProducto) {
        this.totalProducto = totalProducto;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
               "idVenta=" + idVenta +
               ", idProducto=" + idProducto +
               ", cantidadProducto=" + cantidadProducto +
               ", totalProducto=" + totalProducto +
               '}';
    }
}
