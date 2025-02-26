package business;

import entities.Lieu;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class LieuEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void ajouterLieuEntreprise(String nom, String description, double latitude, double longitude) {
        Lieu lieu = new Lieu(nom, description, longitude, latitude);
        em.persist(lieu);
    }

    @Transactional
    public void modifierLieuEntreprise(int id, String nom, String description, double latitude, double longitude) {
        Lieu lieu = em.find(Lieu.class, id);
        if (lieu != null) {
            lieu.setNom(nom);
            lieu.setDescription(description);
            lieu.setLatitude(latitude);
            lieu.setLongitude(longitude);
            em.merge(lieu);
        }
    }

    @Transactional
    public void supprimerLieuEntreprise(int id) {
        Lieu lieu = em.find(Lieu.class, id);
        if (lieu != null) {
            em.remove(lieu);
        }
    }

    public List<Lieu> listerTousLesLieux() {
        return em.createQuery("SELECT L FROM Lieu L", Lieu.class).getResultList();
    }

    public Lieu trouverLieuParId(int id) {
        return em.find(Lieu.class, id);
    }
}