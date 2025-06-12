package mochi.modelo.pojo;

import javafx.beans.property.*;

public class ProductoVentaDTO {

    private Producto producto;
    private double valorModificador; // en lugar de Promocion

    private IntegerProperty cantidad = new SimpleIntegerProperty(1); // editable cantidad, default 1
    private DoubleProperty subtotal = new SimpleDoubleProperty(0);

    public ProductoVentaDTO(Producto producto, double valorModificador) {
        this.producto = producto;
        this.valorModificador = valorModificador;

        calcularSubtotal();

        // Listener para recalcular subtotal si cantidad cambia
        cantidad.addListener((obs, oldVal, newVal) -> {
            calcularSubtotal();
        });
    }

    private void calcularSubtotal() {
        double costo = producto.getCosto();
        int cant = cantidad.get();

        if (cant > producto.getCantidadActual()) {
            // Limitar la cantidad al máximo disponible
            cantidad.set(producto.getCantidadActual());
            cant = producto.getCantidadActual();
        }

        subtotal.set(cant * costo * valorModificador);
    }

    public Producto getProducto() {
        return producto;
    }

    public double getValorModificador() {
        return valorModificador;
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal.get();
    }

    public ReadOnlyDoubleProperty subtotalProperty() {
        return subtotal;
    }
}
