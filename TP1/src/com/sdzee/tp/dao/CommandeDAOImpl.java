package com.sdzee.tp.dao;

import static com.sdzee.tp.dao.DAOUtilitaire.fermetureConnection;
import static com.sdzee.tp.dao.DAOUtilitaire.fermetureRessources;
import static com.sdzee.tp.dao.DAOUtilitaire.fermetureStatement;
import static com.sdzee.tp.dao.DAOUtilitaire.initialisationRequetePreparee;
import static com.sdzee.tp.dao.DAOUtilitaire.mapCommande;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sdzee.tp.beans.Commande;

public class CommandeDAOImpl implements CommandeDAO {
	private static final String	SQL_INSERT_QUERY		= "INSERT INTO "
			+ "Commande(id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison) "
			+ "VALUES (?,?,?,?,?,?,?)";
	private static final String	SQL_DELETE_BY_ID_QUERY	= "DELETE FROM Commande WHERE id=?";
	private static final String	SQL_SELECT_BY_ID_QUERY	= "SELECT id, id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison"
			+ " FROM Commande WHERE id=?";
	private static final String	SQL_SELECT_ALL_QUERY	= "SELECT id, id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison"
			+ " FROM Commande ORDER BY id";

	private DAOFactory			daoFactory;

	public CommandeDAOImpl( DAOFactory daoFactory ) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void creer( Commande commande ) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connection, SQL_INSERT_QUERY, true,
					commande.getClient().getId(), new Date( 1555 ), commande.getMontant(), commande.getModePaiement(),
					( commande.getStatutPaiement() == null ? "" : commande.getStatutPaiement() ),
					commande.getModeLivraison(),
					( commande.getStatutLivraison() == null ? "" : commande.getStatutLivraison() ) );
			int statut = preparedStatement.executeUpdate();

			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création de la commande, aucune ligne ajoutée en table." );
			} else {
				valeursAutoGenerees = preparedStatement.getGeneratedKeys();
				if ( valeursAutoGenerees.next() ) {
					commande.setId( valeursAutoGenerees.getLong( 1 ) );
				} else {
					throw new DAOException(
							"Échec de la création de la commande, aucun ID auto-génerée n'est retourné." );
				}
			}
		} catch ( SQLException e ) {
			throw new DAOException( "Une erreur est survenue. Échec de création de la commande.", e );
		} finally {
			fermetureRessources( valeursAutoGenerees, preparedStatement, connection );
		}
	}

	@Override
	public Commande trouver( Long id ) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Commande commande = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connection, SQL_SELECT_BY_ID_QUERY, false, id );
			resultSet = preparedStatement.executeQuery();

			if ( resultSet.next() ) {
				commande = mapCommande( resultSet );
			} else {
				throw new DAOException( "Aucune commande trouvée avec l'ID entré." );
			}

		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermetureRessources( resultSet, preparedStatement, connection );
		}

		return commande;
	}

	@Override
	public Map<Long, Commande> lister() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Commande commande = null;
		Map<Long, Commande> commandes = new HashMap<Long, Commande>();

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connection, SQL_SELECT_ALL_QUERY, false );
			resultSet = preparedStatement.executeQuery();
			while ( resultSet.next() ) {
				commande = mapCommande( resultSet );
				commandes.put( commande.getId(), commande );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermetureRessources( resultSet, preparedStatement, connection );
		}

		return commandes;
	}

	@Override
	public void supprimer( Long id ) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		if ( this.trouver( id ) != null ) {
			try {
				connection = daoFactory.getConnection();
				preparedStatement = initialisationRequetePreparee( connection, SQL_DELETE_BY_ID_QUERY, false, id );
				int statut = preparedStatement.executeUpdate();
				if ( statut == 0 ) {
					throw new DAOException( "Échec de la suppression de la commande, aucun ID trouvé." );
				}
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				fermetureStatement( preparedStatement );
				fermetureConnection( connection );
			}
		} else {
			throw new DAOException( "Id introuvable. Aucune commande supprimée." );
		}

	}

}
