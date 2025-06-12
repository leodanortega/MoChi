package mochi.modelo.pojo;

import java.time.LocalDate;

public class Venta {

    private int idVenta;
    private int idCliente;
    private double total;
    private LocalDate fecha;

    public Venta() {
    }

    public Venta(int idVenta, int idCliente, double total, LocalDate fecha) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.total = total;
        this.fecha = fecha;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    @Override
    public String toString() {
        return "Venta{" +
               "idVenta=" + idVenta +
               ", idCliente=" + idCliente +
               ", total=" + total +
               ", fecha=" + fecha +
               '}';
    }
}
