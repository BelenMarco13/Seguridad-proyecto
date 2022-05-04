package seguridad.dao;

import org.springframework.jdbc.core.RowMapper;
import seguridad.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRowMapper implements RowMapper<Usuario> {
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNombre(rs.getString("nombre"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setPwd(rs.getString("pwd"));
        usuario.setTelefono(rs.getInt("telefono"));
        return usuario;
    }
}
