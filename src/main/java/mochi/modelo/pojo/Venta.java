package mochi.modelo.pojo;

import java.time.LocalDate;

public class Venta {

    private int idVenta;
    //private int idCliente;
    private double total;
    private LocalDate fecha;

    public Venta() {
    }

    public Venta(int idVenta, double total, LocalDate fecha) {
        this.idVenta = idVenta;
        this.total = total;
        this.fecha = fecha;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
