package gestionbrb.model;


import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a reservation.
 *
 * @author Marco Jakob
 */
public class Reservations {

    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty numTel;
    private final StringProperty date;
    private final StringProperty heure;
    private final IntegerProperty nbCouverts;
    private final StringProperty demandeSpe;

    /**
     * Default constructor.
     */
    public Reservations() {
        this(null, null, null, 0);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param nom
     * @param date
     * @param heure
     */
    public Reservations(String nom, String date, String heure, int nbCouverts) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty("");
        this.numTel = new SimpleStringProperty("");
        this.date = new SimpleStringProperty(date);
        this.heure = new SimpleStringProperty(heure);
        this.demandeSpe = new SimpleStringProperty("");
        this.nbCouverts = new SimpleIntegerProperty(nbCouverts);

    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public StringProperty prenomProperty() {
        return prenom;
    }
    
    public String getNumTel() {
        return numTel.get();
    }

    public void setNumTel(String numTel) {
        this.numTel.set(numTel);
    }

    public StringProperty numTelProperty() {
        return numTel;
    }
    
    public String getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date.toString());
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getHeure() {
        return heure.get();
    }

    public void setHeure(String heure) {
        this.heure.set(heure);
    }

    public StringProperty heureProperty() {
        return heure;
    }

    public int getNbCouverts() {
        return nbCouverts.get();
    }

    public void setNbCouverts(int nbCouverts) {
        this.nbCouverts.set(nbCouverts);
    }

    public IntegerProperty nbCouvertsProperty() {
        return nbCouverts;
    }
    public String getDemandeSpe() {
        return demandeSpe.get();
    }

    public void setDemandeSpe(String demandeSpe) {
        this.demandeSpe.set(demandeSpe);
    }

    public StringProperty demandeSpeProperty() {
        return demandeSpe;
    }

}