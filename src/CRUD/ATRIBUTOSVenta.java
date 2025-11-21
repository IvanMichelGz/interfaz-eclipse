package CRUD;

import javafx.beans.property.*;
import java.sql.Date;

public class ATRIBUTOSVenta {
    private final IntegerProperty id_venta = new SimpleIntegerProperty();
    private final ObjectProperty<Date> fecha = new SimpleObjectProperty<>();
    private final IntegerProperty id_cliente = new SimpleIntegerProperty();
    private final IntegerProperty id_empleado = new SimpleIntegerProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();

    // Getters y setters
    public int getId_venta() { return id_venta.get(); }
    public void setId_venta(int value) { id_venta.set(value); }
    public IntegerProperty id_ventaProperty() { return id_venta; }

    public Date getFecha() { return fecha.get(); }
    public void setFecha(Date value) { fecha.set(value); }
    public ObjectProperty<Date> fechaProperty() { return fecha; }

    public int getId_cliente() { return id_cliente.get(); }
    public void setId_cliente(int value) { id_cliente.set(value); }
    public IntegerProperty id_clienteProperty() { return id_cliente; }

    public int getId_empleado() { return id_empleado.get(); }
    public void setId_empleado(int value) { id_empleado.set(value); }
    public IntegerProperty id_empleadoProperty() { return id_empleado; }

    public double getTotal() { return total.get(); }
    public void setTotal(double value) { total.set(value); }
    public DoubleProperty totalProperty() { return total; }
}