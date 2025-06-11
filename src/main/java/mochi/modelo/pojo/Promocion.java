package mochi.modelo.pojo;

import java.time.LocalDate;

public class Promocion {

    private int idPromocion;
    private int idCliente;
    private int idProducto;
    private double valorModificador;
    private LocalDate fechaIncio;
    private LocalDate fechaFin;

    public Promocion() {
    }

    public Promocion(int idPromocion, int idCliente, int idProducto, double valorModificador, LocalDate fechaIncio, LocalDate fechaFin) {
        this.idPromocion = idPromocion;
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.valorModificador = valorModificador;
        this.fechaIncio = fechaIncio;
        this.fechaFin = fechaFin;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public double getValorModificador() {
        return valorModificador;
    }

    public void setValorModificador(double valorModificador) {
        this.valorModificador = valorModificador;
    }

    public LocalDate getFechaIncio() {
        return fechaIncio;
    }

    public void setFechaIncio(LocalDate fechaIncio) {
        this.fechaIncio = fechaIncio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
