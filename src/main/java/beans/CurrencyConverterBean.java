package beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("currencyConverterBean") // Nom du bean pour JSF
@RequestScoped // Scope du bean
public class CurrencyConverterBean {
    private double dollars; // Montant en dollars
    private double rupiah; // Montant en roupies indonésiennes
    private static final double EXCHANGE_RATE = 15000; // Taux de conversion fixe

    // Getter et setter pour 'dollars'
    public double getDollars() {
        return dollars;
    }

    public void setDollars(double dollars) {
        this.dollars = dollars;
    }

    // Getter pour 'rupiah' (pas de setter car il est calculé)
    public double getRupiah() {
        return rupiah;
    }

    // Méthode pour convertir les dollars en roupies
    public void convert() {
        if (dollars > 0) {
            rupiah = dollars * EXCHANGE_RATE;
        } else {
            rupiah = 0; // Si le montant est négatif ou nul
        }
    }
}