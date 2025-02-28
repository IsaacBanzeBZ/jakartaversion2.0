package beans;

import business.LieuEntrepriseBean;
import entities.Lieu;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


@Named("lieuBean")
@SessionScoped
public class LieuBean implements Serializable {
    private int idLieu = 0;
    private String nom;
    private String description;
    private double latitude;
    private double longitude;
    private String weatherMessage;
     List<Lieu> lieux;
    private int selectedLieu;

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    // Récupérer la liste des lieux depuis la base de données
    public List<Lieu> getListeLieux() {
        return lieuEntrepriseBean.listerTousLesLieux();
    }

    public List<Lieu> getLieux() {
        this.lieux=lieuEntrepriseBean.listerTousLesLieux();
        return lieux;
    }

    public void setLieux() {
        this.lieux =lieuEntrepriseBean.listerTousLesLieux();
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
    public int getIdLieu() {
        
        return idLieu;
    }
    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu; 
    }
    

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom; 
    }

    public String getDescription() { 
        return description; 
    }
    public void setDescription(String description) { 
        this.description = description; 
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) { 
        this.latitude = latitude; 
    }

    public double getLongitude() {
        return longitude; 
    }
    public void setLongitude(double longitude) { 
        this.longitude = longitude; 
    }

    public int getSelectedLieu() {
        return selectedLieu;
    }

    public void setSelectedLieu(int selectedLieu) {
        this.selectedLieu = selectedLieu;
    }
    
    
    
     public void fetchWeatherMessage(Lieu l) {

    if (l != null) {
    // Appel au service web pour obtenir les données météorologiques

    String serviceURL = "http://192.168.180.203:8080/j-weather/webapi/JarkartaWeather?latitude="+ l.getLatitude() + "&longitude=" + l.getLongitude();
    
    Client client = ClientBuilder.newClient();
    String response = client.target(serviceURL)
    .request(MediaType.TEXT_PLAIN)
    .get(String.class);

    // Enregistrement du message météo dans la variable weatherMessage
    this.weatherMessage =response;
}

}

    public void updateWeatherMessage(AjaxBehaviorEvent event) {

    Lieu lieu=lieuEntrepriseBean.trouverLieuParId(selectedLieu);
    this.fetchWeatherMessage(lieu);
    }

    public String getWeatherMessage() {
    return weatherMessage;
    }

    public void setWeatherMessage(String weatherMessage) {
        this.weatherMessage = weatherMessage;
    }
}

