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
    // TABLA VENTAS
    // ============================

    /** Listar ventas con JOIN para obtener nombres de cliente y empleado */
    public ObservableList<ATRIBUTOSVenta> listarVentas() {
        ObservableList<ATRIBUTOSVenta> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT v.id_venta, v.fecha,
                   v.id_cliente, c.nombre AS nombre_cliente,
                   v.id_empleado, e.nombre_empleado,
                   v.id_material, v.nombre_material,
                   v.precio_material, v.cantidad, v.subtotal, v.total
            FROM empresadb.ventas v
            JOIN empresadb.cliente c ON v.id_cliente = c.id_cliente
            JOIN empresadb.empleado e ON v.id_empleado = e.id_empleado
        """;

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ATRIBUTOSVenta v = new ATRIBUTOSVenta();
                v.setId_venta(rs.getInt("id_venta"));
                v.setFecha(rs.getDate("fecha"));
                v.setId_cliente(rs.getInt("id_cliente"));
                v.setNombre_cliente(rs.getString("nombre_cliente"));
                v.setId_empleado(rs.getInt("id_empleado"));
                v.setNombre_empleado(rs.getString("nombre_empleado"));
                v.setId_material(rs.getInt("id_material"));
                v.setNombre_material(rs.getString("nombre_material"));
                v.setPrecio_unitario(rs.getDouble("precio_material"));
                v.setCantidad(rs.getInt("cantidad"));
                v.setSubtotal(rs.getDouble("subtotal"));
                v.setTotal(rs.getDouble("total"));
                lista.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /** Insertar nueva venta */
    public void insertarVenta(ATRIBUTOSVenta v) {
        String sql = """
            INSERT INTO empresadb.ventas
            (fecha, id_cliente, id_empleado,
             id_material, nombre_material, precio_material,
             cantidad, subtotal, total)
            VALUES (?,?,?,?,?,?,?,?,?)
        """;

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, v.getFecha());
            ps.setInt(2, v.getId_cliente());
            ps.setInt(3, v.getId_empleado());
            ps.setInt(4, v.getId_material());
            ps.setString(5, v.getNombre_material());
            ps.setDouble(6, v.getPrecio_unitario());
            ps.setInt(7, v.getCantidad());
            ps.setDouble(8, v.getSubtotal());
            ps.setDouble(9, v.getTotal());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Actualizar venta existente */
    public boolean updateVenta(ATRIBUTOSVenta v) {
        String sql = """
            UPDATE empresadb.ventas
            SET fecha=?, id_cliente=?, id_empleado=?,
                id_material=?, nombre_material=?, precio_material=?,
                cantidad=?, subtotal=?, total=?
            WHERE id_venta=?
        """;

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, v.getFecha());
            ps.setInt(2, v.getId_cliente());
            ps.setInt(3, v.getId_empleado());
            ps.setInt(4, v.getId_material());
            ps.setString(5, v.getNombre_material());
            ps.setDouble(6, v.getPrecio_unitario());
            ps.setInt(7, v.getCantidad());
            ps.setDouble(8, v.getSubtotal());
            ps.setDouble(9, v.getTotal());
            ps.setInt(10, v.getId_venta());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Eliminar venta por ID */
    public boolean eliminarVenta(int id) {
        String sql = "DELETE FROM empresadb.venta WHERE id_venta=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}