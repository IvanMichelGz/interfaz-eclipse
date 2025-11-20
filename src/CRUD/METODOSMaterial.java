package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class METODOSMaterial {

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
                m.setPrecio(rs.getFloat("precio_unitario"));
                m.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                m.setFecha_caducidad(rs.getDate("fecha_caducidad"));
                m.setUbicacion(rs.getString("ubicacion"));
                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarMaterial(ATRIBUTOSMaterial m) {
        String sql = "INSERT INTO empresadb.material (nombre, marca, area, unidad_media, cantidad, precio_unitario, fecha_ingreso, fecha_caducidad, ubicacion) VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getMarca());
            ps.setString(3, m.getArea());
            ps.setString(4, m.getUnidad());
            ps.setInt(5, m.getCantidad());
            ps.setFloat(6, m.getPrecio());
            ps.setDate(7, m.getFecha_ingreso());
            ps.setDate(8, m.getFecha_caducidad());
            ps.setString(9, m.getUbicacion());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateMaterial(ATRIBUTOSMaterial m) {
        String sql = "UPDATE empresadb.material SET nombre=?, marca=?, area=?, unidad_media=?, cantidad=?, precio_unitario=?, fecha_ingreso=?, fecha_caducidad=?, ubicacion=? WHERE id_material=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getMarca());
            ps.setString(3, m.getArea());
            ps.setString(4, m.getUnidad());
            ps.setInt(5, m.getCantidad());
            ps.setFloat(6, m.getPrecio());
            ps.setDate(7, m.getFecha_ingreso());
            ps.setDate(8, m.getFecha_caducidad());
            ps.setString(9, m.getUbicacion());
            ps.setInt(10, m.getId_material());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarMaterial(int id) {
        String sql = "DELETE FROM empresadb.material WHERE id_material=?";

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