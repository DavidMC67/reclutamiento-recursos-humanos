package main.java.com.recursos.humanos.repository;

import main.java.com.recursos.humanos.config.DatabaseConnection;
import main.java.com.recursos.humanos.exception.PersistenciaException;
import main.java.com.recursos.humanos.model.Aspirante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AspiranteRepository {

    private static final String SQL_LISTAR = "select id_aspirante, nombre_aspirante, apellido_aspirante, correo_aspirante, telefono_aspirante, direccion_aspirante, edad_aspirante from aspirantes order by id_aspirante";
    private static final String SQL_GUARDAR = "insert into aspirantes (nombre_aspirante, apellido_aspirante, correo_aspirante, telefono_aspirante, direccion_aspirante, edad_aspirante) values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_ACTUALIZAR = "update aspirantes set nombre_aspirante = ?, apellido_aspirante = ?, correo_aspirante = ?, telefono_aspirante = ?, direccion_aspirante = ?, edad_aspirante = ? where id_aspirante = ?";
    private static final String SQL_ELIMINAR = "delete from aspirantes where id_aspirante = ?";
    private static final String SQL_EXISTE_CORREO = "select count(*) from aspirantes where correo_aspirante = ? and id_aspirante <> ?";

    public List<Aspirante> listar() {
        List<Aspirante> aspirantes = new ArrayList<>();
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement ps = conexion.prepareStatement(SQL_LISTAR);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                aspirantes.add(new Aspirante(
                        rs.getInt("id_aspirante"),
                        rs.getString("nombre_aspirante"),
                        rs.getString("apellido_aspirante"),
                        rs.getString("correo_aspirante"),
                        rs.getString("telefono_aspirante"),
                        rs.getString("direccion_aspirante"),
                        rs.getInt("edad_aspirante")));
            }
            return aspirantes;
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo cargar la lista de aspirantes", e);
        }
    }

    public void guardar(Aspirante aspirante) {
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement ps = conexion.prepareStatement(SQL_GUARDAR)) {
            ps.setString(1, aspirante.getNombreAspirante());
            ps.setString(2, aspirante.getApellidoAspirante());
            ps.setString(3, aspirante.getCorreoAspirante());
            ps.setString(4, aspirante.getTelefonoAspirante());
            ps.setString(5, aspirante.getDireccionAspirante());
            ps.setInt(6, aspirante.getEdadAspirante());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo guardar el aspirante", e);
        }
    }

    public boolean actualizar(Aspirante aspirante) {
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement ps = conexion.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, aspirante.getNombreAspirante());
            ps.setString(2, aspirante.getApellidoAspirante());
            ps.setString(3, aspirante.getCorreoAspirante());
            ps.setString(4, aspirante.getTelefonoAspirante());
            ps.setString(5, aspirante.getDireccionAspirante());
            ps.setInt(6, aspirante.getEdadAspirante());
            ps.setInt(7, aspirante.getIdAspirante());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo actualizar el aspirante", e);
        }
    }

    public boolean eliminar(int idAspirante) {
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement ps = conexion.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, idAspirante);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo eliminar el aspirante", e);
        }
    }

    public boolean existeCorreo(String correo, int idExcluido) {
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement ps = conexion.prepareStatement(SQL_EXISTE_CORREO)) {
            ps.setString(1, correo);
            ps.setInt(2, idExcluido);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo verificar el correo del aspirante", e);
        }
    }
}
