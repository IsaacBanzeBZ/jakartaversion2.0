package beans;

import business.LieuEntrepriseBean;
import entities.Lieu;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

@Named("lieuBean")
@SessionScoped
public class LieuBean implements Serializable {
    private int idLieu = 0;
    private String nom;
    private String description;
    private double latitude;
    private double longitude;

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    // Récupérer la liste des lieux depuis la base de données
    public List<Lieu> getListeLieux() {
        return lieuEntrepriseBean.listerTousLesLieux();
    }

    // Ajouter ou Modifier un lieu
    public void enregistrerLieu() {
        if (idLieu == 0) {
            lieuEntrepriseBean.ajouterLieuEntreprise(nom, description, latitude, longitude);
        } else {
            lieuEntrepriseBean.modifierLieuEntreprise(idLieu, nom, description, latitude, longitude);
        }
        resetForm();
    }

    // Supprimer un lieu
    public void supprimerLieu(int id) {
        lieuEntrepriseBean.supprimerLieuEntreprise(id);
    }

    // Préparer la modification d'un lieu
    public void preparerModification(Lieu lieu) {
        this.idLieu = lieu.getId();
        this.nom = lieu.getNom();
        this.description = lieu.getDescription();
        this.latitude = lieu.getLatitude();
        this.longitude = lieu.getLongitude();
    }

    // Réinitialiser le formulaire
    private void resetForm() {
        this.idLieu = 0;
        this.nom = "";
        this.description = "";
        this.latitude = 0;
        this.longitude = 0;
    }

    // Getters et Setters
    public int getIdLieu() { return idLieu; }
    public void setIdLieu(int idLieu) { this.idLieu = idLieu; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}