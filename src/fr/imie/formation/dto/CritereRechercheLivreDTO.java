package fr.imie.formation.dto;

import fr.imie.formation.constant.LivreEnum;

/**
 * The Class CritereRechercheLivreDTO.
 */
public class CritereRechercheLivreDTO {

    /** The champ. */
    private LivreEnum champ;

    /** The valeur. */
    private String    valeur;

    /** Constructeur privé pour éviter de pouvoir l'utiliser. */
    private CritereRechercheLivreDTO() {
    }

    /**
     * Initialise un nouveau critère de recherche de livre à partir des
     * paramètres.
     * 
     * @param pChamp
     *            the champ
     * @param pValeur
     *            the valeur
     */
    public CritereRechercheLivreDTO( final LivreEnum pChamp, final String pValeur ) {
        champ = pChamp;
        valeur = pValeur;
    }

    /**
     * Gets the champ.
     * 
     * @return the champ
     */
    public LivreEnum getChamp() {
        return champ;
    }

    /**
     * Sets the champ.
     * 
     * @param champ
     *            the new champ
     */
    public void setChamp( LivreEnum champ ) {
        this.champ = champ;
    }

    /**
     * Retourne le valeur.
     * 
     * @return Le valeur
     */
    public String getValeur() {
        return valeur;
    }

    /**
     * Modifie le valeur.
     * 
     * @param valeur
     *            Le nouveau valeur
     */
    public void setValeur( String valeur ) {
        this.valeur = valeur;
    }
}
