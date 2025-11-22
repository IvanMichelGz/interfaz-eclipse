package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class METODOSMaterial {

    private final String servidor = "jdbc:sqlserver://IVANMICHEL07XD:1433;"
            + "databaseName=EmpresaDB;"
            + "encrypt=true;"
            + "trustServerCertificate=true";
    private final String usuario = "sa";
    private final String clave = "Ivan123";

    /** Conexión a la base de datos */
    public Connection conexion() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(servidor, usuario, clave);
        } catch (Exception e) {
            System.err.println("Error al conectar con la BD: " + e.getMessage());
            return null;
        }
    }

    /** Listar todos los materiales */
    public ObservableList<ATRIBUTOSMaterial> listarMateriales() {
        ObservableList<ATRIBUTOSMaterial> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM empresadb.material";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ATRIBUTOSMaterial m = new ATRIBUTOSMaterial();
                m.setId_material(rs.getInt("id_material"));
                m.setNombre(rs.getString("nombre"));
                m.setMarca(rs.getString("marca"));
                m.setArea(rs.getString("area"));
                m.setUnidad(rs.getString("unidad_media"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setPrecio(rs.getDouble("precio_unitario"));
                m.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                m.setFecha_caducidad(rs.getDate("fecha_caducidad"));
                m.setUbicacion(rs.getString("ubicacion"));
                lista.add(m);
            }

        } catch (SQLException e) {
            System.err.println("Error en listarMateriales: " + e.getMessage());
        }

        return lista;
    }

    /** Insertar un nuevo material */
    public void insertarMaterial(ATRIBUTOSMaterial m) {
        String sql = "INSERT INTO empresadb.material (nombre, marca, area, unidad_media, cantidad, precio_unitario, fecha_ingreso, fecha_caducidad, ubicacion) VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getMarca());
            ps.setString(3, m.getArea());
            ps.setString(4, m.getUnidad());
            ps.setInt(5, m.getCantidad());
            ps.setDouble(6, m.getPrecio());
            ps.setDate(7, m.getFecha_ingreso());
            ps.setDate(8, m.getFecha_caducidad());
            ps.setString(9, m.getUbicacion());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error en insertarMaterial: " + e.getMessage());
        }
    }

    /** Actualizar un material existente */
    public boolean updateMaterial(ATRIBUTOSMaterial m) {
        String sql = "UPDATE empresadb.material SET nombre=?, marca=?, area=?, unidad_media=?, cantidad=?, precio_unitario=?, fecha_ingreso=?, fecha_caducidad=?, ubicacion=? WHERE id_material=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getMarca());
            ps.setString(3, m.getArea());
            ps.setString(4, m.getUnidad());
            ps.setInt(5, m.getCantidad());
            ps.setDouble(6, m.getPrecio());
            ps.setDate(7, m.getFecha_ingreso());
            ps.setDate(8, m.getFecha_caducidad());
            ps.setString(9, m.getUbicacion());
            ps.setInt(10, m.getId_material());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en updateMaterial: " + e.getMessage());
            return false;
        }
    }

    /** Eliminar un material por ID */
    public boolean eliminarMaterial(int id) {
        String sql = "DELETE FROM empresadb.material WHERE id_material=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en eliminarMaterial: " + e.getMessage());
            return false;
        }
    }

    /** Buscar precio por nombre de material */
    public double buscarPrecioPorNombre(String nombreMaterial) {
        String sql = "SELECT precio_unitario FROM empresadb.material WHERE nombre = ?";
        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreMaterial);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("precio_unitario");
            }
        } catch (SQLException e) {
            System.err.println("Error en buscarPrecioPorNombre: " + e.getMessage());
        }
        return 0.0;
    }

    /** Buscar ID por nombre de material */
    public int buscarIdPorNombre(String nombreMaterial) {
        String sql = "SELECT id_material FROM empresadb.material WHERE nombre = ?";
        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreMaterial);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_material");
            }
        } catch (SQLException e) {
            System.err.println("Error en buscarIdPorNombre: " + e.getMessage());
        }
        return 0;
    }

    // ============================
    // Métodos extra para autocompletado
    // ============================

    /** Listar solo nombres de materiales (para autocompletado) */
    public List<String> listarNombresMateriales() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nombre FROM empresadb.material";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            System.err.println("Error en listarNombresMateriales: " + e.getMessage());
        }
        return lista;
    }
}