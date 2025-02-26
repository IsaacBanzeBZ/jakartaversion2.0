package beans;

import business.SessionManager;
import business.UtilisateurEntrepriseBean;
import entities.Utilisateur;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import org.mindrot.jbcrypt.BCrypt;

@Named("profilBean")
@SessionScoped
public class ProfilBean implements Serializable {

    private String email;
    private String username;
    private String description;
    private String newPassword;
    private String currentPassword;

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    @Inject
    private SessionManager sessionManager;

    // Getters et Setters
    public String getEmail() {
        return email;
    }
    

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    
    @PostConstruct
    public void init() {
        chargerProfil();
    }

    // Méthode pour charger les informations du profil
    public void chargerProfil() {
        String userEmail = sessionManager.getValueFromSession("user");
        if (userEmail != null) {
            Utilisateur utilisateur = utilisateurEntrepriseBean.trouverUtilisateurParEmail(userEmail);
            if (utilisateur != null) {
                this.email = utilisateur.getEmail();
                this.username = utilisateur.getUsername();
                this.description = utilisateur.getDescription();
            }
        }
    }

    // Méthode pour mettre à jour le profil
    public String mettreAJourProfil() {
        String userEmail = sessionManager.getValueFromSession("user");
        if (userEmail != null) {
            Utilisateur utilisateur = utilisateurEntrepriseBean.trouverUtilisateurParEmail(userEmail);
            if (utilisateur != null) {
                utilisateur.setDescription(this.description);
                if (newPassword != null && !newPassword.isEmpty()) {
                    if (BCrypt.checkpw(currentPassword, utilisateur.getPassword())) {
                        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                        utilisateur.setPassword(hashedPassword);
                        utilisateurEntrepriseBean.mettreAJourUtilisateur(utilisateur);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profil mis à jour avec succès.", null));
                        return "/index?faces-redirect=true";
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ancien mot de passe incorrect.", null));
                        return null;
                    }
                }
                utilisateurEntrepriseBean.mettreAJourUtilisateur(utilisateur);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profil mis à jour avec succès.", null));
                return "/index?faces-redirect=true";
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la mise à jour du profil.", null));
        return null;
    }
}
