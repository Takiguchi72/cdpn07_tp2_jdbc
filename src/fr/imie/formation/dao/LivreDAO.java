package fr.imie.formation.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.imie.formation.dto.CritereRechercheLivreDTO;
import fr.imie.formation.dto.LivreDTO;

public class LivreDAO extends DAO<LivreDTO, CritereRechercheLivreDTO> {
    /** Requête pour la récupération d'un livre en base. */
    private static final String SQL_READ    = "SELECT * FROM livre WHERE livre_id=?;";
    /** Requête pour l'ajout d'un livre en base. */
    private static final String SQL_INSERT  = "INSERT INTO livre (nom, prenom, date_parution) VALUES (?, ?, ?)";
    /** Requête pour la modification d'un livre en base. */
    private static final String SQL_UPDATE  = "UPDATE livre SET nom=?, prenom=?, date_parution=? WHERE livre_id=?;";
    /** Requête pour la suppression d'un livre en base. */
    private static final String SQL_DELETE  = "DELETE FROM livre WHERE livre_id=?;";
    /** Requête pour la récupération de tous les livres en base. */
    private static final String SQL_GET_ALL = "SELECT * FROM livre ORDER BY livre_id;";
    /** Requête poru la recherche d'un livre. */
    private static final String SQL_SEARCH  = "SELECT * FROM livre ";

    @Override
    public LivreDTO read( int pId ) {
        LivreDTO result = null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connexion.prepareStatement( SQL_READ );
            statement.setInt( 1, pId );
            resultSet = statement.executeQuery();
            if ( resultSet.next() ) {
                result = bind( resultSet );
            }
        } catch ( final SQLException ex ) {
            ex.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch ( final SQLException ex ) {
                ex.printStackTrace();
            }

            try {
                statement.close();
            } catch ( final SQLException ex ) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public LivreDTO insert( LivreDTO pObject ) {
        LivreDTO result = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connexion.prepareStatement( SQL_INSERT, Statement.RETURN_GENERATED_KEYS );

            statement.setString( 1, pObject.getNom() );
            statement.setString( 2, pObject.getPrenom() );
            statement.setDate( 3, pObject.getDateParution() );

            int resultQuery = statement.executeUpdate();

            if ( resultQuery > 0 ) {
                resultSet = statement.getGeneratedKeys();
                if ( resultSet.next() ) {
                    result = read( resultSet.getInt( "livre_id" ) );
                } else {
                    System.err.println( "Aucune clef retournée ?!" );
                }
            } else {
                System.err.println(
                        "Une erreur est survenue lors de l'ajout du livre..." );
            }
        } catch ( final SQLException ex ) {
            ex.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch ( final SQLException ex ) {
                ex.printStackTrace();
            }

            try {
                statement.close();
            } catch ( final SQLException ex ) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public LivreDTO update( LivreDTO pObject ) {
        PreparedStatement statement = null;

        try {
            statement = connexion.prepareStatement( SQL_UPDATE );

            statement.setString( 1, pObject.getNom() );
            statement.setString( 2, pObject.getPrenom() );
            statement.setDate( 3, pObject.getDateParution() );
            statement.setInt( 4, pObject.getId() );

            int resultQuery = statement.executeUpdate();

            if ( resultQuery == 0 ) {
                System.err.println( "Une erreur est survenue lors de la mise à jour du livre..." );
            }
        } catch ( final SQLException ex ) {
            ex.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch ( final SQLException ex ) {
                ex.printStackTrace();
            }
        }

        return pObject;
    }

    @Override
    public boolean delete( LivreDTO pObject ) {
        boolean result = false;
        PreparedStatement statement = null;

        try {
            statement = connexion.prepareStatement( SQL_DELETE );

            statement.setInt( 1, pObject.getId() );

            int resultQuery = statement.executeUpdate();

            if ( resultQuery < 1 ) {
                System.err.println( "Une erreur est survenue lors de la suppression du livre..." );
            } else {
                result = true;
            }
        } catch ( final SQLException ex ) {
            ex.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch ( final SQLException ex ) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public List<LivreDTO> getAll() {
        List<LivreDTO> listeLivres = new ArrayList<LivreDTO>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connexion.prepareStatement( SQL_GET_ALL );

            resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                listeLivres.add( bind( resultSet ) );
            }
        } catch ( final SQLException ex ) {
            ex.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch ( final SQLException ex ) {
                ex.printStackTrace();
            }

            try {
                statement.close();
            } catch ( final SQLException ex ) {
                ex.printStackTrace();
            }
        }

        return listeLivres;
    }

    @Override
    LivreDTO bind( ResultSet pResultSet ) throws SQLException {
        return new LivreDTO( pResultSet.getInt( "livre_id" ), pResultSet.getString( "nom" ),
                pResultSet.getString( "prenom" ), pResultSet.getDate( "date_parution" ) );
    }

    @Override
    public List<LivreDTO> search( final List<CritereRechercheLivreDTO> pCriteres ) {
        List<LivreDTO> listeLivres = new ArrayList<LivreDTO>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        // Création d'une variable intermédiaire pour éviter d'écraser la
        // requête de base
        String sqlSearch = SQL_SEARCH;

        // Création de la requête de recherche
        for ( final CritereRechercheLivreDTO critere : pCriteres ) {
            sqlSearch = addCritereRecherche( sqlSearch, critere );
        }

        try {
            statement = connexion.prepareStatement( sqlSearch );

            // Binding de la requête
            int i = 1;
            for ( CritereRechercheLivreDTO critere : pCriteres ) {
                bindRequete( statement, critere, i );
                i++;
            }

            resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                listeLivres.add( bind( resultSet ) );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return listeLivres;
    }

    private String addCritereRecherche( final String pRequete, final CritereRechercheLivreDTO pCritere ) {
        return pRequete.concat( new StringBuilder( pRequete.contains( "WHERE " ) ? "AND " : "WHERE " )
                .append( pCritere.getChamp().val() ).append( "=?" ).toString() );
    }

    private void bindRequete( final PreparedStatement pStatement, final CritereRechercheLivreDTO pCritere,
            final int pIndice ) throws SQLException {
        switch ( pCritere.getChamp() ) {
            case ID:
                try {
                    pStatement.setInt( pIndice, Integer.valueOf( pCritere.getValeur() ) );
                } catch ( NumberFormatException e ) {
                    e.printStackTrace();
                }
                break;
            case NOM:
            case PRENOM:
                pStatement.setString( pIndice, pCritere.getValeur() );
                break;
            case DATE_PARUTION:
                try {
                    pStatement.setDate( pIndice, Date.valueOf( pCritere.getValeur() ) );
                } catch ( final IllegalArgumentException ex ) {
                    ex.printStackTrace();
                }
                break;
            default:
                System.err.println( "WTF man ?!?" );
                break;
        }
    }
}
