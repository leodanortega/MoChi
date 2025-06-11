package mochi.modelo.pojo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Compra {

    private int idCompra;
    private int idProveedor;
    private double total;
    private LocalDate fecha;

    public Compra() {
    }

    public Compra(int idCompra, int idProveedor, double total, LocalDate fecha) {
        this.idCompra = idCompra;
        this.idProveedor = idProveedor;
        this.total = total;
        this.fecha = fecha;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Double getTotal() {
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
