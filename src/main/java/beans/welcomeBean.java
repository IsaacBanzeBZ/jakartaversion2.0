/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import business.SessionManager;
import business.UtilisateurEntrepriseBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import entities.Utilisateur;


@Named("welcomeBean")
@RequestScoped
public class welcomeBean {
    private String nom;
    private String message;
    private String email;
    private String password;
    
    @Inject
    private UtilisateurEntrepriseBean UtilisateurEntrepriseBean;
    
    @Inject
    private SessionManager sessionManager;
    
    @Inject
    private ProfilBean profilBean;

    // Getter pour 'nom'
    public String getNom() {
        return nom;
    }

    // Setter pour 'nom' (obligatoire pour que JSF puisse modifier la valeur)
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter pour 'message'
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    // Méthode pour afficher le message
    public void afficherMessage() {
        if (nom != null && !nom.trim().isEmpty()) {
            message = "Selamat datang, " + nom + "!";
        } else {
            message = "Veuillez entrer votre nom.";
        }
    }
    
    public String sAuthentifier() {
    Utilisateur utilisateur = UtilisateurEntrepriseBean.authentifier(email, password);
    FacesContext context = FacesContext.getCurrentInstance();

        if (utilisateur != null) {
            
            sessionManager.createSession("user", email);
            // Redirection après connexion réussie
            return "home?faces-redirect=true";
        } 
        else {
            // Message d'erreur pour des identifiants incorrects
            this.message = "Email ou mot de passe incorrect.";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return null;
        }
    }

    
    
}