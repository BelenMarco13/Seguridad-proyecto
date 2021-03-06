package seguridad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seguridad.dao.ClaveDao;
import seguridad.dao.UsuarioDao;
import seguridad.model.Usuario;

import javax.servlet.http.HttpSession;

@Controller
public class UsuarioControlador {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private ClaveDao claveDao;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String checkLogin(@ModelAttribute("usuario") Usuario usuario,
                             BindingResult bindingResult, HttpSession session) {

        UsuarioValidador usuarioValidador = new UsuarioValidador();
        usuarioValidador.validate(usuario, bindingResult);

        if (bindingResult.hasErrors()) {
            return "login";
        }

        usuario = usuarioDao.getUsuario(usuario.getCorreo(), usuario.getPwd());

        if (usuario == null) {
            bindingResult.rejectValue("pwd", "datos incorrectos", "Correo o clave incorrecta");
            return "login";
        }

        session.setAttribute("usuario", usuario);
        return "totp";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value="/registro")
    public String addUsuario(Model model, HttpSession session) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @RequestMapping(value="/registro", method= RequestMethod.POST)
    public String processAddSubmit(Model model, @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult bindingResult, HttpSession session) {
        UsuarioValidador usuarioValidador = new UsuarioValidador();
        usuarioValidador.validateRegistro(usuario, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registro";
        }

        String key = claveDao.generateSecretKey();
        usuario.setClave(key);

        usuarioDao.addUsuario(usuario);
        session.setAttribute("usuario", usuario);
        return "principal";
    }

}
