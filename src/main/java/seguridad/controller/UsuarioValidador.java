package seguridad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seguridad.dao.UsuarioDao;
import seguridad.model.Usuario;

public class UsuarioValidador implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Usuario.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Usuario usuario = (Usuario) target;
        if(usuario.getCorreo().equals(""))
            errors.rejectValue("correo","obligatorio","Correo necesario");
        if(usuario.getPwd().equals(""))
            errors.rejectValue("pwd","obligatorio","Clave necesaria");
    }

    public void validateRegistro(Object target, Errors errors) {
        Usuario usuario = (Usuario) target;

        if(usuario.getNombre().equals(""))
            errors.rejectValue("nombre","obligatorio","Nombre necesario");
        if(usuario.getCorreo().equals(""))
            errors.rejectValue("correo","obligatorio","Correo necesario");
        if(usuario.getPwd().equals(""))
            errors.rejectValue("pwd","obligatorio","Clave necesaria");
    }
}
