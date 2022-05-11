package seguridad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seguridad.dao.ClaveDao;
import seguridad.model.Clave;
import seguridad.model.Usuario;

import javax.servlet.http.HttpSession;

@Controller
public class ClaveControlador {

    @Autowired
    private ClaveDao claveDao;

    @Autowired
    public void setClaveDao(ClaveDao claveDao) { this.claveDao = claveDao; }

    @RequestMapping("/totp")
    public String totp(Model model) {
        model.addAttribute("clave", new Clave());
        return "totp";
    }

    @RequestMapping(value="/totp", method= RequestMethod.POST)
    public String checkTotp(@ModelAttribute("clave") Clave clave,
                            BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "totp";
        }

        ClaveValidador claveValidador = new ClaveValidador();
        claveValidador.validate(clave, bindingResult);

        Usuario user = (Usuario) session.getAttribute("usuario");
        String miClave = claveDao.getClave(user);
        String claveTotp = claveDao.getTOTPCode(miClave);

        if(clave.getClave().equals(claveTotp)) {
            return "principal";
        }
        return "redirect:/login";
    }
}
