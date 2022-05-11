package seguridad.dao;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seguridad.model.Usuario;

import javax.security.auth.kerberos.EncryptionKey;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UsuarioDao {

    private JdbcTemplate jdbcTemplate;
    final Map<String, Usuario> listaUsuarios = new HashMap<>();

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addUsuario(Usuario usuario){
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String pwd = passwordEncryptor.encryptPassword(usuario.getPwd());
        jdbcTemplate.update("INSERT INTO Usuario VALUES(?,?,?,?,?)",
                usuario.getNombre(), usuario.getCorreo(), pwd, usuario.getTelefono(), usuario.getClave());

        usuario.setPwd(pwd);
        listaUsuarios.put(usuario.getCorreo(), usuario);
    }

    public Usuario getUsuario(String correo, String pwd){

        List<Usuario> listUsuario = jdbcTemplate.query("SELECT * FROM Usuario WHERE correo = ?",
                new UsuarioRowMapper(), correo);

        if(listUsuario.size() == 0)
            return null;

        Usuario usuario = listUsuario.get(0);
        if(usuario == null)
            return null;

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if(passwordEncryptor.checkPassword(pwd, usuario.getPwd()))
            return usuario;
        else
            return null;
    }

    public List<Usuario> getUsuarios() {
        try {
            return jdbcTemplate.query("SELECT * FROM Usuario", new UsuarioRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Usuario>();
        }
    }
}
