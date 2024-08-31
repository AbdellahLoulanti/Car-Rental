package com.PjGl.pjgl.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

	@GetMapping("/home")
    public String home() {
        return "pages/home"; // retourne le nom du fichier HTML home.html dans le dossier des ressources/templates
    }
}
