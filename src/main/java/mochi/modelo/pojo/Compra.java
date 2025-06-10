package mochi.modelo.pojo;

import java.time.LocalDate;

public class Compra{

    //private int idProovedor;
    private int idCompra;
    private double total;
    private LocalDate fecha;

    public Compra() {
    }

    public Compra(int idCompra, double total, LocalDate fecha) {
        this.idCompra = idCompra;
        this.total = total;
        this.fecha = fecha;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
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
