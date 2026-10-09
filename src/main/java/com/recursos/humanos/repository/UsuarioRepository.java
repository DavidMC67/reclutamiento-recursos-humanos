package main.java.com.recursos.humanos.repository;

import main.java.com.recursos.humanos.config.DatabaseConnection;
import main.java.com.recursos.humanos.exception.PersistenciaException;
import main.java.com.recursos.humanos.model.Rol;
import main.java.com.recursos.humanos.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioRepository {

    private static final String SQL_EXISTE_CORREO = "select count(*) from usuario where correo_usuario = ?";
    private static final String SQL_GUARDAR = "insert into usuario (nombre_usuario, apellido_usuario, correo_usuario, contrasena_usuario, id_rol) values (?, ?, ?, ?, ?)";
    private static final String SQL_BUSCAR_POR_CORREO = "select u.id_usuario, u.nombre_usuario, u.apellido_usuario, u.correo_usuario, u.contrasena_usuario, r.id_rol, r.nombre_rol "
            + "from usuario u inner join rol r on u.id_rol = r.id_rol where u.correo_usuario = ?";

    public boolean existeCorreo(String correo) {
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement ps = conexion.prepareStatement(SQL_EXISTE_CORREO)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo verificar el correo", e);
        }
    }

    public void guardar(Usuario usuario) {
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement ps = conexion.prepareStatement(SQL_GUARDAR)) {
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getApellidoUsuario());
            ps.setString(3, usuario.getCorreoUsuario());
            ps.setString(4, usuario.getContrasenaUsuario());
            ps.setInt(5, usuario.getRol().getIdRol());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo registrar el usuario", e);
        }
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement ps = conexion.prepareStatement(SQL_BUSCAR_POR_CORREO)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setApellidoUsuario(rs.getString("apellido_usuario"));
                usuario.setCorreoUsuario(rs.getString("correo_usuario"));
                usuario.setContrasenaUsuario(rs.getString("contrasena_usuario"));
                usuario.setRol(new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol")));
                return Optional.of(usuario);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo consultar el usuario", e);
        }
    }
}
