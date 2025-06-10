package mochi.modelo.pojo;

import java.time.LocalDate;

public class Pedido {

    private int idPedido;
    //private int idProducto;
    //private int idProovedor;
    private int cantidad;
    private LocalDate fecha;

    public Pedido() {
    }

    public Pedido(int idPedido, int cantidad, LocalDate fecha) {
        this.idPedido = idPedido;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
