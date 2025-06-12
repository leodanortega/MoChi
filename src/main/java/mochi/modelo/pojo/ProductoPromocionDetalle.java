package mochi.modelo.pojo;


import javafx.beans.property.*;

public class ProductoPromocionDetalle {
    private final StringProperty nombreProducto = new SimpleStringProperty();
    private final IntegerProperty cantidadActual = new SimpleIntegerProperty();
    private final DoubleProperty costo = new SimpleDoubleProperty();
    private final DoubleProperty valorModificador = new SimpleDoubleProperty();
    private final IntegerProperty cantidadComprar = new SimpleIntegerProperty(0);
    private final DoubleProperty subtotal = new SimpleDoubleProperty();

    public ProductoPromocionDetalle(String nombre, int cantidadActual, double costo, double valorModificador) {
        this.nombreProducto.set(nombre);
        this.cantidadActual.set(cantidadActual);
        this.costo.set(costo);
        this.valorModificador.set(valorModificador);

        // Cuando cambie cantidadComprar, recalcular subtotal
        this.cantidadComprar.addListener((obs, oldVal, newVal) -> {
            int qty = newVal.intValue();
            if (qty > this.cantidadActual.get()) {
                // No permitir más que la cantidad actual
                this.cantidadComprar.set(this.cantidadActual.get());
                qty = this.cantidadActual.get();
            } else if (qty < 0) {
                this.cantidadComprar.set(0);
                qty = 0;
            }
            // subtotal = costo * valorModificador * cantidadComprar
            this.subtotal.set(this.costo.get() * this.valorModificador.get() * qty);
        });
    }

    // Getters y setters (properties)
    public StringProperty nombreProductoProperty() { return nombreProducto; }
    public IntegerProperty cantidadActualProperty() { return cantidadActual; }
    public DoubleProperty costoProperty() { return costo; }
    public DoubleProperty valorModificadorProperty() { return valorModificador; }
    public IntegerProperty cantidadComprarProperty() { return cantidadComprar; }
    public DoubleProperty subtotalProperty() { return subtotal; }
}
