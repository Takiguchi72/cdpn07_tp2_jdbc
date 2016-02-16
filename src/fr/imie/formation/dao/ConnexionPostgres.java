package fr.imie.formation.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnexionPostgres {
    /**
     * Create a prepared connection to the postgresql database
     * 
     * @return The preared connection [sql.Connection]
     */
    public static Connection getPreparedInstance()
    {
        // Localisation du driver JDBC
        try {
            Class.forName( "org.postgresql.Driver" );
        } catch ( Exception e ) {
            System.out.println( "Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!" );
            e.printStackTrace();
        }// Fin catch
         // Création d'un objet de type Connection
        Connection theConnection = null;
        try {
            // Connexion à la basse
            theConnection = DriverManager.getConnection(
                    new StringBuilder( "jdbc:postgresql://" ).append( DonneesConnexion.HOST ).append( ":" )
                            .append( DonneesConnexion.PORT ).append( "/" ).append( DonneesConnexion.BASE ).toString(),
                    DonneesConnexion.LOGIN,
                    DonneesConnexion.PSWD );
        } catch ( Exception e ) {
            System.out.println( "Erreur lors de la connexion à la base de donnée :\n" + e );
        }// Fin catch
        return theConnection;
    }// Fin getPreparedInstance()
}