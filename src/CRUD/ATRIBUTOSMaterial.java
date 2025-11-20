package CRUD;

import javafx.beans.property.*;
import java.sql.Date;

public class ATRIBUTOSMaterial {

    private final IntegerProperty id_material;
    private final StringProperty nombre;
    private final StringProperty marca;
    private final StringProperty area;
    private final StringProperty unidad;
    private final IntegerProperty cantidad;
    private final FloatProperty precio;
    private final ObjectProperty<Date> fecha_ingreso;
    private final ObjectProperty<Date> fecha_caducidad;
    private final StringProperty ubicacion;

    public ATRIBUTOSMaterial() {
        this.id_material = new SimpleIntegerProperty();
        this.nombre = new SimpleStringProperty();
        this.marca = new SimpleStringProperty();
        this.area = new SimpleStringProperty();
        this.unidad = new SimpleStringProperty();
        this.cantidad = new SimpleIntegerProperty();
        this.precio = new SimpleFloatProperty();
        this.fecha_ingreso = new SimpleObjectProperty<>();
        this.fecha_caducidad = new SimpleObjectProperty<>();
        this.ubicacion = new SimpleStringProperty();
    }

    public int getId_material() { return id_material.get(); }
    public void setId_material(int value) { id_material.set(value); }
    public IntegerProperty id_materialProperty() { return id_material; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty nombreProperty() { return nombre; }

    public String getMarca() { return marca.get(); }
    public void setMarca(String value) { marca.set(value); }
    public StringProperty marcaProperty() { return marca; }

    public String getArea() { return area.get(); }
    public void setArea(String value) { area.set(value); }
    public StringProperty areaProperty() { return area; }

    public String getUnidad() { return unidad.get(); }
    public void setUnidad(String value) { unidad.set(value); }
    public StringProperty unidadProperty() { return unidad; }

    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int value) { cantidad.set(value); }
    public IntegerProperty cantidadProperty() { return cantidad; }

    public float getPrecio() { return precio.get(); }
    public void setPrecio(float value) { precio.set(value); }
    public FloatProperty precioProperty() { return precio; }

    public Date getFecha_ingreso() { return fecha_ingreso.get(); }
    public void setFecha_ingreso(Date value) { fecha_ingreso.set(value); }
    public ObjectProperty<Date> fecha_ingresoProperty() { return fecha_ingreso; }

    public Date getFecha_caducidad() { return fecha_caducidad.get(); }
    public void setFecha_caducidad(Date value) { fecha_caducidad.set(value); }
    public ObjectProperty<Date> fecha_caducidadProperty() { return fecha_caducidad; }

    public String getUbicacion() { return ubicacion.get(); }
    public void setUbicacion(String value) { ubicacion.set(value); }
    public StringProperty ubicacionProperty() { return ubicacion; }

    @Override
    public String toString() {
        return id_material.get() + " - " + nombre.get() + " (" + marca.get() + ")";
    }
}