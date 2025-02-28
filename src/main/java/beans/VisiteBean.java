package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named(value = "formulaireVisiteBean")
@SessionScoped
public class VisiteBean implements Serializable {

    private Long idUtilisateur = 1L; // Simule un utilisateur connecté
    private Long idLieu;
    private Date dateVisite;
    private int tempsPasse;
    private String observations;
    private double depenses;
    private boolean afficherFormulaireVisite = false;
    
    private List<VisiteBean> visites = new ArrayList<>(); // Stocker les visites enregistrées

    public Long getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(Long idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public Long getIdLieu() { return idLieu; }
    public void setIdLieu(Long idLieu) { this.idLieu = idLieu; }

    public Date getDateVisite() { return dateVisite; }
    public void setDateVisite(Date dateVisite) { this.dateVisite = dateVisite; }

    public int getTempsPasse() { return tempsPasse; }
    public void setTempsPasse(int tempsPasse) { this.tempsPasse = tempsPasse; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    public double getDepenses() { return depenses; }
    public void setDepenses(double depenses) { this.depenses = depenses; }

    public boolean isAfficherFormulaireVisite() { return afficherFormulaireVisite; }
    
    public List<VisiteBean> getVisites() { return visites; }

    public void afficherFormulaireVisite() {
        afficherFormulaireVisite = true;
    }

    public void sauvegarderVisite() {
        // Simuler un enregistrement dans la liste
        visites.add(new VisiteBean(idUtilisateur, idLieu, dateVisite, tempsPasse, observations, depenses));

        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Visite enregistrée avec succès", null));

        afficherFormulaireVisite = false; // Cacher le formulaire après enregistrement
    }

    public VisiteBean() {}

    public VisiteBean(Long idUtilisateur, Long idLieu, Date dateVisite, int tempsPasse, String observations, double depenses) {
        this.idUtilisateur = idUtilisateur;
        this.idLieu = idLieu;
        this.dateVisite = dateVisite;
        this.tempsPasse = tempsPasse;
        this.observations = observations;
        this.depenses = depenses;
    }
}
