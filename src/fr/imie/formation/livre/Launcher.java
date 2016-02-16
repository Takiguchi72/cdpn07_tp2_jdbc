package fr.imie.formation.livre;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.imie.formation.constant.ActionsEnum;
import fr.imie.formation.constant.LivreEnum;
import fr.imie.formation.dao.ConnexionPostgres;
import fr.imie.formation.dao.LivreDAO;
import fr.imie.formation.dto.CritereRechercheLivreDTO;
import fr.imie.formation.dto.LivreDTO;

public class Launcher {
    private static Connection connexion;

    private static LivreDAO   daoLivres;

    /**
     * Initialise les attribut de classe.
     */
    static {
        connexion = ConnexionPostgres.getPreparedInstance();
        daoLivres = new LivreDAO();
        daoLivres.setConnexion( connexion );
    }

    /**
     * Point d'entrée du programme.
     * 
     * @param args
     */
    public static void main( String[] args ) {
        boolean continuer = true;
        ActionsEnum action;

        do {
            printMainMenu();

            action = ActionsEnum.values()[Saisies.saisirAction()];

            switch ( action ) {
                case RECHERCHER:
                    rechercherLivre();
                    break;
                case AJOUTER:
                    ajouterLivre();
                    break;
                case MODIFIER:
                    modifierLivre();
                    break;
                case SUPPRIMER:
                    supprimerLivre();
                    break;
                case CONSULTER_TOUS:
                    consulterTous();
                    break;
                default:
                    System.err.println( "WTF man ?!?" );
                    break;
            }

            continuer = Saisies.saisirContinuer();
        } while ( continuer );

        System.out.println( "Aurevoir ! :D" );

        try {
            connexion.close();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    private static void rechercherLivre() {
        /*
         * Création d'une map pour indiquer si le critère sélectionné n'a pas
         * déjà été saisi.
         */
        Map<LivreEnum, Boolean> criteresRecherche = new HashMap<LivreEnum, Boolean>();
        criteresRecherche.put( LivreEnum.ID, false );
        criteresRecherche.put( LivreEnum.NOM, false );
        criteresRecherche.put( LivreEnum.PRENOM, false );
        criteresRecherche.put( LivreEnum.DATE_PARUTION, false );

        List<CritereRechercheLivreDTO> listeCriteres = new LinkedList<CritereRechercheLivreDTO>();

        // Ajout des critères de recherche
        boolean ajouterCritere = true;
        while ( listeCriteres.size() <= 4 && ajouterCritere ) {
            printSearchMenu();
            LivreEnum critereChoisi = Saisies.saisirCritereRecherche();

            if ( criteresRecherche.get( critereChoisi ) ) {
                System.err.println( "Vous avez déjà défini un critère de recherche sur cette propriété !" );
            } else {
                // Saisie de la valeur et ajout du critere à la liste
                listeCriteres.add( new CritereRechercheLivreDTO( critereChoisi,
                        Saisies.saisirValeurCritereRecherche( critereChoisi ) ) );

                criteresRecherche.put( critereChoisi, true );

                ajouterCritere = Saisies.saisirAutreCritere();
            }
        }

        List<LivreDTO> listeLivres = daoLivres.search( listeCriteres );

        if ( listeLivres.isEmpty() ) {
            System.out.println( "Aucun livre n'a été trouvé..." );
        } else {
            System.out.println( "Voici les livres trouvés en base :" );
            for ( LivreDTO livreDTO : listeLivres ) {
                System.out.println( livreDTO.toString() );
            }
        }
    }

    /**
     * Effectue la saisie du nouveau livre et l'ajoute en base.
     */
    private static void ajouterLivre() {
        final String nom = Saisies.saisirString( "Nom" );

        final String prenom = Saisies.saisirString( "Prénom" );

        final Date dateParution = Saisies.saisirDate();

        System.out.println( "Livre ajouté : "
                + daoLivres.insert( new LivreDTO( 0, nom, prenom, dateParution ) ).toString() );
    }

    /**
     * Effectue la saisie de la modification d'un livre.
     */
    private static void modifierLivre() {
        LivreDTO livreAModifier = null;

        // On affiche les livres en base et on récupère le nombre de livres
        int nombreLivres = consulterTous();

        // S'il y a au moins 1 livre, on effectue la saisie
        if ( nombreLivres > 0 ) {
            do {

                System.out.println( "Veuillez saisir l'id du livre à modifier :" );
                int idLivreAModifier = Saisies.saisirNombre();

                livreAModifier = daoLivres.read( idLivreAModifier );

                if ( livreAModifier == null ) {
                    System.err.println( "Il n'y a pas de livre ayant pour id '" + idLivreAModifier + "'" );
                }
            } while ( livreAModifier == null );

            System.out.println( "Livre à modifier : " + livreAModifier.toString() );

            // Saisies
            final String newNom = Saisies.saisirString( "Nom" );

            final String newPrenom = Saisies.saisirString( "Prénom" );

            final Date newDateParution = Saisies.saisirDate();

            // Binding
            livreAModifier.setNom( newNom );
            livreAModifier.setPrenom( newPrenom );
            livreAModifier.setDateParution( newDateParution );

            // Update + affichage final
            System.out.println( "Livre modifié : " + daoLivres.update( livreAModifier ) );
        } // fin if
    }

    /**
     * Effectue la saisie pour supprimer un livre.
     */
    private static void supprimerLivre() {
        LivreDTO livreASupprimer = null;

        // On affiche les livres en base et on récupère le nombre de livres
        int nombreLivres = consulterTous();

        // S'il y a au moins 1 livre, on effectue la saisie
        if ( nombreLivres > 0 ) {
            do {
                System.out.println( "Veuillez saisir l'id du livre à supprimer :" );
                int idLivreASupprimer = Saisies.saisirNombre();

                livreASupprimer = daoLivres.read( idLivreASupprimer );

                if ( livreASupprimer == null ) {
                    System.err.println( "Il n'y a pas de livre ayant pour id '" + idLivreASupprimer + "'" );
                }
            } while ( livreASupprimer == null );

            boolean resultSuppr = daoLivres.delete( livreASupprimer );

            if ( resultSuppr ) {
                System.out.println( "Le livre a été supprimé !" );
            }
        }
    }

    /**
     * Affiche les livres contenus en base.
     * 
     * @return Le nombre de livres contenu en base.
     */
    private static int consulterTous() {
        List<LivreDTO> listeLivres = daoLivres.getAll();

        if ( listeLivres.isEmpty() ) {
            System.out.println( "Il n'y a pas de livres !" );
        } else {
            System.out.println( "Liste des livres :" );
            for ( LivreDTO livreDTO : listeLivres ) {
                System.out.println( livreDTO.toString() );
            }
            System.out.println();
        }

        return listeLivres.size();
    }

    /**
     * Affiche le menu du programme.
     */
    private static void printMainMenu() {
        final StringBuilder builder = new StringBuilder();
        builder.append( " --- Gestion des livres :) ---\n\n" );
        builder.append( "Que désirez vous faire ?\n" );
        for ( int i = 0; i < ActionsEnum.values().length; i++ ) {
            builder.append( "  " ).append( i ).append( " : " ).append( ActionsEnum.values()[i].val() ).append( "\n" );
        }
        System.out.println( builder.toString() );
    }

    /**
     * Affiche le menu de recherche
     */
    private static void printSearchMenu() {
        final StringBuilder builder = new StringBuilder();
        builder.append( "Sur quel critère voulez vous effectuer la recherche ?\n" );
        for ( int i = 0; i < LivreEnum.values().length; i++ ) {
            builder.append( "(" ).append( i ).append( ") " ).append( LivreEnum.values()[i].lib() ).append( "\n" );
        }
        System.out.println( builder.toString() );
    }
}
