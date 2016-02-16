package fr.imie.formation.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The Class DAO.
 * 
 * @param <T>
 *            the generic type
 */
public abstract class DAO<T, K> {

    /** The connexion. */
    protected Connection connexion;

    /**
     * Gets the connexion.
     * 
     * @return the connexion
     */
    public Connection getConnexion() {
        return connexion;
    }

    /**
     * Sets the connexion.
     * 
     * @param connexion
     *            the new connexion
     */
    public void setConnexion( Connection connexion ) {
        this.connexion = connexion;
    }

    /**
     * Recherche l'objet en base.
     * 
     * @param pId
     *            L'identifiant de l'objet.
     * @return L'objet retourné
     */
    abstract public T read( final int pId );

    /**
     * Insert l'objet en base.
     * 
     * @param pObject
     *            L'objet à insérer.
     * @return L'objet inséré en base.
     */
    abstract public T insert( T pObject );

    /**
     * Modifie l'objet en base.
     * 
     * @param pObject
     *            L'objet à modifier.
     * @return L'objet modifié en base.
     */
    abstract public T update( T pObject );

    /**
     * Supprime l'objet en base.
     * 
     * @param pObject
     *            L'objet à supprimer.
     * @return {@code true} si l'objet a été supprimé, {@code false} sinon.
     */
    abstract public boolean delete( T pObject );

    /**
     * Retourne tous les objets dans la base.
     * 
     * @return La liste des objets en base.
     */
    abstract public List<T> getAll();

    /**
     * Retourne tous les objets
     * 
     * @param critere
     * @return
     */
    // TODO faire un critere abstrait et hériter
    abstract public List<T> search( final List<K> pCriteres );

    /**
     * Initialise un nouvel objet {@code T} à partir d'un ResultSet.
     * 
     * @param pResultSet
     * @return
     */
    abstract T bind( final ResultSet pResultSet ) throws SQLException;
}
