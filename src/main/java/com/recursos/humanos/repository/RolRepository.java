package main.java.com.recursos.humanos.repository;

import main.java.com.recursos.humanos.config.DatabaseConnection;
import main.java.com.recursos.humanos.exception.PersistenciaException;
import main.java.com.recursos.humanos.model.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolRepository {

    private static final String SQL_LISTAR = "select id_rol, nombre_rol from rol order by id_rol";

    public List<Rol> listar() {
        List<Rol> roles = new ArrayList<>();
        try (Connection conexion = DatabaseConnection.getConnection();
                PreparedStatement ps = conexion.prepareStatement(SQL_LISTAR);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                roles.add(new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol")));
            }
            return roles;
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudieron cargar los roles", e);
        }
    }
}
