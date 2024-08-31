package com.PjGl.pjgl.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.PjGl.pjgl.Model.Admin;
import com.PjGl.pjgl.Model.Manager;
import com.PjGl.pjgl.Repository.AdminRepo;
import com.PjGl.pjgl.Repository.ManagerRepo;

@Controller
public class adminpageContr {
    
    @Autowired
    private ManagerRepo managerRepo;
    
    @Autowired
    private AdminRepo adminRepo;
    
    
    
    @GetMapping("/managers")
    public String afficherManagers(Model model) {
        // Récupérer tous les managers
    	List<Manager> managers = managerRepo.findAll().stream()
                .filter(manager -> "Afficher".equals(manager.getStatut()))
                .collect(Collectors.toList());
        
        // Ajouter la liste des managers au modèle
        model.addAttribute("managers", managers);
        return "pages/adminpage"; // Retourne la vue affichant la liste des managers
    }
    
    
    @GetMapping("/details-manager/{id}")
    public String afficherDetailsManager(@PathVariable("id") String id, Model model) {
        // Rechercher le manager par son identifiant
        Optional<Manager> managerOptional = managerRepo.findById(id);
        
        if (managerOptional.isPresent()) {
            // Si le manager est trouvé, l'ajouter au modèle et afficher la vue des détails
            Manager manager = managerOptional.get();
            model.addAttribute("manager", manager);
            return "pages/details-manager"; // Retourne la vue affichant les détails du manager
        } else {
            // Si aucun manager n'est trouvé, rediriger vers la page des managers avec un message d'erreur
            model.addAttribute("errorMessage", "Le manager avec l'ID " + id + " n'a pas été trouvé.");
            return "redirect:/managers"; // Redirige vers la liste des managers
        }
    }

    
    @GetMapping("/modifier-manager/{id}")
    public String afficherFormulaireModification(@PathVariable("id") String id, Model model) {
        // Rechercher le manager par son identifiant
        Optional<Manager> managerOptional = managerRepo.findById(id);
        
        if (managerOptional.isPresent()) {
            // Si le manager est trouvé, l'ajouter au modèle et afficher le formulaire de modification
            Manager manager = managerOptional.get();
            model.addAttribute("manager", manager);
            return "pages/modifier-manager"; // Retourne la vue du formulaire de modification
        } else {
            // Si aucun manager n'est trouvé, rediriger vers la page des managers avec un message d'erreur
            model.addAttribute("errorMessage", "Le manager avec l'ID " + id + " n'a pas été trouvé.");
            return "redirect:/managers"; // Redirige vers la liste des managers
        }
    }
    
    @PostMapping("/modifier-manager/{id}")
    public String modifierManager(@PathVariable("id") String id, @ModelAttribute Manager modifiedManager, Model model) {

    	// Récupérer le manager existant par son identifiant
        Optional<Manager> managerOptional = managerRepo.findById(id);
        
        if (managerOptional.isPresent()) {
            // Mettre à jour les attributs du manager existant avec les nouvelles valeurs du formulaire
            Manager existingManager = managerOptional.get();
            existingManager.setId(id);
            existingManager.setNom(modifiedManager.getNom());
            existingManager.setPrenom(modifiedManager.getPrenom());
            existingManager.setEmail(modifiedManager.getEmail());
            existingManager.setPassword(modifiedManager.getPassword());
            
            // Enregistrer les modifications dans la base de données
            managerRepo.save(existingManager);
        } else {
            // Si aucun manager n'est trouvé, rediriger vers la page des managers avec un message d'erreur
            model.addAttribute("errorMessage", "Le manager avec l'ID " + id + " n'a pas été trouvé.");
            return "redirect:/managers"; // Redirige vers la liste des managers
        }
        
        // Rediriger vers la page des détails du manager mis à jour
        return "redirect:/details-manager/" + id;
    }


    @GetMapping("/supprimer-manager/{id}")
    public String supprimerManager(@PathVariable("id") String id, Model model) {
        Optional<Manager> managerOptional = managerRepo.findById(id);

        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();
            // Modifier le statut pour archiver le manager
            manager.setStatut("Archiver");
            managerRepo.save(manager);
            model.addAttribute("successMessage", "Le manager a été archivé avec succès.");
        } else {
            model.addAttribute("errorMessage", "Le manager avec l'ID " + id + " n'a pas été trouvé.");
            return "redirect:/managers"; // Redirige vers la liste des managers si non trouvé
        }

        return "redirect:/managers"; // Redirige vers la liste des managers après archivage
    }



    @GetMapping("/ajuser")
    public String afficherFormulaire() {
        return "pages/AjouterUser"; // Retourne la vue du formulaire
    }

    @PostMapping("/ajuser")
    public String ajouterUser(@RequestParam("nom") String nom,
                              @RequestParam("prenom") String prenom,
                              @RequestParam("email") String email,
                              @RequestParam("password") String password,
                              @RequestParam("image") MultipartFile file,
                              @RequestParam("role") String role,
                              Model model) {
        try {
            if (role.equals("admin")) {
                Admin admin = new Admin();
                admin.setNom(nom);
                admin.setPrenom(prenom);
                admin.setEmail(email);
                admin.setPassword(password);
                admin.setImage(file.getBytes());
                Admin savedAdmin = adminRepo.save(admin);
                if (savedAdmin != null) {
                    model.addAttribute("successMessage", "Admin a été enregistré avec succès.");
                } else {
                    model.addAttribute("errorMessage", "Une erreur s'est produite lors de l'enregistrement de l'admin.");
                }
            } else if (role.equals("manager")) {
                Manager manager = new Manager();
                manager.setNom(nom);
                manager.setPrenom(prenom);
                manager.setEmail(email);
                manager.setPassword(password);
                manager.setImage(file.getBytes());
                Manager savedManager = managerRepo.save(manager);
                if (savedManager != null) {
                    model.addAttribute("successMessage", "Manager a été enregistré avec succès.");
                } else {
                    model.addAttribute("errorMessage", "Une erreur s'est produite lors de l'enregistrement du manager.");
                }
            }
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Erreur lors de l'enregistrement de l'image");
            return "pages/AjouterUser";
        }

        return "pages/AjouterUser";
    }
}
