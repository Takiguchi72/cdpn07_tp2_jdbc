package fr.imie.formation.constant;

public enum LivreEnum {
    ID( "livre_id", "Identifiant" ),
    NOM( "nom", "Nom" ),
    PRENOM( "prenom", "Prénom" ),
    DATE_PARUTION( "date_parution", "Date de parution" );

    private String value;
    private String libelle;

    private LivreEnum( final String pValue, final String pLibelle ) {
        value = pValue;
        libelle = pLibelle;
    }

    public String val() {
        return value;
    }

    public String lib() {
        return libelle;
    }
}
