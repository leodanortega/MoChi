package mochi.modelo.pojo;

public class DetalleCompra{

    //private int idCompra;
    //private int idProducto;
    private int cantidad;
    private double precioCompra;

    public DetalleCompra() {
    }

    public DetalleCompra(int cantidad, double precioCompra) {
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }
}
