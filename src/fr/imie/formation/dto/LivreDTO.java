package fr.imie.formation.dto;

import java.sql.Date;

/**
 * The Class LivreDTO.
 */
public class LivreDTO {

    /** The id. */
    private int    id;

    /** The nom. */
    private String nom;

    /** The prenom. */
    private String prenom;

    /** The date parution. */
    private Date   dateParution;

    /**
     * Initialise un nouveau livre à partir des paramètres.
     * 
     * @param pId
     *            the id
     * @param pNom
     *            the nom
     * @param pPrenom
     *            the prenom
     * @param pDateParution
     *            the date parution
     */
    public LivreDTO( final int pId, final String pNom, final String pPrenom, final Date pDateParution ) {
        id = pId;
        nom = pNom;
        prenom = pPrenom;
        dateParution = pDateParution;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId( int id ) {
        this.id = id;
    }

    /**
     * Gets the nom.
     * 
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets the nom.
     * 
     * @param nom
     *            the new nom
     */
    public void setNom( String nom ) {
        this.nom = nom;
    }

    /**
     * Gets the prenom.
     * 
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Sets the prenom.
     * 
     * @param prenom
     *            the new prenom
     */
    public void setPrenom( String prenom ) {
        this.prenom = prenom;
    }

    /**
     * Gets the date parution.
     * 
     * @return the date parution
     */
    public Date getDateParution() {
        return dateParution;
    }

    /**
     * Sets the date parution.
     * 
     * @param dateParution
     *            the new date parution
     */
    public void setDateParution( Date dateParution ) {
        this.dateParution = dateParution;
    }

    @Override
    public String toString() {
        return "LivreDTO [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateParution=" + dateParution + "]";
    }

}
