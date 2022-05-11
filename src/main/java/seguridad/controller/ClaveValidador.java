package seguridad.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import seguridad.model.Clave;
import seguridad.model.Usuario;

public class ClaveValidador implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Usuario.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Clave clave = (Clave) target;

        if(clave.getClave().equals(""))
            errors.rejectValue("clave","obligatorio","Clave necesaria");
    }
}
