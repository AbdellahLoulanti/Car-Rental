package com.PjGl.pjgl.Controller;

import Chain.AdminAuthenticationHandler;
import Chain.ManagerAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class LoginContr {

    @Autowired
    private AdminAuthenticationHandler adminHandler;

    @Autowired
    private ManagerAuthenticationHandler managerHandler;

    @PostConstruct
    public void init() {
        // Construire la chaîne d'authentification
        adminHandler.setNextHandler(managerHandler);
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "pages/Login";  // Assurez-vous que "login" correspond à votre vue de login.
    }


    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        Model model) {
        String result = adminHandler.authenticate(email, password);
        if (result.equals("pages/Login")) {
            model.addAttribute("errorMessage", "Erreur, email ou mot de passe incorrect.");
        }
        return result;
    }
}
