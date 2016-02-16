package fr.imie.formation.constant;

public enum ActionsEnum {
    RECHERCHER( "Rechercher un livre" ),
    AJOUTER( "Ajouter un livre" ),
    MODIFIER( "Modifier un livre" ),
    SUPPRIMER( "Supprimer un livre" ),
    CONSULTER_TOUS( "Consulter tous les livres" ),
    SORTIR( "Sortir" );

    private String valeur;

    private ActionsEnum( final String pValeur ) {
        valeur = pValeur;
    }

    public String val() {
        return valeur;
    }
}
