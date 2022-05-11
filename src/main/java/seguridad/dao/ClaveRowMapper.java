package seguridad.dao;

import org.springframework.jdbc.core.RowMapper;
import seguridad.model.Clave;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClaveRowMapper implements RowMapper<Clave> {
    @Override
    public Clave mapRow(ResultSet rs, int rowNum) throws SQLException {
        Clave clave = new Clave();
        clave.setClave(rs.getString("clave"));
        return clave;
    }
}
