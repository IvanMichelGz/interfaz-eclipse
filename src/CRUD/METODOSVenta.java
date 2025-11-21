package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class METODOSVenta {

    // Conexión a SQL Server
    public Connection conexion() {
        String servidor = "jdbc:sqlserver://IVANMICHEL07XD:1433;"
                + "databaseName=EmpresaDB;"
                + "encrypt=true;"
                + "trustServerCertificate=true";
        String usuario = "sa";
        String clave = "Ivan123";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(servidor, usuario, clave);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ============================
    // CABECERA DE VENTA (tabla venta)
    // ============================

    public ObservableList<ATRIBUTOSVenta> listarVentas() {
        ObservableList<ATRIBUTOSVenta> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM empresadb.ventas";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ATRIBUTOSVenta v = new ATRIBUTOSVenta();
                v.setId_venta(rs.getInt("id_venta"));
                v.setFecha(rs.getDate("fecha"));
                v.setId_cliente(rs.getInt("id_cliente"));
                v.setId_empleado(rs.getInt("id_empleado"));
                v.setTotal(rs.getDouble("total"));
                lista.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarVenta(ATRIBUTOSVenta v) {
        String sql = "INSERT INTO empresadb.ventas (fecha, id_cliente, id_empleado, total) VALUES (?,?,?,?)";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, v.getFecha());
            ps.setInt(2, v.getId_cliente());
            ps.setInt(3, v.getId_empleado());
            ps.setDouble(4, v.getTotal());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateVenta(ATRIBUTOSVenta v) {
        String sql = "UPDATE empresadb.ventas SET fecha=?, id_cliente=?, id_empleado=?, total=? WHERE id_venta=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, v.getFecha());
            ps.setInt(2, v.getId_cliente());
            ps.setInt(3, v.getId_empleado());
            ps.setDouble(4, v.getTotal());
            ps.setInt(5, v.getId_venta());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarVenta(int id) {
        String sql = "DELETE FROM empresadb.ventas WHERE id_venta=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================
    // DETALLE DE VENTA (tabla detalle_venta)
    // ============================

    public ObservableList<ATRIBUTOSDetalleVenta> listarDetalleVenta(int idVenta) {
        ObservableList<ATRIBUTOSDetalleVenta> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM empresadb.detalle_venta WHERE id_venta=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ATRIBUTOSDetalleVenta d = new ATRIBUTOSDetalleVenta();
                d.setId_detalle(rs.getInt("id_detalle"));
                d.setId_venta(rs.getInt("id_venta"));
                d.setId_material(rs.getInt("id_material"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecio_unitario(rs.getFloat("precio_unitario"));
                d.setSubtotal(rs.getFloat("subtotal"));
                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarDetalleVenta(ATRIBUTOSDetalleVenta d) {
        String sql = "INSERT INTO empresadb.detalle_venta (id_venta, id_material, cantidad, precio_unitario, subtotal) VALUES (?,?,?,?,?)";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, d.getId_venta());
            ps.setInt(2, d.getId_material());
            ps.setInt(3, d.getCantidad());
            ps.setFloat(4, d.getPrecio_unitario());
            ps.setFloat(5, d.getSubtotal());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean eliminarDetalleVenta(int idDetalle) {
        String sql = "DELETE FROM empresadb.detalle_venta WHERE id_detalle=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idDetalle);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}