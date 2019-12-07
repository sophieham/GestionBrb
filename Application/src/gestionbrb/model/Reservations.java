package gestionbrb.model;


import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Réservation d'une table à une date et heure donnée
 * @author Sophie
 *
 */
public class Reservations {
    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty numTel;
    private final StringProperty date;
    private final StringProperty heure;
    private final IntegerProperty nbCouverts;
    private final StringProperty demandeSpe;
    private final IntegerProperty idReservation;

    /**
     * Default constructor.
     */
    public Reservations() {
        this(0, null, null, null, null, null, 0, null);
    }

    /**
     * Constructeur d'une réservation
     * 
     * @param nom nom de la personne qui reserve 
     * @param prenom son prénom
     * @param numeroTel son numéro de téléphone (au format +33xxx ou 0xxx)
     * @param date la date de réservation
     * @param heure l'heure de réservation
     * @param demandeSpe les demande spéciales (si il y en a)
     * @param nbCouverts le nombre de couverts
     * @param idReservation l'identifiant unique de la réservation
     */
    public Reservations(int idReservation, String nom, String prenom, String numTel, String date, String heure, int nbCouverts, String demandeSpe) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.numTel = new SimpleStringProperty(numTel);
        this.date = new SimpleStringProperty(date);
        this.heure = new SimpleStringProperty(heure);
        this.demandeSpe = new SimpleStringProperty(demandeSpe);
        this.nbCouverts = new SimpleIntegerProperty(nbCouverts);
        this.idReservation = new SimpleIntegerProperty(idReservation);

    }

    //Getters & Setters pour toutes les variables
    
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
    public int getID() {
        return idReservation.get();
    }

    public void setID(int id) {
        this.idReservation.set(id);
    }

    public IntegerProperty idProperty() {
        return idReservation;
    }

}