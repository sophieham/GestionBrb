package gestionbrb.model;


import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// TODO: Auto-generated Javadoc
/**
 * Réservation d'une table à une date et heure donnée.
 *
 * @author Sophie
 */
public class Reservations {
    
    /** The nom. */
    private final StringProperty nom;
    
    /** The prenom. */
    private final StringProperty prenom;
    
    /** The num tel. */
    private final StringProperty numTel;
    
    /** The date. */
    private final StringProperty date;
    
    /** The heure. */
    private final StringProperty heure;
    
    /** The nb couverts. */
    private final IntegerProperty nbCouverts;
    
    /** The demande spe. */
    private final StringProperty demandeSpe;
    
    /** The id reservation. */
    private final IntegerProperty idReservation;

    /**
     * Default constructor.
     */
    public Reservations() {
        this(0, null, null, null, null, null, 0, null);
    }

    /**
     * Constructeur d'une réservation.
     *
     * @param idReservation l'identifiant unique de la réservation
     * @param nom nom de la personne qui reserve
     * @param prenom son prénom
     * @param numTel the num tel
     * @param date la date de réservation
     * @param heure l'heure de réservation
     * @param nbCouverts le nombre de couverts
     * @param demandeSpe les demande spéciales (si il y en a)
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
    
    /**
     * Gets the nom.
     *
     * @return the nom
     */
    public String getNom() {
        return nom.get();
    }

    /**
     * Sets the nom.
     *
     * @param nom the new nom
     */
    public void setNom(String nom) {
        this.nom.set(nom);
    }

    /**
     * Nom property.
     *
     * @return the string property
     */
    public StringProperty nomProperty() {
        return nom;
    }

    /**
     * Gets the prenom.
     *
     * @return the prenom
     */
    public String getPrenom() {
        return prenom.get();
    }

    /**
     * Sets the prenom.
     *
     * @param prenom the new prenom
     */
    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    /**
     * Prenom property.
     *
     * @return the string property
     */
    public StringProperty prenomProperty() {
        return prenom;
    }
    
    /**
     * Gets the num tel.
     *
     * @return the num tel
     */
    public String getNumTel() {
        return numTel.get();
    }

    /**
     * Sets the num tel.
     *
     * @param numTel the new num tel
     */
    public void setNumTel(String numTel) {
        this.numTel.set(numTel);
    }

    /**
     * Num tel property.
     *
     * @return the string property
     */
    public StringProperty numTelProperty() {
        return numTel;
    }
    
    /**
     * Gets the date.
     *
     * @return the date
     */
    public String getDate() {
        return date.get();
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(LocalDate date) {
        this.date.set(date.toString());
    }

    /**
     * Date property.
     *
     * @return the string property
     */
    public StringProperty dateProperty() {
        return date;
    }

    /**
     * Gets the heure.
     *
     * @return the heure
     */
    public String getHeure() {
        return heure.get();
    }

    /**
     * Sets the heure.
     *
     * @param heure the new heure
     */
    public void setHeure(String heure) {
        this.heure.set(heure);
    }

    /**
     * Heure property.
     *
     * @return the string property
     */
    public StringProperty heureProperty() {
        return heure;
    }

    /**
     * Gets the nb couverts.
     *
     * @return the nb couverts
     */
    public int getNbCouverts() {
        return nbCouverts.get();
    }

    /**
     * Sets the nb couverts.
     *
     * @param nbCouverts the new nb couverts
     */
    public void setNbCouverts(int nbCouverts) {
        this.nbCouverts.set(nbCouverts);
    }

    /**
     * Nb couverts property.
     *
     * @return the integer property
     */
    public IntegerProperty nbCouvertsProperty() {
        return nbCouverts;
    }
    
    /**
     * Gets the demande spe.
     *
     * @return the demande spe
     */
    public String getDemandeSpe() {
        return demandeSpe.get();
    }

    /**
     * Sets the demande spe.
     *
     * @param demandeSpe the new demande spe
     */
    public void setDemandeSpe(String demandeSpe) {
        this.demandeSpe.set(demandeSpe);
    }

    /**
     * Demande spe property.
     *
     * @return the string property
     */
    public StringProperty demandeSpeProperty() {
        return demandeSpe;
    }
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getID() {
        return idReservation.get();
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setID(int id) {
        this.idReservation.set(id);
    }

    /**
     * Id property.
     *
     * @return the integer property
     */
    public IntegerProperty idProperty() {
        return idReservation;
    }

}