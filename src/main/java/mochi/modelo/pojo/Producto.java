package mochi.modelo.pojo;

public class Producto {

    private int idProducto;
    private String nombre;
    private String presentacion;
    private double costo;
    private int cantidadActual;
    private int cantidadMinima;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String presentacion, double costo, int cantidadActual, int cantidadMinima) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.costo = costo;
        this.cantidadActual = cantidadActual;
        this.cantidadMinima = cantidadMinima;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }
}
