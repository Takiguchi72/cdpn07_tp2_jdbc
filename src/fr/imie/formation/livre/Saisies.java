package fr.imie.formation.livre;

import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

import fr.imie.formation.constant.ActionsEnum;
import fr.imie.formation.constant.LivreEnum;

public class Saisies {
    /** Le scanner pour effectuer des saisies utilisateur. */
    private static final Scanner scanner = new Scanner( System.in );

    /**
     * Effectue la saisie d'une date au format yyyy-MM-dd.
     * 
     * @return
     */
    public static Date saisirDate() {
        Date result = null;

        System.out.println( "Veuillez saisir l'attribut suivant : date_parution (aaaa-mm-jj)" );
        String saisie = scanner.next();

        if ( !Pattern.matches( "^[0-9]{4}(-[0-9]{2}){2}$", saisie ) ) {
            System.err.println( "Veuillez saisir une date au format 'aaaa-mm-jj' !" );
            result = saisirDate();
        } else {
            try {
                result = Date.valueOf( saisie );
            } catch ( final IllegalArgumentException ex ) {
                System.err.println( "Date incorrecte ! (" + saisie + ")" );
                result = saisirDate();
            }
        }

        return result;
    }

    public static String saisirString( final String pChampASaisir ) {
        System.out.println( "Veuillez saisir l'attribut suivant : " + pChampASaisir );
        return scanner.next();
    }

    private static boolean saisieBinaire( final String pQuestion ) {
        int saisie = 1;
        boolean result = true;

        System.out.println( pQuestion + "\n  1 : Oui\n  0 : Non" );

        saisie = saisirNombre();

        if ( saisie != 0 && saisie != 1 ) {
            System.err.println( "Erreur de saisie !" );
            result = saisieBinaire( pQuestion );
        } else {
            result = saisie == 1 ? true : false;
        }

        return result;
    }

    public static boolean saisirContinuer() {
        return saisieBinaire( "Voulez vous réaliser une autre opération ?" );
    }

    public static boolean saisirAutreCritere() {
        return saisieBinaire( "Voulez vous ajouter un nouveau critère dans votre recherche ?" );
    }

    /**
     * Effectue la saisie de l'action.
     * 
     * @return Le numéro correspondant à l'action voulue.
     */
    public static int saisirAction() {
        int saisie = 0;

        saisie = saisirNombre();

        if ( saisie < 0 || saisie >= ActionsEnum.values().length ) {
            System.err.println( "Veuillez saisir un nombre entre 0 et " + ( ActionsEnum.values().length - 1 ) );
            saisie = saisirAction();
        }

        return saisie;
    }

    /**
     * Effectue une saisie d'un nombre.
     * 
     * @return Le nombre saisi.
     */
    public static int saisirNombre() {
        int saisie = 0;

        try {
            saisie = scanner.nextInt();
        } catch ( final InputMismatchException ex ) {
            // Vidage du scanner
            scanner.next();

            System.err.println( "Veuillez saisir un nombre !" );
            saisie = saisirNombre();
        }

        return saisie;
    }

    /**
     * Effectue la saisie d'un critère de recherche.
     * 
     * @return L'indice de critère saisi.
     */
    public static LivreEnum saisirCritereRecherche() {
        // Saisie du critère de recherche
        int indiceSaisi = saisirNombre();

        if ( indiceSaisi < 0 || indiceSaisi >= LivreEnum.values().length ) {
            System.err.println( "Veuillez saisir un nombre entre 0 et " + ( LivreEnum.values().length - 1 ) );
            return saisirCritereRecherche();
        }

        return LivreEnum.values()[indiceSaisi];
    }

    /**
     * Effectue la saisie de la valeur du critere de recherche en paramètre.
     * 
     * @param pCritere
     * @return
     */
    public static String saisirValeurCritereRecherche( final LivreEnum pCritere ) {
        System.out.println( "Veuillez saisir la valeur pour le critere suivant : " + pCritere.lib() );
        return scanner.next();
    }
}
