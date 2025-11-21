package CRUD;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class METODOSProveedor {

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

    public ObservableList<ATRIBUTOSProveedor> listarProveedores() {
        ObservableList<ATRIBUTOSProveedor> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM empresadb.proveedor";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ATRIBUTOSProveedor p = new ATRIBUTOSProveedor();
                p.setId_proveedor(rs.getInt("id_proveedor"));
                p.setNombre(rs.getString("nombre"));
                p.setDireccion(rs.getString("direccion"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("email"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarProveedor(ATRIBUTOSProveedor p) {
        String sql = "INSERT INTO empresadb.proveedor (nombre, direccion, telefono, email) VALUES (?,?,?,?)";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDireccion());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getEmail());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateProveedor(ATRIBUTOSProveedor p) {
        String sql = "UPDATE empresadb.proveedor SET nombre=?, direccion=?, telefono=?, email=? WHERE id_proveedor=?";

        try (Connection con = conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDireccion());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getEmail());
            ps.setInt(5, p.getId_proveedor());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarProveedor(int id) {
        String sql = "DELETE FROM empresadb.proveedor WHERE id_proveedor=?";

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