package mochi.modelo.pojo;

public class DetalleVenta {

    //private int idProducto;
    //private int idVenta;
    private int cantidadProoducto;
    private double totalProducto;

    public DetalleVenta() {
    }

    public DetalleVenta(int cantidadProoducto, double totalProducto) {
        this.cantidadProoducto = cantidadProoducto;
        this.totalProducto = totalProducto;
    }

    public int getCantidadProoducto() {
        return cantidadProoducto;
    }

    public void setCantidadProoducto(int cantidadProoducto) {
        this.cantidadProoducto = cantidadProoducto;
    }

    public double getTotalProducto() {
        return totalProducto;
    }

    public void setTotalProducto(double totalProducto) {
        this.totalProducto = totalProducto;
    }
}
