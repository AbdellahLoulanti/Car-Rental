package com.PjGl.pjgl.Controller;






import java.time.LocalDateTime;


import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.mail.javamail.JavaMailSender;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

import com.PjGl.pjgl.Model.Client;

import com.PjGl.pjgl.Model.Voiture;
import com.PjGl.pjgl.Model.reservation;
import com.PjGl.pjgl.Observer.ReservationManager;
import com.PjGl.pjgl.Repository.ReservationRepo;


import com.PjGl.pjgl.Repository.ClientRepo;
import com.PjGl.pjgl.Repository.VoitureRepo;
import com.PjGl.pjgl.factory.CarFactory;
import com.PjGl.pjgl.service.MailService;

import java.io.IOException;




@Controller
public class managerpageContr {

    private final CarFactory carFactory = new CarFactory();

	@Autowired
    private ClientRepo clientRepo;
	
	
	@Autowired
    private VoitureRepo voitureRepo;
	
	@Autowired
    private ReservationRepo reservationRepo;
	
    @Autowired
    private MailService mailService;
    
    @Autowired
    private ReservationManager reservationManager;


	
	@GetMapping("/managerpage")
    public String afficherpagemanager() {
        return "pages/managerpage"; // Retourne la vue du formulaire
    }

    @GetMapping("/ajouter-client")
    public String afficherFormulaire() {
        return "pages/AjouterClient"; // Retourne la vue du formulaire
    }
    
    @GetMapping("/AjouterResrvation")
    public String afficherForm() {
        return "pages/AjouterReservation"; // Retourne la vue du formulaire
    }

    @PostMapping("/ajouter-client")
    public String enregistrerClient(@RequestParam("nom") String nom,
                                    @RequestParam("prenom") String prenom,
                                    @RequestParam("email") String email,
                                    @RequestParam("image") MultipartFile file,
                                    @RequestParam("tele") String tele,
                                    @RequestParam("password") String password,
                                    @RequestParam("cin") String cin, 
                                    Model model, 
                                    RedirectAttributes redirectAttributes) {
        // Créez une nouvelle instance de Client avec les données du formulaire
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);

        try {
            if (!file.isEmpty()) {
                client.setImage(file.getBytes());
            }
        } catch (IOException e) {
            // En cas d'erreur, ajoutez un message d'erreur et retournez au formulaire
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'enregistrement de l'image.");
            return "redirect:/ajouter-client";
        }

        client.setTele(tele);
        client.setPassword(password);
        client.setCin(cin);
        
        // Enregistrez le client dans la base de données
        Client savedClient = clientRepo.save(client);

        if (savedClient != null) {
            // L'enregistrement a réussi, ajoutez un message de succès
            redirectAttributes.addFlashAttribute("successMessage", "Client a été enregistré avec succès.");
        } else {
            // L'enregistrement a échoué, ajoutez un message d'erreur
            redirectAttributes.addFlashAttribute("errorMessage", "Une erreur s'est produite lors de l'enregistrement de client.");
        }
        
        return "redirect:/ajouter-client";
    }

    
    @PostMapping("/AjouterReservation") // Assurez-vous que ceci correspond exactement à ce qui est dans le formulaire
    public String enregistrerReservation(@RequestParam("nomClient") String nomClient,
                                         @RequestParam("voitureId") String voitureId,
                                         @RequestParam("dateDebut") LocalDateTime dateDebut,
                                         @RequestParam("dateFin") LocalDateTime dateFin,
                                         @RequestParam("discount") double discount,
                                         @RequestParam("status") String status, Model model) {
        // Créez une nouvelle instance de Reservation avec les données du formulaire
        reservation reserv = new reservation();
        reserv.setNomClient(nomClient);
        
        // Trouver la voiture correspondante par ID
        Voiture voiture = voitureRepo.findById(voitureId).orElse(null);
        if (voiture == null) {
            model.addAttribute("errorMessage", "Voiture non trouvée.");
            return "pages/AjouterReservation"; // Assurez-vous que cette vue existe.
        }
        reserv.setNomVoiture(voiture.getMarque() + "-" + voiture.getModele()); // ou juste stocker l'ID selon le besoin
        reserv.setDateDebut(dateDebut);
        reserv.setDateFin(dateFin);
        reserv.setRemise(discount);
        reserv.setStatus(status);
        
        
        // Enregistrez la réservation dans la base de données
        reservation savedReservation = reservationRepo.save(reserv);

        if (savedReservation != null) {
            // L'enregistrement a réussi, ajoutez un message de succès à la vue
            model.addAttribute("successMessage", "La réservation a été enregistrée avec succès.");
            return "pages/AjouterReservation"; // Assurez-vous que cette vue existe.
        } else {
            // L'enregistrement a échoué, ajoutez un message d'erreur à la vue
            model.addAttribute("errorMessage", "Une erreur s'est produite lors de l'enregistrement de la réservation.");
            return "pages/AjouterReservation"; // Assurez-vous que cette vue existe.
        }
    }

    @GetMapping("/voir-reservations")
    public String VoirReservation(Model model) {
        // Récupérer toutes les réservations
        List<reservation> reservations = reservationRepo.findAll().stream()
                .filter(reservation -> "Afficher".equals(reservation.getStatut()))
                .collect(Collectors.toList());

        // Ajouter la liste des réservations au modèle avec le nom correct
        model.addAttribute("reservations", reservations);
        return "pages/voir-reservations"; // Retourne la vue affichant la liste des réservations
    }
    
    
    @PostMapping("/accepter-reservation/{id}")
    public String accepterReservation(@PathVariable String id) {
        reservation reservation = reservationRepo.findById(id).orElse(null);
        if (reservation != null) {
            reservation.setStatus("Confirmée");
            reservationRepo.save(reservation);
        }
        return "redirect:/voir-reservations";
    }

    @PostMapping("/refuser-reservation/{id}")
    public String refuserReservation(@PathVariable String id) {
        reservation reservation = reservationRepo.findById(id).orElse(null);
        if (reservation != null) {
            reservationRepo.delete(reservation);
        }
        return "redirect:/voir-reservations";
    }



    @GetMapping("/voir-clients")
    public String afficherClients(Model model) {
        // Récupérer tous les managers
    	List<Client> clients = clientRepo.findAll().stream()
                .filter(client -> "Afficher".equals(client.getStatut()))
                .collect(Collectors.toList());
        
        // Ajouter la liste des managers au modèle
        model.addAttribute("clients", clients);
        return "pages/voir-clients"; // Retourne la vue affichant la liste des managers
    }
    
    
    
    @GetMapping("/modifier-client/{id}")
    public String afficherFormulaireModification(@PathVariable("id") String id, Model model) {
        // Rechercher le manager par son identifiant
        Optional<Client> clientOptional = clientRepo.findById(id);
        
        if (clientOptional.isPresent()) {
            // Si le manager est trouvé, l'ajouter au modèle et afficher le formulaire de modification
            Client client = clientOptional.get();
            model.addAttribute("client", client);
            return "pages/modifier-client"; // Retourne la vue du formulaire de modification
        } else {
            // Si aucun manager n'est trouvé, rediriger vers la page des managers avec un message d'erreur
            model.addAttribute("errorMessage", "Le client avec l'ID " + id + " n'a pas été trouvé.");
            return "redirect:/voir-clients"; // Redirige vers la liste des managers
        }
    }
    
    @PostMapping("/modifier-client/{id}")
    public String modifierClient(@PathVariable("id") String id, @ModelAttribute Client modifiedClient, Model model) {

    	// Récupérer le manager existant par son identifiant
        Optional<Client> clientrOptional = clientRepo.findById(id);
        
        if (clientrOptional.isPresent()) {
            // Mettre à jour les attributs du manager existant avec les nouvelles valeurs du formulaire
            Client existingClient = clientrOptional.get();
            existingClient.setId(id);
            existingClient.setNom(modifiedClient.getNom());
            existingClient.setPrenom(modifiedClient.getPrenom());
            existingClient.setEmail(modifiedClient.getEmail());
            existingClient.setTele(modifiedClient.getTele());
            existingClient.setPassword(modifiedClient.getPassword());
            existingClient.setCin(modifiedClient.getCin());
            
            // Enregistrer les modifications dans la base de données
            clientRepo.save(existingClient);
        } else {
            // Si aucun manager n'est trouvé, rediriger vers la page des managers avec un message d'erreur
            model.addAttribute("errorMessage", "Le client avec l'ID " + id + " n'a pas été trouvé.");
            return "redirect:/voir-clients"; // Redirige vers la liste des managers
        }
        
        // Rediriger vers la page des détails du manager mis à jour
        return "redirect:/voir-clients";
    }
    
    
    
    @GetMapping("/supprimer-client/{id}")
    public String supprimerClient(@PathVariable("id") String id, Model model) {
        Optional<Client> clientOptional = clientRepo.findById(id);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            // Modifier le statut pour archiver le manager
            client.setStatut("Archiver");
            clientRepo.save(client);
            model.addAttribute("successMessage", "Le client a été archivé avec succès.");
        } else {
            model.addAttribute("errorMessage", "Le client avec l'ID " + id + " n'a pas été trouvé.");
            return "redirect:/voir-clients"; // Redirige vers la liste des managers si non trouvé
        }

        return "redirect:/voir-clients"; // Redirige vers la liste des managers après archivage
    }
    
    @GetMapping("/rechercher-client")
    	public String rechere(Model model) {
            return "pages/rechercher-client"; // Retourne la vue du formulaire
    }
    
   
    /*@PostMapping("/rechercher-client")
    public String rechercherClientParCIN(@RequestParam("cin") String cin, Model model, RedirectAttributes redirectAttributes) {
        Client client = clientRepo.findOneByCin(cin);
        
        if (client != null) {
            // Utiliser addFlashAttribute pour ajouter l'objet client qui persiste après la redirection
            redirectAttributes.addFlashAttribute("client", client);
            return "redirect:/rechercher-client"; // Utilisez la redirection
        } else {
            // Passer le message d'erreur aussi via redirect attributes
            redirectAttributes.addFlashAttribute("errorMessage", "Aucun client trouvé avec le CIN " + cin);
            return "redirect:/rechercher-client";
        }
    }
    */
    
    @PostMapping("/rechercher-client")
    public String rechercherClientparCin(@RequestParam("cin") String cin, RedirectAttributes redirectAttributes) {
        List<Client> clients = clientRepo.findByCin(cin);

        if (clients.isEmpty()) {
            // S'il n'y a pas de réservations trouvées
            redirectAttributes.addFlashAttribute("errorMessage", "Aucune client trouvée pour cette Cin '" + cin + "'");
        } else {
            // S'il y a des réservations trouvées, passez-les à la vue
            redirectAttributes.addFlashAttribute("clients", clients);
        }

        return "redirect:/rechercher-client"; // Utilisez la redirection
    }

 

    
    @GetMapping("/rechercher-reservation")
   	public String recherr(Model model) {
           return "pages/rechercher-reservation"; // Retourne la vue du formulaire
   }
    
    @PostMapping("/rechercher-reservation")
    public String rechercherReservationParNomVoiture(@RequestParam("nomVoiture") String nomVoiture, RedirectAttributes redirectAttributes) {
        List<reservation> reservations = reservationRepo.findByNomVoiture(nomVoiture);

        if (reservations.isEmpty()) {
            // S'il n'y a pas de réservations trouvées
            redirectAttributes.addFlashAttribute("errorMessage", "Aucune réservation trouvée pour la voiture nommée '" + nomVoiture + "'");
        } else {
            // S'il y a des réservations trouvées, passez-les à la vue
            redirectAttributes.addFlashAttribute("reservations", reservations);
        }

        return "redirect:/rechercher-reservation"; // Utilisez la redirection
    }



    
    
    
    
    

   /* @GetMapping("/ajouter-voiture")
    public String afficherform() {
        return "pages/AjouterVoiture"; // Retourne   la vue du formulaire
    }


    /*@PostMapping("/ajouter-voiture")
    public String ajouterVoiture(@RequestParam("numero") String numero,
    		                     @RequestParam("marque") String marque,
                                 @RequestParam("modele") String modele,
                                 @RequestParam("anneeFab") String anneeFab,
                                 @RequestParam("matricule") String matricule,                            
                                 @RequestParam("prixLocation") double prixLocation,
                                 @RequestParam("dispo") String dispo,
                                 Model model) {
        // Créez une nouvelle instance de Voiture avec les données du formulaire
        Voiture voiture = new Voiture();
        voiture.setNumero(numero); 
        voiture.setMarque(marque);
        voiture.setModele(modele);
        voiture.setAnneeFab(anneeFab);
        voiture.setMatricule(matricule);
        voiture.setPrixLocation(prixLocation);
        voiture.setDispo(dispo);

        // Enregistrez la voiture dans la base de données
        Voiture savedVoiture = voitureRepo.save(voiture);

        if (savedVoiture != null) {
            // L'enregistrement a réussi, ajoutez un message de succès à la vue
            model.addAttribute("successMessage", "Voiture a été enregistré avec succès.");
        } else {
            // L'enregistrement a échoué, ajoutez un message d'erreur à la vue
            model.addAttribute("errorMessage", "Une erreur s'est produite lors de l'enregistrement de la voiture.");
        }
        
        return "pages/AjouterVoiture";
    }*/
    
   /* @PostMapping("/ajouter-voiture")
    public String ajouterVoiture(@RequestParam("numero") String numero,
                                 @RequestParam("marque") String marque,
                                 @RequestParam("modele") String modele,
                                 @RequestParam("image") MultipartFile file,
                                 @RequestParam("anneeFab") String anneeFab,
                                 @RequestParam("matricule") String matricule,                            
                                 @RequestParam("prixLocation") double prixLocation,
                                 @RequestParam("dispo") String dispo,
                                 Model model) {
        Voiture voiture = new Voiture();
        voiture.setNumero(numero); 
        voiture.setMarque(marque);
        voiture.setModele(modele);
        try {
            voiture.setImage(file.getBytes());
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Erreur lors de l'enregistrement de l'image");
            return "ajouter-voiture"; // Assurez-vous que cette vue existe
        }
        voiture.setAnneeFab(anneeFab);
        voiture.setMatricule(matricule);
        voiture.setPrixLocation(prixLocation);
        voiture.setDispo(dispo);

        Voiture savedVoiture = voitureRepo.save(voiture);

        if (savedVoiture != null) {
            model.addAttribute("successMessage", "Voiture enregistrée avec succès.");

            // Notification via le pattern Observer
            reservationManager.addNewCar(voiture);

        } else {
            model.addAttribute("errorMessage", "Erreur lors de l'enregistrement de la voiture.");
        }
        
        return "pages/AjouterVoiture";
        
       
    }
    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image");
    }
*/
    
    
    
    @GetMapping("/ajouter-voiture")
    public String afficherform() {
        return "pages/AjouterVoiture"; // Retourne la vue du formulaire
    }

    @PostMapping("/ajouter-voiture")
    public String ajouterVoiture(@RequestParam("numero") String numero,
    		                     @RequestParam("marque") String marque,
                                 @RequestParam("modele") String modele,
                                 @RequestParam("type") String type,
                                 @RequestParam("image") MultipartFile file,
                                 @RequestParam("anneeFab") String anneeFab,
                                 @RequestParam("matricule") String matricule,
                                 @RequestParam("prixLocation") double prixLocation,
                                 @RequestParam("dispo") String dispo,
                                 Model model) {
        // Utiliser la factory pour créer une instance de voiture selon le type
        Voiture car = carFactory.createCar(type);
        car.setNumero(numero); 
        car.setMarque(marque);
        car.setModele(modele);
        car.setAnneeFab(anneeFab);
        car.setMatricule(matricule);
        car.setPrixLocation(prixLocation);
        car.setDispo(dispo);
        try {
            car.setImage(file.getBytes());
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Erreur lors de l'enregistrement de l'image");
            return "pages/AjouterVoiture";
        }

        // Enregistrez la voiture dans la base de données
        Voiture savedVoiture = voitureRepo.save(car);

        if (savedVoiture != null) {
            model.addAttribute("successMessage", "Voiture enregistrée avec succès.");

            // Notification via le pattern Observer
            reservationManager.addNewCar(car);

        } else {
            model.addAttribute("errorMessage", "Erreur lors de l'enregistrement de la voiture.");
        }
        return "pages/AjouterVoiture";
    }
    
    @GetMapping("/details-voiture/{id}")
    public String afficherDetailsVoiture(@PathVariable("id") String id, Model model) {
        Optional<Voiture> voitureOptional = voitureRepo.findById(id);
        if (voitureOptional.isPresent()) {
            Voiture voiture = voitureOptional.get();
            model.addAttribute("voiture", voiture);
            return "pages/details-voiture";
        } else {
            model.addAttribute("errorMessage", "La voiture avec l'ID " + id + " n'a pas été trouvée.");
            return "redirect:/voir-voitures";
        }
    }

    
    

    @GetMapping("/voir-voitures")
    public String afficherVoitures(Model model) {
        // Récupérer tous les managers
    	List<Voiture> voitures = voitureRepo.findAll().stream()
                .filter(voiture -> "Afficher".equals(voiture.getStatut()))
                .collect(Collectors.toList());
        
        // Ajouter la liste des managers au modèle
        model.addAttribute("voitures", voitures);
        return "pages/voir-voitures"; // Retourne la vue affichant la liste des managers
    }
    @GetMapping("/AjouterReservation")
    public String afficherVoiture(Model model) {
        // Récupérer tous les managers
    	List<Voiture> voitures = voitureRepo.findAll().stream()
                .filter(voiture -> "Afficher".equals(voiture.getStatut()))
                .collect(Collectors.toList());
        
        // Ajouter la liste des managers au modèle
        model.addAttribute("voitures", voitures);
        return "pages/AjouterReservation"; // Retourne la vue affichant la liste des managers
    }
    
    
    @GetMapping("/modifier-voiture/{id}")
    public String afficherFormModification(@PathVariable("id") String id, Model model) {
        // Rechercher le manager par son identifiant
        Optional<Voiture> voitureOptional = voitureRepo.findById(id);
        
        if (voitureOptional.isPresent()) {
            // Si le manager est trouvé, l'ajouter au modèle et afficher le formulaire de modification
        	Voiture voiture = voitureOptional.get();
            model.addAttribute("voiture", voiture);
            return "pages/modifier-voiture"; // Retourne la vue du formulaire de modification
        } else {
            // Si aucun manager n'est trouvé, rediriger vers la page des managers avec un message d'erreur
            model.addAttribute("errorMessage", "La voiture avec l'ID " + id + " n'a pas été trouvé.");
            return "redirect:/voir-voiture"; // Redirige vers la liste des managers
        }
    }
    @PostMapping("/modifier-voiture/{id}")
    public String modifierVoiture(@PathVariable("id") String id, @ModelAttribute Voiture modifiedVoiture, Model model) {

    	// Récupérer le manager existant par son identifiant
        Optional<Voiture> voitureOptional = voitureRepo.findById(id);
        
        if (voitureOptional.isPresent()) {
            // Mettre à jour les attributs du manager existant avec les nouvelles valeurs du formulaire
            Voiture existingVoiture = voitureOptional.get();
            existingVoiture.setId(id);
            existingVoiture.setNumero(modifiedVoiture.getNumero());
            existingVoiture.setMarque(modifiedVoiture.getMarque());
            existingVoiture.setModele(modifiedVoiture.getModele());
            existingVoiture.setAnneeFab(modifiedVoiture.getAnneeFab());
            existingVoiture.setMatricule(modifiedVoiture.getMatricule());
            existingVoiture.setPrixLocation(modifiedVoiture.getPrixLocation());
            existingVoiture.setDispo(modifiedVoiture.getDispo());
            
            // Enregistrer les modifications dans la base de données
            voitureRepo.save(existingVoiture);
        } else {
            // Si aucun manager n'est trouvé, rediriger vers la page des managers avec un message d'erreur
            model.addAttribute("errorMessage", "La voiture avec l'ID " + id + " n'a pas été trouvé.");
            return "redirect:/voir-voitures"; // Redirige vers la liste des managers
        }
        
        // Rediriger vers la page des détails du manager mis à jour
        return "redirect:/voir-voitures";
    }

    
    
    @GetMapping("/supprimer-voiture/{id}")
    public String supprimerVoiture(@PathVariable("id") String id, Model model) {
        Optional<Voiture> voitureOptional = voitureRepo.findById(id);

        if (voitureOptional.isPresent()) {
            Voiture voiture = voitureOptional.get();
            // Modifier le statut pour archiver le manager
            voiture.setStatut("Archiver");
            voitureRepo.save(voiture);
            model.addAttribute("successMessage", "La voiture a été archivé avec succès.");
        } else {
            model.addAttribute("errorMessage", "La voiture avec l'ID " + id + " n'a pas été trouvé.");
            return "redirect:/voir-voitures"; // Redirige vers la liste des managers si non trouvé
        }

        return "redirect:/voir-voitures"; // Redirige vers la liste des managers après archivage
    }
    
    
    @GetMapping("/rechercher-voiture")
	public String recherVoiture(Model model) {
        return "pages/rechercher-voiture"; // Retourne la vue du formulaire
    }

    
    
    
   /* @PostMapping("/rechercher-voiture")
    public String rechercherVoitureParmatricule(@RequestParam("matricule") String matricule, Model model, RedirectAttributes redirectAttributes) {
    Voiture voiture = voitureRepo.findOneByMatricule(matricule);
    
    if (voiture != null) {
        // Utiliser addFlashAttribute pour ajouter l'objet client qui persiste après la redirection
        redirectAttributes.addFlashAttribute("voiture", voiture);
        return "redirect:/rechercher-voiture"; // Utilisez la redirection
    } else {
        // Passer le message d'erreur aussi via redirect attributes
        redirectAttributes.addFlashAttribute("errorMessage", "Aucune voiture trouvé avec le Matricule " + matricule);
        return "redirect:/rechercher-voiture";
    }
}*/
    
    
    
    @PostMapping("/rechercher-voiture")
    public String rechercherReservationParNomVoitur(@RequestParam("matricule") String matricule, RedirectAttributes redirectAttributes) {
        List<Voiture> voitures = voitureRepo.findByMatricule(matricule);

        if (voitures.isEmpty()) {
            // S'il n'y a pas de réservations trouvées
            redirectAttributes.addFlashAttribute("errorMessage", "Aucune réservation trouvée pour la voiture nommée '" + matricule + "'");
        } else {
            // S'il y a des réservations trouvées, passez-les à la vue
            redirectAttributes.addFlashAttribute("voitures", voitures);
        }

        return "redirect:/rechercher-voiture"; // Utilisez la redirection
    }
    
    
    @Autowired
    public void ReservationController(ReservationRepo reservationRepo, VoitureRepo voitureRepo) {
        this.reservationRepo = reservationRepo;
        this.voitureRepo = voitureRepo;
    }

    @GetMapping("/download-reservation-pdf/{id}")
    public ResponseEntity<byte[]> downloadReservationPdf(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid ID provided".getBytes());
        }

        Optional<reservation> reservationOpt = reservationRepo.findById(id);
        if (!reservationOpt.isPresent()) {
            System.out.println("Reservation not found for ID: " + id);
            return ResponseEntity.notFound().build();
        }

        reservation reservation = reservationOpt.get();
        String marqueModele = reservation.getNomVoiture();  // Assuming this is in the format "Marque Modele"
        if (marqueModele == null || marqueModele.trim().isEmpty()) {
            System.out.println("Marque and Modele are missing for reservation ID: " + id);
            return ResponseEntity.badRequest().body("Marque and Modele are missing in the reservation".getBytes());
        }

        // Séparez la marque et le modèle pour effectuer la recherche
        String[] parts = marqueModele.split("-", 2);
        if (parts.length < 2) {
            System.out.println("Invalid marque and modele format for reservation ID: " + id);
            return ResponseEntity.badRequest().body("Invalid marque and modele format in the reservation".getBytes());
        }
        String marque = parts[0];
        String modele = parts[1];

        Optional<Voiture> voitureOpt = voitureRepo.findByMarqueAndModele(marque, modele);
        if (!voitureOpt.isPresent()) {
            System.out.println("Car not found for marque: " + marque + " and modele: " + modele);
            return ResponseEntity.notFound().build();
        }

        Voiture voiture = voitureOpt.get();
        return generatePdfResponse(reservation, voiture);
    }

    private ResponseEntity<byte[]> generatePdfResponse(reservation reservation, Voiture voiture) {
        String htmlContent = buildHtmlForReservation(reservation, voiture);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream);
            byte[] pdfBytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "reservation-details.pdf";
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            System.out.println("PDF generated successfully for reservation ID: " + reservation.getId());
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.err.println("Error generating PDF: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private String buildHtmlForReservation(reservation reservation, Voiture voiture) {
        String logoUrl = getClass().getClassLoader().getResource("static/images/Black logo - no background.png").toString();
        String carDiagramUrl = getClass().getClassLoader().getResource("static/images/v.png").toString();

        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
            reservation.getDateDebut().toLocalDate(),
            reservation.getDateFin().toLocalDate()
        );
        double totalPrice = daysBetween * voiture.getPrixLocation();
        double finalPrice = totalPrice -(totalPrice * (reservation.getRemise())/100);

        return "<html><head><title>Contrat de Location</title></head><body>"
        + "<img src='" + logoUrl + "' style='height: 60px; width: auto; display: block; margin: 10px auto;' />"
        + "<h1 style='text-align:center; font-family: Arial, sans-serif; color: #444;'>Contrat de Location - N° " + reservation.getId() + "</h1>"
        + "<table style='width:100%; border-collapse: collapse; margin: 20px 0; font-family: Arial, sans-serif; border: 1px solid #ccc;'>"
        + "<tr><td style='padding: 8px; border: 1px solid #ccc;'><strong>Date:</strong> " + reservation.getDateDebut() + "</td></tr>"
        + "<tr><td style='padding: 8px; border: 1px solid #ccc;'><strong>Le Locataire:</strong> " + reservation.getNomClient() + "</td></tr>"
        + "<tr><td style='padding: 8px; border: 1px solid #ccc;'><strong>Le Propriétaire:</strong> ABDOS CAR</td></tr>"
        + "<tr><td style='padding: 8px; border: 1px solid #ccc;'><strong>Matricule du véhicule:</strong> " + voiture.getMatricule() + "</td></tr>"
        + "<tr><td style='padding: 8px; border: 1px solid #ccc;'><strong>Marque et modèle:</strong> " + voiture.getMarque() + " " + voiture.getModele() + "</td></tr>"
        + "<tr><td style='padding: 8px; border: 1px solid #ccc;'><strong>Date début:</strong> " + reservation.getDateDebut() + "</td></tr>"
        + "<tr><td style='padding: 8px; border: 1px solid #ccc;'><strong>Date fin:</strong> " + reservation.getDateFin() + "</td></tr>"
        + "<tr><td style='padding: 8px; border: 1px solid #ccc;'><strong>Prix location par jour:</strong> " + voiture.getPrixLocation() + " MAD</td></tr>"
        + "<tr><td style='padding: 8px; border: 1px solid #ccc;'><strong>Prix total:</strong> " + totalPrice + " MAD</td></tr>"
        + "<tr><td style='padding: 8px; border: 1px solid #ccc;'><strong>Remise:</strong> " + reservation.getRemise() + " %</td></tr>"
        + "<tr><td style='padding: 8px; border: 1px solid #ccc;'><strong>Price Final:</strong> " + finalPrice + " MAD</td></tr>"


        + "</table>"
        + "<h2 style='text-align:center; font-family: Arial, sans-serif; color: #555;'>À vérifier avant toute location</h2>"
        + "<ul style='font-family: Arial, sans-serif; margin: 20px; list-style-position: inside;'>"
        + "<li>Vérifier l'état du véhicule et le niveau de carburant.</li>"
        + "<li>Confirmer tous les documents du véhicule.</li>"
        + "</ul>"
        + "<h2 style='text-align:center; font-family: Arial, sans-serif; color: #555;'>À remplir au début de la location</h2>"
        + "<p style='font-family: Arial, sans-serif; margin: 20px;'>Indiquez par des croix sur le schéma ci-dessous les dommages visibles sur le véhicule.</p>"
        + "<img src='" + carDiagramUrl + "' style='width:50%; height:200px; display: block; margin: 0 auto;' />"
        + "</body></html>";
    }
}
    
   
